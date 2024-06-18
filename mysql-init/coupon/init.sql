-- init.sql
CREATE USER 'neulpoom'@'%' IDENTIFIED BY 'neulpoom';
GRANT ALL PRIVILEGES ON *.* TO 'neulpoom'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
