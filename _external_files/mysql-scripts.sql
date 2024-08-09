
-- table employees creation
CREATE TABLE employees (
    id int NOT NULL AUTO_INCREMENT,
    first_name varchar(150),
    last_name varchar(150),
    age int UNSIGNED,
    email varchar(150),
    occupation varchar(150),
    created_by varchar(50) DEFAULT 'system',
    created_on datetime DEFAULT CURRENT_TIMESTAMP,
    updated_by varchar(50),
    updated_on datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX index_firstName (first_name)
);

-- Handle audit fields: Create a trigger to handle default values for updated_by and updated_on fields on update
DELIMITER //
CREATE TRIGGER before_update_employees
BEFORE UPDATE ON employees
FOR EACH ROW
BEGIN
    SET NEW.updated_on = CURRENT_TIMESTAMP;
    IF NEW.updated_by IS NULL THEN
        SET NEW.updated_by = 'system';
    END IF;
END//
DELIMITER ;