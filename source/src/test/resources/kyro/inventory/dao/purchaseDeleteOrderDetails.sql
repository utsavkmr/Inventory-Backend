SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO purchase SET id=1, DATE="2015/01/01", dueDate="2015/01/01", freight=100, isFulfilled=FALSE, isPaid=FALSE, NO="123",
	noVendor="123", subTotal=100, taxPercent=0, taxTotal=0, total=0, locationId=1, vendorId=1, closed=0;

INSERT INTO order_details SET id=1, DATE="2015/01/01", discount=0, purchaseId=1, purchaseUOMConversion=1, quantityUOM = 10, quantity=10, totalPrice = 100, discountTotal = 0, subTotal=100, unitPrice=10, usePurchaseUOM=FALSE, productId=1;
INSERT INTO order_details SET id=2, DATE="2015/01/01", discount=0, purchaseId=1, purchaseUOMConversion=1, quantityUOM = 20, quantity=20, totalPrice = 100, discountTotal = 0, subTotal=200, unitPrice=10, usePurchaseUOM=FALSE, productId=1;

INSERT INTO stock_account SET id=1, productId = 1, locationId = 0, stockBalanceType = "ON_ORDER";
INSERT INTO stock_balance SET id=1, accountId = 1, balance = 30, lastTransactionEntityId = 1, lastTransactionChildId = 1, lastTransactionType = "ORDER", lastTransactionDateTime = "2015/01/01";
INSERT INTO stock_checkpoint SET id=1, accountId = 1, amount=10, balanceAfter=10, lastTransactionEntityId=1, lastTransactionChildId=1, lastTransactionType="ORDER", lastTransactionDateTime = "2015/01/01", closed=0, closingId=0;
INSERT INTO stock_checkpoint SET id=2, accountId = 1, amount=20, balanceAfter=30, lastTransactionEntityId=1, lastTransactionChildId=2, lastTransactionType="ORDER", lastTransactionDateTime = "2015/01/01", closed=0, closingId=0;


INSERT INTO receive_details SET id=1, purchaseId=1, purchaseUOMConversion=1, quantity=10, quantityUOM=10, receiveDate = "2015/01/01", usePurchaseUOM=FALSE, locationId = 1, orderDetailsId = 1, productId=1;

UPDATE order_details SET receiveDetailsId = 1 WHERE id=1;

INSERT INTO stock_account SET id=2, productId = 1, locationId = 1, stockBalanceType = "RECEIVE";

INSERT INTO stock_balance SET id=2, accountId = 2, balance = 10, lastTransactionEntityId = 1, lastTransactionChildId = 1, lastTransactionType = "RECEIVE", lastTransactionDateTime = "2015/01/01";

INSERT INTO stock_checkpoint SET id=3, accountId = 2, amount=10, balanceAfter=10, lastTransactionEntityId=1, lastTransactionChildId=1, lastTransactionType="RECEIVE", lastTransactionDateTime = "2015/01/01", closed=0, closingId=0;
INSERT INTO stock_checkpoint SET id=4, accountId = 1, amount=-10, balanceAfter=20, lastTransactionEntityId=1, lastTransactionChildId=1, lastTransactionType="RECEIVE", lastTransactionDateTime = "2015/01/01", closed=0, closingId=0;


SET FOREIGN_KEY_CHECKS = 1;