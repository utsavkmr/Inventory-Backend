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
        accBalanceOnCreate(purchase);
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
        accBalanceOnUpdate(purchase);

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
                    StockCheckpoint stockCheckpoint = updateStockBalance(
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


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void accBalanceOnCreate(Purchase purchase) throws ServiceException, DatabasePersistenceException {
        Double total = purchase.getTotal();
        Double subTotal = purchase.getSubTotal();
        Double freight = purchase.getFreight();
        Long lastTransactionEntityId = purchase.getId();
        Long lastTransactionChildId = null;
        TransactionType lastTransactionType = TransactionType.ORDER;
        Date lastTransactionDateTime = purchase.getDate();

        try {

            AccCheckpoint accCheckPointPayable = updateAccBalance(
                    null,
                    total,
                    "2110",
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    lastTransactionType,
                    lastTransactionDateTime
            );

            AccCheckpoint accCheckPointInventory = updateAccBalance(
                    null,
                    subTotal,
                    "1310",
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    lastTransactionType,
                    lastTransactionDateTime
            );

            AccCheckpoint accCheckPointFreight = updateAccBalance(
                    null,
                    freight,
                    "5700",
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    lastTransactionType,
                    lastTransactionDateTime
            );

            AccCheckpoint accCheckPointTax = updateAccBalance(
                    null,
                    freight,
                    "5910",
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    lastTransactionType,
                    lastTransactionDateTime
            );

        } catch (SQLException e) {
            throw new ServiceException("Can't create stock account",e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void accBalanceOnUpdate(Purchase purchase) throws ServiceException, DatabasePersistenceException {
        Double total = purchase.getTotal();
        Double subTotal = purchase.getSubTotal();
        Double freight = purchase.getFreight();
        Long lastTransactionEntityId = purchase.getId();
        Long lastTransactionChildId = null;
        TransactionType lastTransactionType = TransactionType.ORDER;
        Date lastTransactionDateTime = purchase.getDate();

        try {

            AccCheckpoint accCheckPointPayable = updateAccBalance(
                    null,
                    total,
                    "2110",
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    lastTransactionType,
                    lastTransactionDateTime
            );

            AccCheckpoint accCheckPointInventory = updateAccBalance(
                    null,
                    subTotal,
                    "1310",
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    lastTransactionType,
                    lastTransactionDateTime
            );

            AccCheckpoint accCheckPointFreight = updateAccBalance(
                    null,
                    freight,
                    "5700",
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    lastTransactionType,
                    lastTransactionDateTime
            );

            AccCheckpoint accCheckPointTax = updateAccBalance(
                    null,
                    freight,
                    "5910",
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    lastTransactionType,
                    lastTransactionDateTime
            );

        } catch (SQLException e) {
            throw new ServiceException("Can't create stock account",e);
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

                //Delete Order Details
                if(!isExist) {
                    // TODO :
                    // Check if exist receive details & return details that quantity > 0
                    //
                    ReceiveDetails receiveDetails = orderDetailsUpdated.getReceiveDetails();
                    if(receiveDetails!=null) {
                        qtyBalanceOnReceiveDelete(receiveDetails,purchase);
                        deleteReceiveDetails(receiveDetails);
                    }

                    updated.getOrders().remove(orderDetailsUpdated);
                    qtyBalanceOnDelete(purchase, orderDetailsUpdated);
                    deleteOrderDetails(orderDetailsUpdated);
                }
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
                StockCheckpoint stockCheckpointUpdate = updateStockBalance(
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteReceiveDetails(ReceiveDetails receiveDetails) {
        OrderDetails orderDetails = entityManager.find(OrderDetails.class, receiveDetails.getId());
        orderDetails.setReceiveDetails(null);
        entityManager.merge(orderDetails);
        entityManager.remove(receiveDetails);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteOrderDetails(OrderDetails orderDetails) {
        entityManager.remove(orderDetails);
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

    public void updatePurchaseReceived(List<ReceiveDetails> receiveDetailsList,Purchase purchase) throws ServiceException, DatabasePersistenceException {

        Purchase purchaseUpdated = entityManager.find(Purchase.class, purchase.getId());

        if(receiveDetailsList!=null) {
            for(ReceiveDetails receiveDetails : receiveDetailsList) {

                if(receiveDetails.getId()==null) {
                    entityManager.persist(receiveDetails);
                    purchaseUpdated.getReceiveDetailsList().add(receiveDetails);
                    qtyBalanceOnReceive(receiveDetails, purchase);
                }
                else {
                    entityManager.merge(receiveDetails);
                    qtyBalanceOnReceiveUpdate(receiveDetails, purchase);
                }

            }
        }

        entityManager.merge(purchaseUpdated);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void qtyBalanceOnReceive(ReceiveDetails receiveDetails, Purchase purchase) throws ServiceException, DatabasePersistenceException {
        //Update
        Long productId = receiveDetails.getProduct().getId();
        Long locationId = receiveDetails.getLocation().getId();
        double amount = receiveDetails.getQuantity();
        Long lastTransactionEntityId = purchase.getId();
        Long lastTransactionChildId = receiveDetails.getId();
        Date lastTransactionDateTime = purchase.getDate();

        try {

            StockCheckpoint stockCheckpointReceive = updateStockBalance(
                    productId,
                    locationId,
                    amount,
                    StockBalanceType.RECEIVE,
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    TransactionType.RECEIVE,
                    lastTransactionDateTime
            );

            StockCheckpoint stockCheckpointOrder = updateStockBalance(
                    productId,
                    0L,
                    -1* amount,
                    StockBalanceType.ON_ORDER,
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    TransactionType.RECEIVE,
                    lastTransactionDateTime
            );



        } catch (SQLException e) {
            throw new ServiceException("Can't create stock account",e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void qtyBalanceOnReceiveUpdate(ReceiveDetails receiveDetails, Purchase purchase) throws ServiceException, DatabasePersistenceException {
        //Update
        Long productId = receiveDetails.getProduct().getId();
        Long locationId = receiveDetails.getLocation().getId();
        double amount = receiveDetails.getQuantity();
        Long lastTransactionEntityId = purchase.getId();
        Long lastTransactionChildId = receiveDetails.getId();
        Date lastTransactionDateTime = purchase.getDate();

        try {
            Location location = getLocationReceiveDetails(receiveDetails);

            if(location !=null && locationId == location.getId()) {

                StockCheckpoint stockCheckpointReceive = updateStockBalance(
                        productId,
                        locationId,
                        amount,
                        StockBalanceType.RECEIVE,
                        lastTransactionEntityId,
                        lastTransactionChildId,
                        TransactionType.RECEIVE,
                        lastTransactionDateTime
                );

                StockCheckpoint stockCheckpointOrder = updateStockBalance(
                        productId,
                        0L,
                        -1 * amount,
                        StockBalanceType.ON_ORDER,
                        lastTransactionEntityId,
                        lastTransactionChildId,
                        TransactionType.RECEIVE,
                        lastTransactionDateTime
                );

            }
            else {

                deleteStockCheckpoint(
                        productId,
                        location.getId(),
                        amount,
                        StockBalanceType.RECEIVE,
                        lastTransactionEntityId,
                        lastTransactionChildId,
                        TransactionType.RECEIVE,
                        lastTransactionDateTime
                );

                StockCheckpoint stockCheckpointReceive = updateStockBalance(
                        productId,
                        locationId,
                        amount,
                        StockBalanceType.RECEIVE,
                        lastTransactionEntityId,
                        lastTransactionChildId,
                        TransactionType.RECEIVE,
                        lastTransactionDateTime
                );

                updateStockBalance(
                        productId,
                        0L,
                        -1 * amount,
                        StockBalanceType.ON_ORDER,
                        lastTransactionEntityId,
                        lastTransactionChildId,
                        TransactionType.RECEIVE,
                        lastTransactionDateTime
                );

            }

        } catch (SQLException e) {
            throw new ServiceException("Can't create stock account",e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void qtyBalanceOnReceiveDelete(ReceiveDetails receiveDetails, Purchase purchase) throws ServiceException, DatabasePersistenceException {
        //Delete
        Long productId = receiveDetails.getProduct().getId();
        Long locationId = receiveDetails.getLocation().getId();
        double amount = receiveDetails.getQuantity();
        Long lastTransactionEntityId = purchase.getId();
        Long lastTransactionChildId = receiveDetails.getId();
        Date lastTransactionDateTime = purchase.getDate();

        try {
            Location location = getLocationReceiveDetails(receiveDetails);

            deleteStockCheckpoint(
                    productId,
                    location.getId(),
                    amount,
                    StockBalanceType.RECEIVE,
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    TransactionType.RECEIVE,
                    lastTransactionDateTime
            );

            deleteStockCheckpoint(
                    productId,
                    0L,
                    amount,
                    StockBalanceType.ON_ORDER,
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    TransactionType.RECEIVE,
                    lastTransactionDateTime
            );

        } catch (SQLException e) {
            throw new ServiceException("Can't create stock account",e);
        }
    }

}
