SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE stock_account;
TRUNCATE TABLE stock_checkpoint;
TRUNCATE TABLE stock_balance;

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

INSERT INTO purchase SET id=1, DATE="2015/01/01", dueDate="2015/01/01", freight=100, isFulfilled=FALSE, isPaid=FALSE, NO="123",
	noVendor="123", subTotal=100, taxPercent=0, taxTotal=0, total=0, locationId=1, vendorId=1, closed=0;

INSERT INTO order_details SET id=1, DATE="2015/01/01", discount=0, purchaseId=1, purchaseUOMConversion=1, quantityUOM = 10, quantity=10, subTotal=100, unitPrice=10, usePurchaseUOM=FALSE, productId=1;
INSERT INTO order_details SET id=2, DATE="2015/01/01", discount=0, purchaseId=1, purchaseUOMConversion=1, quantityUOM = 20, quantity=20, subTotal=200, unitPrice=10, usePurchaseUOM=FALSE, productId=1;

INSERT INTO stock_account SET id=1, productId = 1, locationId = 0, stockBalanceType = "ON_ORDER";
INSERT INTO stock_balance SET id=1, accountId = 1, balance = 30, lastTransactionEntityId = 1, lastTransactionChildId = 1, lastTransactionType = "ORDER", lastTransactionDateTime = "2015/01/01";
INSERT INTO stock_checkpoint SET id=1, accountId = 1, amount=10, balanceAfter=10, lastTransactionEntityId=1, lastTransactionChildId=1, lastTransactionType="ORDER", lastTransactionDateTime = "2015/01/01", closed=0, closingId=0;
INSERT INTO stock_checkpoint SET id=2, accountId = 1, amount=20, balanceAfter=30, lastTransactionEntityId=1, lastTransactionChildId=2, lastTransactionType="ORDER", lastTransactionDateTime = "2015/01/01", closed=0, closingId=0;

SET FOREIGN_KEY_CHECKS = 1;