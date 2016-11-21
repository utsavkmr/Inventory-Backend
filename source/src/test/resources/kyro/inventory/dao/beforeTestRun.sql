SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE city;
TRUNCATE TABLE country;
TRUNCATE TABLE province;
TRUNCATE TABLE product_category;
TRUNCATE TABLE location;
TRUNCATE TABLE measurement;
TRUNCATE TABLE vendor;

INSERT INTO city SET NAME="test", isActive=TRUE, createdBy="test", createdDate="2015/01/01";
INSERT INTO country SET NAME="test", isActive=TRUE, createdBy="test", createdDate="2015/01/01";
INSERT INTO province SET NAME="test", isActive=TRUE, createdBy="test", createdDate="2015/01/01";

SET FOREIGN_KEY_CHECKS = 1;