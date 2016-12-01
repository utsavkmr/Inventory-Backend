SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE stock_account;
TRUNCATE TABLE stock_checkpoint;
TRUNCATE TABLE stock_balance;

TRUNCATE TABLE receive_details;
TRUNCATE TABLE return_details;
TRUNCATE TABLE purchase;
TRUNCATE TABLE order_details;
TRUNCATE TABLE product;
TRUNCATE TABLE address;
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
INSERT INTO location SET NAME="test", isActive=TRUE, createdBy="test", createdDate="2015/01/01";
INSERT INTO product_category SET NAME="test", isActive=TRUE, createdBy="test", createdDate="2015/01/01";
INSERT INTO measurement SET NAME="pcs", abbreviation="pcs", isActive=TRUE, createdBy="test", createdDate="2015/01/01";
INSERT INTO measurement SET NAME="packs", abbreviation="packs", isActive=TRUE, createdBy="test", createdDate="2015/01/01";
INSERT INTO address SET postalCode="123", remarks="", street="street", cityId=1, countryId=1, provinceId=1;
INSERT INTO product SET id=1, NAME="test", barcode="123", CODE="123", costPrice=10000, purchaseUOMConversion=1,
	remarks="", reorderPoint=1, salesPrice=15000, salesStandardPrice=15000, salesUOMConversion=1,
	TYPE=0, productCategoryId=1, purchaseUOMId=1, salesUOMId=1, standardUOMId=1, isActive=TRUE, createdBy="test", createdDate="2015/01/01";
INSERT INTO vendor SET NAME="test", email="test@test.com", fax="123", phone="123",remarks="test", website="test",
	addressId = 1, isActive=TRUE, createdBy="test", createdDate="2015/01/01";

SET FOREIGN_KEY_CHECKS = 1;