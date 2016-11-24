package kyro.inventory.dao.impl;

import kyro.inventory.ServiceException;
import kyro.inventory.model.*;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.jdbc.Work;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;

import javax.persistence.Query;
import java.sql.*;
import java.util.List;
import java.util.Date;

/**
 * Created by fahrur on 11/24/2016.
 */
public abstract class BaseAccountingServiceImpl<T extends IdentifiableEntity> extends BaseServiceImpl<T>  {

    private static final String QUERY_GET_STOCK_ACCOUNT = "SELECT * FROM inv_account a WHERE a.productId = ? AND a.locationId = ? AND stockBalanceType = ?";

    private static final String QUERY_INSERT_STOCK_ACCOUNT  = "INSERT INTO inv_account SET productId = ?, locationId = ?, stockBalanceType = ?;";

    private static final String QUERY_LAST_INSERT_ID = "SELECT LAST_INSERT_ID();";

    private static final String QUERY_GET_STOCK_BALANCE =
            "SELECT * FROM inv_balance b WHERE b.accountId = ?";

    private static final String QUERY_INSERT_STOCK_BALANCE  = "INSERT INTO inv_balance SET accountId = ?, balance = ?, lastTransactionEntityId = ?," +
            " lastTransactionChildId = ?, lastTransactionType = ?, lastTransactionDateTime = ?;";

    private static final String QUERY_UPDATE_STOCK_BALANCE  = "UPDATE inv_balance SET balance = ?, lastTransactionEntityId = ?," +
            " lastTransactionChildId = ?, lastTransactionType = ?, lastTransactionDateTime = ?" +
            " WHERE id=?;";

    private static final String QUERY_GET_STOCK_CHECKPOINT = "SELECT * FROM inv_checkpoint c WHERE c.accountId = ? AND c.lastTransactionEntityId=?" +
            " AND c.lastTransactionChildId=? AND c.lastTransactionType=?";

    private static final String QUERY_INSERT_STOCK_CHECKPOINT = "INSERT INTO inv_checkpoint SET accountId = ?" +
            ", amount=?, balanceAfter=?"+
            ", lastTransactionEntityId=?, lastTransactionChildId=?, lastTransactionType=?, lastTransactionDateTime=?";

    private static final String QUERY_UPDATE_STOCK_CHECKPOINT = "UPDATE inv_checkpoint SET" +
            " amount=?, balanceAfter=?, lastTransactionDateTime=?" +
            " WHERE id=?";

    private static final String QUERY_UPDATE_ALL_STOCK_CHECKPOINT_BALANCE_AFTER = "UPDATE inv_checkpoint SET" +
            " balanceAfter = balanceAfter + ?" +
            " WHERE accountId = ? AND id > ?";

    protected BaseAccountingServiceImpl(Class<T> entityClass) {
        super(entityClass);
    }

    protected StockAccount getStockAccountId(StockAccount stockAccount) {
        Long stockAccountId=null;

        Query q = entityManager.createNativeQuery(QUERY_GET_STOCK_ACCOUNT);

        q.setParameter(1, stockAccount.productId);
        q.setParameter(2, stockAccount.locationId);
        q.setParameter(3, stockAccount.stockBalanceType.toString());

        List accounts = q.getResultList();
        if (accounts.size() > 0) {
            stockAccount = (StockAccount) q.getSingleResult();
        }

        return stockAccount;
    }

    protected StockAccount createStockAccountId(StockAccount stockAccount) throws SQLException {

        stockAccount = getStockAccountId(stockAccount);

        if(stockAccount.id==null) {

            Query q = entityManager.createNativeQuery(QUERY_INSERT_STOCK_ACCOUNT);
            q.setParameter(1, stockAccount.productId);
            q.setParameter(2, stockAccount.locationId);
            q.setParameter(3, stockAccount.stockBalanceType.toString());

            q.executeUpdate();

            Query qId = entityManager.createNativeQuery(QUERY_LAST_INSERT_ID);
            stockAccount.id = ((Number) qId.getSingleResult()).longValue();
        }
        return stockAccount;
    }

    protected StockCheckPoint getStockCheckPoint(StockCheckPoint stockCheckPoint) {

        Query q = entityManager.createNativeQuery(QUERY_GET_STOCK_CHECKPOINT);

        q.setParameter(1, stockCheckPoint.accountId);
        q.setParameter(2, stockCheckPoint.lastTransactionEntityId);
        q.setParameter(3, stockCheckPoint.lastTransactionChildId);
        q.setParameter(4, stockCheckPoint.lastTransactionType.toString());

        List accounts = q.getResultList();
        if (accounts.size() > 0) {
            stockCheckPoint = (StockCheckPoint) q.getSingleResult();
        }

        return stockCheckPoint;
    }

    protected StockCheckPoint createStockCheckPoint(StockCheckPoint stockCheckPoint) {

        Query q = entityManager.createNativeQuery(QUERY_INSERT_STOCK_CHECKPOINT);
        q.setParameter(1, stockCheckPoint.accountId);
        q.setParameter(2, stockCheckPoint.amount);
        q.setParameter(3, stockCheckPoint.balanceAfter);
        q.setParameter(4, stockCheckPoint.lastTransactionEntityId);
        q.setParameter(5, stockCheckPoint.lastTransactionChildId);
        q.setParameter(6, stockCheckPoint.lastTransactionType.toString());
        q.setParameter(7, stockCheckPoint.lastTransactionDateTime);

        q.executeUpdate();

        Query qId = entityManager.createNativeQuery(QUERY_LAST_INSERT_ID);
        stockCheckPoint.id = ((Number) qId.getSingleResult()).longValue();


        return stockCheckPoint;
    }

