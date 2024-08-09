CREATE PROCEDURE `get_clients`(IN pageNumber INT, IN pageSize INT)
BEGIN
DECLARE theOffset INT;
SET theOffset = (pageSize * pageNumber) - pageSize;
SELECT c1.id, c1.first_name, c1.last_name, c1.dni, c1.status, c1.created_at, c1.updated_at
FROM client_projections c1
         JOIN (SELECT c2.id
               FROM client_projections c2
               ORDER BY c2.last_name, c2.first_name
                   LIMIT pageSize OFFSET theOffset) AS c3 ON c1.id = c3.id;
END