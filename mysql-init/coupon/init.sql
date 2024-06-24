-- init.sql
CREATE USER 'neulpoom'@'%' IDENTIFIED BY 'neulpoom';
GRANT ALL PRIVILEGES ON *.* TO 'neulpoom'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

CREATE TABLE IF NOT EXISTS coupon (
    coupon_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    coupon_code VARBINARY(255) NOT NULL,
    discount_rate INT NOT NULL,
    max_quantity BIGINT,
    issued_quantity BIGINT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    expires_at DATETIME DEFAULT NULL
);
