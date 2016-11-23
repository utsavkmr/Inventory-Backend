SET FOREIGN_KEY_CHECKS = 0;

-- -----------------------------------------------------
-- Table `inventory`.`inv_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inventory`.`inv_account` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `productId` BIGINT NOT NULL,
  `locationId` BIGINT NOT NULL,
  `stockBalanceType` ENUM('ON_HAND', 'ON_ORDER', 'AVAILABLE', 'IN_TRANSIT') NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `inventory`.`inv_balance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inventory`.`inv_balance` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `accountId` BIGINT NOT NULL,
  `balance` DECIMAL(15,2) NOT NULL,
  `lastTransactionEntityId` BIGINT NOT NULL,
  `lastTransactionChildId` BIGINT NOT NULL,
  `lastTransactionType` ENUM('ORDER', 'RECEIVE', 'RETURN') NOT NULL,
  `lastTransactionDateTime` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_inv_balance_inv_account1_idx` (`accountId` ASC),
  CONSTRAINT `fk_inv_balance_inv_account1`
    FOREIGN KEY (`accountId`)
    REFERENCES `inventory`.`inv_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `inventory`.`inv_checkpoint`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inventory`.`inv_checkpoint` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `accountId` BIGINT NOT NULL,
  `amount` DECIMAL(15,2) NOT NULL,
  `balanceAfter` DECIMAL(15,2) NOT NULL,
  `lastTransactionEntityId` BIGINT NOT NULL,
  `lastTransactionChildId` BIGINT NOT NULL,
  `lastTransactionType` ENUM('ORDER', 'RECEIVE', 'RETURN') NOT NULL,
  `lastTransactionDateTime` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_inv_checkpoint_inv_account1_idx` (`accountId` ASC),
  CONSTRAINT `fk_inv_checkpoint_inv_account1`
    FOREIGN KEY (`accountId`)
    REFERENCES `inventory`.`inv_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

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
INSERT INTO product SET NAME="test", barcode="123", CODE="123", costPrice=10000, purchaseUOMConversion=1,
	remarks="", reorderPoint=1, salesPrice=15000, salesStandardPrice=15000, salesUOMConversion=1,
	TYPE=0, productCategoryId=1, purchaseUOMId=1, salesUOMId=1, standardUOMId=1, isActive=TRUE, createdBy="test", createdDate="2015/01/01";
INSERT INTO vendor SET NAME="test", email="test@test.com", fax="123", phone="123",remarks="test", website="test",
	addressId = 1, isActive=TRUE, createdBy="test", createdDate="2015/01/01";

INSERT INTO purchase SET DATE="2015/01/01", dueDate="2015/01/01", freight=100, isFulfilled=FALSE, isPaid=FALSE, NO="123",
	noVendor="123", subTotal=100, taxPercent=0, taxTotal=0, locationId=1, vendorId=1;

INSERT INTO order_details SET DATE="2015/01/01", discount=0, purchaseId=1, purchaseUOMConversion=1, quantity=10, subTotal=100, unitPrice=10, usePurchaseUOM=FALSE, productId=1;

SET FOREIGN_KEY_CHECKS = 1;