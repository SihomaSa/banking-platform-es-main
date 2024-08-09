CREATE PROCEDURE `get_accounts`(IN pageNumber INT, IN pageSize INT)
BEGIN
DECLARE theOffset INT;
SET theOffset = (pageSize * pageNumber) - pageSize;
SELECT
    a1.id, a1.number, a1.balance, a1.overdraft_limit,
    a1.client_id, a1.client_dni, a1.client_name,
    a1.created_at, a1.updated_at
FROM
    account_projections a1
        JOIN (SELECT a2.id
              FROM account_projections a2
              ORDER BY a2.created_at
                  LIMIT pageSize OFFSET theOffset) AS a3 ON a1.id = a3.id;
END