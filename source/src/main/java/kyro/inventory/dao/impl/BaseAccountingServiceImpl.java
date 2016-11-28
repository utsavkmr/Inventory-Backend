package kyro.inventory.dao.impl;

import kyro.inventory.ServiceException;
import kyro.inventory.model.*;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.jdbc.Work;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.List;
import java.util.Date;

/**
 * Created by fahrur on 11/24/2016.
 */
@Transactional
public abstract class BaseAccountingServiceImpl<T extends IdentifiableEntity> extends BaseServiceImpl<T>  {

    private static final String QUERY_GET_STOCK_ACCOUNT = "SELECT a FROM StockAccount a WHERE a.productId = ?1 AND a.locationId = ?2 AND a.stockBalanceType = ?3";

    private static final String QUERY_GET_STOCK_BALANCE = "SELECT b FROM StockBalance b WHERE b.accountId = ?1";

    private static final String QUERY_GET_STOCK_CHECKPOINT = "SELECT c FROM StockCheckpoint c WHERE c.accountId = ?1 AND c.lastTransactionEntityId=?2" +
            " AND c.lastTransactionChildId=?3 AND c.lastTransactionType=?4";

    private static final String QUERY_UPDATE_ALL_STOCK_CHECKPOINT_BALANCE_AFTER = "UPDATE StockCheckpoint c SET" +
            " c.balanceAfter = ( c.balanceAfter + ? )" +
            " WHERE c.accountId = ? AND c.id > ?";

    protected BaseAccountingServiceImpl(Class<T> entityClass) {
        super(entityClass);
    }

    protected StockAccount getStockAccount(StockAccount stockAccount) {
        StockAccount ret = null;

        // Create query
        TypedQuery<StockAccount> q = getEntityManager().createQuery(QUERY_GET_STOCK_ACCOUNT, StockAccount.class);

        q.setParameter(1, stockAccount.getProductId());
        q.setParameter(2, stockAccount.getLocationId());
        q.setParameter(3, stockAccount.stockBalanceType);

        // Execute the query
        List<StockAccount> accounts = q.getResultList();
        if (accounts.size() > 0) {
            ret = q.getSingleResult();
        }
        else {
            return null;
        }

        return ret;
    }

    protected StockAccount createStockAccount(StockAccount stockAccount) throws SQLException {
        entityManager.persist(stockAccount);
        return stockAccount;
    }

    protected StockCheckPoint getStockCheckPoint(StockCheckPoint stockCheckPoint) {

        StockCheckPoint ret = null;

        TypedQuery<StockCheckPoint> q = getEntityManager().createQuery(QUERY_GET_STOCK_CHECKPOINT, StockCheckPoint.class);

        q.setParameter(1, stockCheckPoint.getAccountId());
        q.setParameter(2, stockCheckPoint.getLastTransactionEntityId());
        q.setParameter(3, stockCheckPoint.getLastTransactionChildId());
        q.setParameter(4, stockCheckPoint.getLastTransactionType());

        List<StockCheckPoint> stockCheckPoints = q.getResultList();
        if (stockCheckPoints.size() > 0) {
            ret = q.getSingleResult();
        }
        else {
            return null;
        }

        return ret;
    }

    protected StockCheckPoint createStockCheckPoint(StockCheckPoint stockCheckPoint) {
        entityManager.persist(stockCheckPoint);
        return stockCheckPoint;
    }

    protected StockCheckPoint updateStockCheckPoint(StockCheckPoint stockCheckPoint) {
        entityManager.merge(stockCheckPoint);
        return stockCheckPoint;
    }

    protected void updateAllStockCheckPointBalanceAfter(StockCheckPoint stockCheckPoint, StockAccount stockAccount, double diff) {

        Query q = getEntityManager().createQuery(QUERY_UPDATE_ALL_STOCK_CHECKPOINT_BALANCE_AFTER);

        q.setParameter(1, diff);
        q.setParameter(2, stockAccount.getId());
        q.setParameter(3, stockCheckPoint.getId());

        q.executeUpdate();
    }

