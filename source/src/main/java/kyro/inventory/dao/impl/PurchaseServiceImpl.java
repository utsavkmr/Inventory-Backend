package kyro.inventory.dao.impl;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.PurchaseService;
import kyro.inventory.model.*;

import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.Query;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
        calculateQtyAndTotal(purchase);
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
    @Transactional(propagation = Propagation.REQUIRED)
    public Purchase update(Purchase purchase) throws ServiceException, DatabasePersistenceException {
        calculateQtyAndTotal(purchase);
        Purchase existingPurchase = entityManager.find(Purchase.class,purchase.getId());
        CopyOnWriteArrayList<OrderDetails> existingOrderDetails = new CopyOnWriteArrayList<OrderDetails>( existingPurchase.getOrders() );

        updatePurchase(purchase);
        Purchase updated = entityManager.find(Purchase.class,purchase.getId());

        qtyBalanceOnUpdate(purchase,updated,existingOrderDetails);

        return purchase;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void qtyBalanceOnCreate(Purchase purchase) throws ServiceException, DatabasePersistenceException {
        Long locationId = null;

        if(purchase.getOrders()!=null && purchase.getOrders().size()>0) {
            for (OrderDetails orderDetails : purchase.getOrders()) {

                if(orderDetails.getId()==null) {
                    orderDetails.setPurchaseId(purchase.getId());
                    entityManager.persist(orderDetails);
                }

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

    public void calculateQtyAndTotal(Purchase purchase) {

        Double subTotals = 0.0;

        if(purchase.getOrders()!=null) {
            for (OrderDetails orderDetails : purchase.getOrders()) {
                Double unitPrice = orderDetails.getUnitPrice();
                Double discount = orderDetails.getDiscount();
                Double qty = orderDetails.getQuantityUOM() * orderDetails.getPurchaseUOMConversion();
                Double price = (qty * unitPrice);
                Double discountTotal = price * (discount/100.0);
                Double subTotal = price + discountTotal;

                subTotals += subTotal;

                orderDetails.setQuantity(qty);
                orderDetails.setSubTotal(subTotal);
            }
        }

        Double taxPercent = purchase.getTaxPercent();
        Double taxTotal = subTotals * (taxPercent/100.0);
        Double freight = purchase.getFreight();

        Double total = subTotals + taxTotal + freight;

        purchase.setSubTotal(subTotals);
        purchase.setTaxTotal(taxTotal);
        purchase.setTotal(total);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updatePurchase(Purchase purchase)  throws ServiceException, DatabasePersistenceException {
        entityManager.merge(purchase);
        entityManager.flush();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void qtyBalanceOnUpdate(Purchase purchase, Purchase updated, List<OrderDetails> existingOrderDetails) throws ServiceException, DatabasePersistenceException {

        if(existingOrderDetails!=null && existingOrderDetails.size()>0) {

            for(OrderDetails orderDetailsUpdated : existingOrderDetails) {
                Boolean isExist = false;

                for(OrderDetails orderDetails : purchase.getOrders()) {
                    if(orderDetailsUpdated.getId()==orderDetails.getId()) {
                        isExist = true;
                        break;
                    }
                }

                if(!isExist) {
                    updated.getOrders().remove(orderDetailsUpdated);
                    qtyBalanceOnDelete(purchase, orderDetailsUpdated);
                    entityManager.remove(orderDetailsUpdated);
                }
            }

            for (OrderDetails orderDetails : updated.getOrders()) {

                //Update
                Long productId = orderDetails.getProduct().getId();
                double amount = orderDetails.getQuantity();
                StockBalanceType balanceType = StockBalanceType.ON_ORDER;
                Long lastTransactionEntityId = purchase.getId();
                Long lastTransactionChildId = orderDetails.getId();
                TransactionType lastTransactionType = TransactionType.ORDER;
                Date lastTransactionDateTime = purchase.getDate();

                try {
                    StockCheckPoint stockCheckPointUpdate = updateStockBalance(
                            productId,
                            0L,
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void qtyBalanceOnDelete(Purchase purchase,OrderDetails orderDetails) throws ServiceException, DatabasePersistenceException {

        //Update
        Long productId = orderDetails.getProduct().getId();
        double amount = orderDetails.getQuantity();
        StockBalanceType balanceType = StockBalanceType.ON_ORDER;
        Long lastTransactionEntityId = purchase.getId();
        Long lastTransactionChildId = orderDetails.getId();
        TransactionType lastTransactionType = TransactionType.ORDER;
        Date lastTransactionDateTime = purchase.getDate();

        try {
            deleteStockCheckpoint(
                    productId,
                    0L,
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
