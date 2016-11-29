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

    private static final String QUERY_GET_ACC_ACCOUNT = "SELECT a FROM AccAccount a WHERE a.productId = ?1 AND a.accountCode = ?2";

    private static final String QUERY_GET_STOCK_BALANCE = "SELECT b FROM StockBalance b WHERE b.accountId = ?1";

    private static final String QUERY_GET_ACC_BALANCE = "SELECT b FROM AccBalance b WHERE b.accountId = ?1";

    private static final String QUERY_GET_STOCK_CHECKPOINT = "SELECT c FROM StockCheckpoint c WHERE c.accountId = ?1 AND c.lastTransactionEntityId=?2" +
            " AND c.lastTransactionChildId=?3 AND c.lastTransactionType=?4";

    private static final String QUERY_GET_ACC_CHECKPOINT = "SELECT c FROM AccCheckpoint c WHERE c.accountId = ?1 AND c.lastTransactionEntityId=?2" +
            " AND c.lastTransactionChildId=?3 AND c.lastTransactionType=?4";

    private static final String QUERY_UPDATE_ALL_STOCK_CHECKPOINT_BALANCE_AFTER = "UPDATE StockCheckpoint c SET" +
            " c.balanceAfter = ( c.balanceAfter + ? )" +
            " WHERE c.accountId = ? AND c.id > ?";

    private static final String QUERY_UPDATE_ALL_ACC_CHECKPOINT_BALANCE_AFTER = "UPDATE AccCheckpoint c SET" +
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
        q.setParameter(3, stockAccount.getStockBalanceType());

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

    protected AccAccount getAccAccount(AccAccount accAccount) {
        AccAccount ret = null;

        // Create query
        TypedQuery<AccAccount> q = getEntityManager().createQuery(QUERY_GET_ACC_ACCOUNT, AccAccount.class);

        q.setParameter(1, accAccount.getProductId());
        q.setParameter(2, accAccount.getAccountCode());

        // Execute the query
        List<AccAccount> accounts = q.getResultList();
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

    protected AccAccount createAccAccount(AccAccount accAccount) throws SQLException {
        entityManager.persist(accAccount);
        return accAccount;
    }

    protected StockCheckpoint getStockCheckPoint(StockCheckpoint stockCheckpoint) {

        StockCheckpoint ret = null;

        TypedQuery<StockCheckpoint> q = getEntityManager().createQuery(QUERY_GET_STOCK_CHECKPOINT, StockCheckpoint.class);

        q.setParameter(1, stockCheckpoint.getAccountId());
        q.setParameter(2, stockCheckpoint.getLastTransactionEntityId());
        q.setParameter(3, stockCheckpoint.getLastTransactionChildId());
        q.setParameter(4, stockCheckpoint.getLastTransactionType());

        List<StockCheckpoint> stockCheckPoints = q.getResultList();
        if (stockCheckPoints.size() > 0) {
            ret = q.getSingleResult();
        }
        else {
            return null;
        }

        return ret;
    }


    protected AccCheckpoint getAccCheckPoint(AccCheckpoint accCheckpoint) {

        AccCheckpoint ret = null;

        TypedQuery<AccCheckpoint> q = getEntityManager().createQuery(QUERY_GET_ACC_CHECKPOINT, AccCheckpoint.class);

        q.setParameter(1, accCheckpoint.getAccountId());
        q.setParameter(2, accCheckpoint.getLastTransactionEntityId());
        q.setParameter(3, accCheckpoint.getLastTransactionChildId());
        q.setParameter(4, accCheckpoint.getLastTransactionType());

        List<AccCheckpoint> accCheckPoints = q.getResultList();
        if (accCheckPoints.size() > 0) {
            ret = q.getSingleResult();
        }
        else {
            return null;
        }

        return ret;
    }

    protected StockCheckpoint createStockCheckPoint(StockCheckpoint stockCheckpoint) {
        entityManager.persist(stockCheckpoint);
        return stockCheckpoint;
    }

    protected AccCheckpoint createAccCheckPoint(AccCheckpoint accCheckpoint) {
        entityManager.persist(accCheckpoint);
        return accCheckpoint;
    }

    protected StockCheckpoint updateStockCheckPoint(StockCheckpoint stockCheckpoint) {
        entityManager.merge(stockCheckpoint);
        return stockCheckpoint;
    }

    protected AccCheckpoint updateAccCheckPoint(AccCheckpoint AccCheckPoint) {
        entityManager.merge(AccCheckPoint);
        return AccCheckPoint;
    }

    protected void updateAllStockCheckPointBalanceAfter(StockCheckpoint stockCheckpoint, StockAccount stockAccount, double diff) {

        Query q = getEntityManager().createQuery(QUERY_UPDATE_ALL_STOCK_CHECKPOINT_BALANCE_AFTER);

        q.setParameter(1, diff);
        q.setParameter(2, stockAccount.getId());
        q.setParameter(3, stockCheckpoint.getId());

        q.executeUpdate();
    }

    protected void updateAllAccCheckPointBalanceAfter(AccCheckpoint accCheckpoint, AccAccount accAccount, double diff) {

        Query q = getEntityManager().createQuery(QUERY_UPDATE_ALL_ACC_CHECKPOINT_BALANCE_AFTER);

        q.setParameter(1, diff);
        q.setParameter(2, accAccount.getId());
        q.setParameter(3, accCheckpoint.getId());

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

    protected AccBalance getAccBalance(AccBalance accBalance) {
        AccBalance ret = null;

        TypedQuery<AccBalance> q = getEntityManager().createQuery(QUERY_GET_ACC_BALANCE, AccBalance.class);

        q.setParameter(1, accBalance.getAccountId());

        List<AccBalance> balances = q.getResultList();
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

    protected AccBalance createAccBalance(AccBalance accBalance) {
        entityManager.persist(accBalance);
        return accBalance;
    }

    protected StockBalance updateStockBalance(StockBalance stockBalance) throws ServiceException {
        entityManager.merge(stockBalance);
        return stockBalance;
    }

    protected AccBalance updateAccBalance(AccBalance accBalance) throws ServiceException {
        entityManager.merge(accBalance);
        return accBalance;
    }

    public StockCheckpoint updateStockBalance(
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

        StockCheckpoint stockCheckPoint = new StockCheckpoint(null, stockAccount.getId(),
                amount, 0.00, lastTransactionEntityId, lastTransactionChildId,
                lastTransactionType, lastTransactionDateTime, false, 0L);

        StockCheckpoint existedStockCheckPoint = getStockCheckPoint(stockCheckPoint);

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

    public AccCheckpoint updateAccBalance(
            Long productId,
            Double amount,
            String accountCode,
            Long lastTransactionEntityId,
            Long lastTransactionChildId,
            TransactionType lastTransactionType,
            Date lastTransactionDateTime
    ) throws SQLException, ServiceException {
        AccCheckpoint accCheckpoint;

        AccAccount account = new AccAccount(null,productId,accountCode);
        AccAccount existedAccount = getAccAccount(account);
        account = existedAccount != null ? existedAccount : createAccAccount(account);

        AccBalance accBalance = new AccBalance(null, account.getId(), 0.00, lastTransactionEntityId,
                lastTransactionChildId, lastTransactionType, lastTransactionDateTime);
        AccBalance existedAccBalance = getAccBalance(accBalance);
        accBalance = existedAccBalance != null ? existedAccBalance : createAccBalance(accBalance);

        accCheckpoint = new AccCheckpoint(null, account.getId(),
                amount, 0.00, lastTransactionEntityId, lastTransactionChildId,
                lastTransactionType, lastTransactionDateTime, false, 0L);

        AccCheckpoint existedAccCheckpoint = getAccCheckPoint(accCheckpoint);

        double balanceAfter = accBalance.getBalance();
        double diff = 0.00;

        //Create acc checkpoint
        if(existedAccCheckpoint==null) {
            diff = amount;
            accCheckpoint.setBalanceAfter( accBalance.getBalance() + diff );
            accCheckpoint = createAccCheckPoint(accCheckpoint);
        }
        //Update acc checkpoint
        else {
            diff = amount - existedAccCheckpoint.getAmount();
            existedAccCheckpoint.setAmount(amount);
            existedAccCheckpoint.setBalanceAfter( existedAccCheckpoint.getBalanceAfter() + diff );
            accCheckpoint = updateAccCheckPoint(existedAccCheckpoint);
        }

        updateAllAccCheckPointBalanceAfter(accCheckpoint,account,diff);

        balanceAfter += diff;
        accBalance.setBalance(balanceAfter);
        updateAccBalance(accBalance);

        return accCheckpoint;
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

        StockCheckpoint stockCheckpoint = new StockCheckpoint(null, stockAccount.getId(),
                amount, 0.00, lastTransactionEntityId, lastTransactionChildId,
                lastTransactionType, lastTransactionDateTime, false, 0L);

        StockCheckpoint existedStockCheckpoint = getStockCheckPoint(stockCheckpoint);
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
