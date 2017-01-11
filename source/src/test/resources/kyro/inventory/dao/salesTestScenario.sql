SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO location SET id=2, NAME="test2", isActive=TRUE, createdBy="test", createdDate="2015/01/01";

INSERT INTO sales SET id=1, DATE="2015/01/01", dueDate="2015/01/01", freight=100, isPaid=FALSE, NO="123",
	subTotal=100, taxPercent=0, taxTotal=0, total=0, locationId=1, customerId=1, closed=0;

INSERT INTO sales_details SET id=1, DATE="2015/01/01", discount=0, salesUOMConversion=1, quantityUOM = 10, quantity=10, totalPrice = 100, discountTotal = 0, subTotal=100, unitPrice=10, useSalesUOM=FALSE, productId=1, locationId=1, salesId=1;

INSERT INTO stock_account SET id=1, productId = 1, locationId = 1, stockBalanceType = "SALES";
INSERT INTO stock_account SET id=2, productId = 1, locationId = 1, stockBalanceType = "ON_HAND";
INSERT INTO stock_balance SET id=1, accountId = 1, balance = 10, lastTransactionEntityId = 1, lastTransactionChildId = 1, lastTransactionType = "SALES", lastTransactionDateTime = "2015/01/01";
INSERT INTO stock_balance SET id=2, accountId = 2, balance = -10, lastTransactionEntityId = 1, lastTransactionChildId = 1, lastTransactionType = "SALES", lastTransactionDateTime = "2015/01/01";
INSERT INTO stock_checkpoint SET id=1, accountId = 1, amount=10, balanceAfter=10, lastTransactionEntityId=1, lastTransactionChildId=1, lastTransactionType="SALES", lastTransactionDateTime = "2015/01/01", closed=0, closingId=0;
INSERT INTO stock_checkpoint SET id=2, accountId = 2, amount=-10, balanceAfter=-10, lastTransactionEntityId=1, lastTransactionChildId=1, lastTransactionType="SALES", lastTransactionDateTime = "2015/01/01", closed=0, closingId=0;

SET FOREIGN_KEY_CHECKS = 1;