    protected StockCheckPoint updateStockCheckPoint(StockCheckPoint stockCheckPoint) {

        Query q = entityManager.createNativeQuery(QUERY_UPDATE_STOCK_CHECKPOINT);
        q.setParameter(1, stockCheckPoint.amount);
        q.setParameter(2, stockCheckPoint.balanceAfter);
        q.setParameter(3, stockCheckPoint.lastTransactionDateTime);
        q.setParameter(4, stockCheckPoint.id);

        q.executeUpdate();

        return stockCheckPoint;
    }

    protected void updateAllStockCheckPointBalanceAfter(StockCheckPoint stockCheckPoint, StockAccount stockAccount, double diff) {

        Query q = entityManager.createNativeQuery(QUERY_UPDATE_ALL_STOCK_CHECKPOINT_BALANCE_AFTER);
        q.setParameter(1, diff);
        q.setParameter(2, stockAccount.id);
        q.setParameter(3, stockCheckPoint.id);

        q.executeUpdate();
    }

    protected StockBalance getStockBalance(StockBalance stockBalance) {

        Query q = entityManager.createNativeQuery(QUERY_GET_STOCK_BALANCE);

        q.setParameter(1, stockBalance.accountId);

        List accounts = q.getResultList();
        if (accounts.size() > 0) {
            stockBalance = (StockBalance) q.getSingleResult();
        }

        return stockBalance;
    }

    protected StockBalance createStockBalance(StockBalance stockBalance) {

        stockBalance = getStockBalance(stockBalance);

        if(stockBalance.id==null) {
            Query q = entityManager.createNativeQuery(QUERY_INSERT_STOCK_BALANCE);
            q.setParameter(1, stockBalance.accountId);
            q.setParameter(2, 0.00);
            q.setParameter(3, stockBalance.lastTransactionEntityId);
            q.setParameter(4, stockBalance.lastTransactionChildId);
            q.setParameter(5, stockBalance.lastTransactionType.toString());
            q.setParameter(6, stockBalance.lastTransactionDateTime);

            q.executeUpdate();

            Query qId = entityManager.createNativeQuery(QUERY_LAST_INSERT_ID);
            stockBalance.id = ((Number) qId.getSingleResult()).longValue();
        }

        return stockBalance;
    }

    protected StockBalance updateStockBalance(StockBalance stockBalance) throws ServiceException {

        if(stockBalance.id!=null) {
            Query q = entityManager.createNativeQuery(QUERY_UPDATE_STOCK_BALANCE);
            q.setParameter(1, stockBalance.balance);
            q.setParameter(2, stockBalance.lastTransactionEntityId);
            q.setParameter(3, stockBalance.lastTransactionChildId);
            q.setParameter(4, stockBalance.lastTransactionType.toString());
            q.setParameter(5, stockBalance.lastTransactionDateTime);
            q.setParameter(6, stockBalance.id);

            q.executeUpdate();
        }
        else {
            throw new ServiceException("Stock balance "+stockBalance.id+" are not exist!");
        }

        return stockBalance;
    }

    public StockCheckPoint updateStockBalance(
            Long productId,
            Long locationId,
            Double amount,
            StockBalanceType balanceType,
            Long lastTransactionEntityId,
            Long lastTransactionChildId,
            TransactionType lastTransactionType,
            Date lastTransactionDateTime
    ) throws SQLException, ServiceException {

        StockAccount stockAccount = new StockAccount(null,productId,locationId,balanceType);
        stockAccount = createStockAccountId(stockAccount);

        StockBalance stockBalance = new StockBalance(null, stockAccount.id, 0.00, lastTransactionEntityId,
                lastTransactionChildId, lastTransactionType, lastTransactionDateTime);
        stockBalance = createStockBalance(stockBalance);

        StockCheckPoint stockCheckPoint = new StockCheckPoint(null, stockAccount.id,
                amount, 0.00, lastTransactionEntityId, lastTransactionChildId,
                lastTransactionType, lastTransactionDateTime);

        stockCheckPoint = getStockCheckPoint(stockCheckPoint);

        double balanceAfter = stockBalance.balance;
        double diff = 0.00;

        //Create stock checkpoint
        if(stockCheckPoint.id==null) {
            diff = amount;
            stockCheckPoint.balanceAfter = stockBalance.balance + diff;
            stockCheckPoint = createStockCheckPoint(stockCheckPoint);
        }
        //Update stock checkpoint
        else {
            diff = amount - stockCheckPoint.amount;
            stockCheckPoint.balanceAfter = stockCheckPoint.balanceAfter + diff;
            stockCheckPoint = updateStockCheckPoint(stockCheckPoint);
        }

        updateAllStockCheckPointBalanceAfter(stockCheckPoint,stockAccount,diff);

        balanceAfter += diff;
        stockBalance.balance = balanceAfter;
        updateStockBalance(stockBalance);

        return stockCheckPoint;
    }


}
