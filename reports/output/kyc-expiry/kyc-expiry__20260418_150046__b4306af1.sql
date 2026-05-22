WITH latest_proof AS (
    SELECT
        cp.customer_identifier,
        cp.proof_of_id_type,
        cp.proof_of_id_value,
        cp.start_date,
        cp.end_date,
        cp.effective_date,
        ROW_NUMBER() OVER (
            PARTITION BY cp.customer_identifier
            ORDER BY cp.end_date ASC, cp.proof_id DESC
        ) AS rn
    FROM customer_proof_of_id cp
    WHERE (:proofType IS NULL OR TRIM(cp.proof_of_id_type) = TRIM(:proofType))
      AND (:asOfDate IS NULL OR cp.effective_date <= :asOfDate)
)
SELECT
    cd.customer_identifier AS customer_id,
    cd.customer_full_name AS customer_name,
    cd.customer_status AS customer_status,
    lp.proof_of_id_type AS proof_type,
    lp.proof_of_id_value AS proof_value,
    lp.start_date AS proof_start_date,
    lp.end_date AS proof_end_date,
    CASE
        WHEN lp.customer_identifier IS NULL THEN 'MISSING_PROOF'
        WHEN :startDate IS NOT NULL AND :endDate IS NOT NULL
             AND lp.end_date >= :startDate
             AND lp.end_date < DATE_ADD(:endDate, INTERVAL 1 DAY) THEN 'EXPIRING_IN_RANGE'
        WHEN lp.end_date < :asOfDate THEN 'EXPIRED'
        ELSE 'VALID'
    END AS proof_status
FROM customer_detail cd
LEFT JOIN latest_proof lp
    ON lp.customer_identifier = cd.customer_identifier
   AND lp.rn = 1
WHERE (:status IS NULL OR TRIM(cd.customer_status) = TRIM(:status))
  AND (
      lp.customer_identifier IS NULL
      OR :startDate IS NULL
      OR :endDate IS NULL
      OR (lp.end_date >= :startDate AND lp.end_date < DATE_ADD(:endDate, INTERVAL 1 DAY))
      OR lp.end_date < :asOfDate
  )
ORDER BY proof_status, lp.end_date, cd.customer_identifier;
