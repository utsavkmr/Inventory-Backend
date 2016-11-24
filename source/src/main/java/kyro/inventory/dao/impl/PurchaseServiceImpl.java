package kyro.inventory.dao.impl;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.PurchaseService;
import kyro.inventory.model.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.Query;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Purchase Service Impl
 *
 * @author fahrur
 * @version 1.0
 */
@Service
@Transactional
public class PurchaseServiceImpl extends BaseAccountingServiceImpl<Purchase>
        implements PurchaseService {

    /**
     * Constructor
     */
    public PurchaseServiceImpl() {
        super(Purchase.class);
    }

    /**
     * Create Purchase
     * @param purchase the purchase
     * @return the purchase
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public Purchase create(Purchase purchase) throws ServiceException, DatabasePersistenceException {
        entityManager.persist(purchase);
        qtyBalanceOnCreate(purchase);
        return purchase;
    }

    /**
     * Update the purchase
     * @param purchase the purchase
     * @return the purchase
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Purchase update(Purchase purchase) throws ServiceException, DatabasePersistenceException {
        entityManager.merge(purchase);
        return purchase;
    }

    public void qtyBalanceOnCreate(Purchase purchase) throws ServiceException, DatabasePersistenceException {
        Long locationId = purchase.getLocation().getId();

        if(purchase.getOrders()!=null && purchase.getOrders().size()>0) {
            for (OrderDetails orderDetails : purchase.getOrders()) {

                Long productId = orderDetails.getProduct().getId();
                double amount = orderDetails.getQuantity();
                StockBalanceType balanceType = StockBalanceType.ON_ORDER;
                Long lastTransactionEntityId = purchase.getId();
                Long lastTransactionChildId = orderDetails.getId();
                TransactionType lastTransactionType = TransactionType.ORDER;
                Date lastTransactionDateTime = purchase.getDate();

                try {
                    StockCheckPoint stockCheckPoint = updateStockBalance(
                        productId,
                        locationId,
                        amount,
                        balanceType,
                        lastTransactionEntityId,
                        lastTransactionChildId,
                        lastTransactionType,
                        lastTransactionDateTime
                    );
                } catch (SQLException e) {
                    throw new ServiceException("Can't create stock account",e);
                }
            }
        }

    }

}
