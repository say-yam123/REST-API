WITH active_contacts AS (
    SELECT
        ci.customer_identifier,
        COUNT(*) AS active_contact_count,
        MAX(CASE WHEN UPPER(ci.customer_contact_type) = 'EMAIL' THEN 1 ELSE 0 END) AS has_email,
        MAX(CASE WHEN UPPER(ci.customer_contact_type) = 'PHONE' THEN 1 ELSE 0 END) AS has_phone,
        MAX(ci.end_date) AS latest_contact_end_date
    FROM customer_contact_information ci
    WHERE (:contactType IS NULL OR TRIM(ci.customer_contact_type) = TRIM(:contactType))
      AND (:asOfDate IS NULL OR ci.effective_date <= :asOfDate)
      AND (:asOfDate IS NULL OR ci.start_date <= :asOfDate)
      AND (:asOfDate IS NULL OR ci.end_date >= :asOfDate)
    GROUP BY ci.customer_identifier
)
SELECT
    cd.customer_identifier AS customer_id,
    cd.customer_full_name AS customer_name,
    cd.customer_status AS customer_status,
    cd.customer_country_of_origin AS country_of_origin,
    COALESCE(ac.active_contact_count, 0) AS active_contact_count,
    COALESCE(ac.has_email, 0) AS has_email,
    COALESCE(ac.has_phone, 0) AS has_phone,
    ac.latest_contact_end_date AS latest_contact_end_date,
    CASE
        WHEN COALESCE(ac.active_contact_count, 0) = 0 THEN 'NO_ACTIVE_CONTACT'
        WHEN COALESCE(ac.has_email, 0) = 0 THEN 'MISSING_EMAIL'
        WHEN COALESCE(ac.has_phone, 0) = 0 THEN 'MISSING_PHONE'
        ELSE 'OK'
    END AS coverage_status
FROM customer_detail cd
LEFT JOIN active_contacts ac
    ON ac.customer_identifier = cd.customer_identifier
WHERE (:status IS NULL OR TRIM(cd.customer_status) = TRIM(:status))
  AND (:country IS NULL OR TRIM(cd.customer_country_of_origin) = TRIM(:country))
ORDER BY active_contact_count ASC, cd.customer_identifier;