    protected StockBalance getStockBalance(StockBalance stockBalance) {
        StockBalance ret = null;

        TypedQuery<StockBalance> q = getEntityManager().createQuery(QUERY_GET_STOCK_BALANCE, StockBalance.class);

        q.setParameter(1, stockBalance.getAccountId());

        List<StockBalance> balances = q.getResultList();
        if (balances.size() > 0) {
            ret = q.getSingleResult();
        }
        else {
            return null;
        }

        return ret;
    }

    protected StockBalance createStockBalance(StockBalance stockBalance) {
        entityManager.persist(stockBalance);
        return stockBalance;
    }

    protected StockBalance updateStockBalance(StockBalance stockBalance) throws ServiceException {
        entityManager.merge(stockBalance);
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
        StockAccount existedStockAccount = getStockAccount(stockAccount);
        stockAccount = existedStockAccount != null ? existedStockAccount : createStockAccount(stockAccount);

        StockBalance stockBalance = new StockBalance(null, stockAccount.getId(), 0.00, lastTransactionEntityId,
                lastTransactionChildId, lastTransactionType, lastTransactionDateTime);
        StockBalance existedStockBalance = getStockBalance(stockBalance);
        stockBalance = existedStockBalance != null ? existedStockBalance : createStockBalance(stockBalance);

        StockCheckPoint stockCheckPoint = new StockCheckPoint(null, stockAccount.getId(),
                amount, 0.00, lastTransactionEntityId, lastTransactionChildId,
                lastTransactionType, lastTransactionDateTime);

        StockCheckPoint existedStockCheckPoint = getStockCheckPoint(stockCheckPoint);

        double balanceAfter = stockBalance.getBalance();
        double diff = 0.00;

        //Create stock checkpoint
        if(existedStockCheckPoint==null) {
            diff = amount;
            stockCheckPoint.setBalanceAfter( stockBalance.getBalance() + diff );
            stockCheckPoint = createStockCheckPoint(stockCheckPoint);
        }
        //Update stock checkpoint
        else {
            diff = amount - existedStockCheckPoint.getAmount();
            existedStockCheckPoint.setAmount(amount);
            existedStockCheckPoint.setBalanceAfter( existedStockCheckPoint.getBalanceAfter() + diff );
            stockCheckPoint = updateStockCheckPoint(existedStockCheckPoint);
        }

        updateAllStockCheckPointBalanceAfter(stockCheckPoint,stockAccount,diff);

        balanceAfter += diff;
        stockBalance.setBalance(balanceAfter);
        updateStockBalance(stockBalance);

        return stockCheckPoint;
    }

    public void deleteStockCheckpoint(
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
        stockAccount = getStockAccount(stockAccount);
        if(stockAccount==null) {
            throw new ServiceException("Stock account not exist");
        }

        StockBalance stockBalance = new StockBalance(null, stockAccount.getId(), 0.00, lastTransactionEntityId,
                lastTransactionChildId, lastTransactionType, lastTransactionDateTime);
        stockBalance = getStockBalance(stockBalance);
        if(stockBalance==null) {
            throw new ServiceException("Stock balance not exist");
        }

        StockCheckPoint stockCheckpoint = new StockCheckPoint(null, stockAccount.getId(),
                amount, 0.00, lastTransactionEntityId, lastTransactionChildId,
                lastTransactionType, lastTransactionDateTime);

        StockCheckPoint existedStockCheckpoint = getStockCheckPoint(stockCheckpoint);
        if(existedStockCheckpoint==null) {
            throw new ServiceException("Stock checkpoint not exist");
        }

        double balanceAfter = stockBalance.getBalance();
        double diff = -1 * amount;

        updateAllStockCheckPointBalanceAfter(existedStockCheckpoint,stockAccount,diff);

        entityManager.remove(existedStockCheckpoint);

        balanceAfter += diff;
        stockBalance.setBalance(balanceAfter);
        updateStockBalance(stockBalance);
    }


}
