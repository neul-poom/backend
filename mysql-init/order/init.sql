-- init.sql
CREATE USER 'neulpoom'@'%' IDENTIFIED BY 'neulpoom';
GRANT ALL PRIVILEGES ON *.* TO 'neulpoom'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

CREATE TABLE IF NOT EXISTS orders
(
    order_num   BIGINT AUTO_INCREMENT PRIMARY KEY,
    buyer_num   BIGINT         NOT NULL,
    lecture_num BIGINT         NOT NULL,
    price       DECIMAL(15, 2) NOT NULL,
    status      ENUM ('INITIATED', 'IN_PROGRESS', 'COMPLETED', 'FAILED_CUSTOMER', 'CANCELED'),
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);