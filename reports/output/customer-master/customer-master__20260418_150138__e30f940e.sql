WITH latest_address AS (
    SELECT
        ca.customer_identifier,
        ca.customer_address_type,
        ca.customer_address_value,
        ca.effective_date,
        ROW_NUMBER() OVER (
            PARTITION BY ca.customer_identifier
            ORDER BY ca.effective_date DESC, ca.address_id DESC
        ) AS rn
    FROM customer_address ca
    WHERE (:asOfDate IS NULL OR ca.effective_date <= :asOfDate)
),
latest_contact AS (
    SELECT
        ci.customer_identifier,
        ci.customer_contact_type,
        ci.customer_contact_value,
        ci.effective_date,
        ci.start_date,
        ci.end_date,
        ROW_NUMBER() OVER (
            PARTITION BY ci.customer_identifier
            ORDER BY ci.effective_date DESC, ci.contact_information_id DESC
        ) AS rn
    FROM customer_contact_information ci
    WHERE (:contactType IS NULL OR ci.customer_contact_type = :contactType)
      AND (:asOfDate IS NULL OR ci.effective_date <= :asOfDate)
)
SELECT
    cd.customer_identifier AS customer_id,
    cd.customer_full_name AS customer_name,
    cd.customer_status AS customer_status,
    cd.customer_country_of_origin AS country_of_origin,
    cd.customer_preferred_language AS preferred_language,
    cd.customer_gender AS gender,
    cd.customer_date_of_birth AS date_of_birth,
    lc.customer_contact_type AS primary_contact_type,
    lc.customer_contact_value AS primary_contact_value,
    la.customer_address_type AS latest_address_type,
    la.customer_address_value AS latest_address_value,
    la.effective_date AS address_effective_date
FROM customer_detail cd
LEFT JOIN latest_address la
    ON la.customer_identifier = cd.customer_identifier
   AND la.rn = 1
LEFT JOIN latest_contact lc
    ON lc.customer_identifier = cd.customer_identifier
   AND lc.rn = 1
WHERE (:status IS NULL OR TRIM(cd.customer_status) = TRIM(:status))
  AND (:country IS NULL OR TRIM(cd.customer_country_of_origin) = TRIM(:country))
ORDER BY cd.customer_identifier;
