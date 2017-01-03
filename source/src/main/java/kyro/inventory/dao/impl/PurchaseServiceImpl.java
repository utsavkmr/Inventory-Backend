package kyro.inventory.dao.impl;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.PurchaseService;
import kyro.inventory.model.*;
import kyro.inventory.dao.impl.ServiceHelper;

import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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

    public List<Purchase> search(PurchaseSearchCriteria criteria)
            throws ServiceException, DatabasePersistenceException {

        Integer page = criteria.page;
        Integer limit = criteria.limit;

        List<Purchase> purchases = null;

        final String signature = CLASS_NAME + ".search(S criteria)";

        String className = "Purchase";

        try {
            StringBuffer sb = new StringBuffer("SELECT e FROM ").append(className).append(" e ");
            StringBuffer sbWhere = new StringBuffer("WHERE (1=1)");

            if(criteria.vendorId !=null ) {
                sbWhere.append(" AND e.vendor.id = :vendorId");
            }
            if(criteria.dateFrom !=null ) {
                sbWhere.append(" AND e.date >= :dateFrom");
            }
            if(criteria.dateTo !=null ) {
                sbWhere.append(" AND e.date >= :dateTo");
            }


            sb.append(sbWhere);

            // Append ORDER BY clause
            sb.append(" ORDER BY ").append("id").append(" ").append("asc");

            // Create query
            TypedQuery<Purchase> query = getEntityManager().createQuery(sb.toString(), Purchase.class);

            if(criteria.vendorId !=null ) {
                query.setParameter("vendorId", criteria.vendorId);
            }
            if(criteria.dateFrom !=null ) {
                query.setParameter("dateForm", criteria.dateFrom);
            }
            if(criteria.dateTo !=null ) {
                query.setParameter("dateTo", criteria.dateTo);
            }

            query.setFirstResult((page-1) * limit);
            query.setMaxResults(limit);

            // Execute the query
            purchases = query.getResultList();

            String sql = "SELECT COUNT(e) FROM "+className+" e";
            Query q = getEntityManager().createQuery(sql);
            Long count = (Long) q.getSingleResult();

            criteria.totalRow = count;
            criteria.totalPage = getTotalPage(criteria.totalRow, criteria);

        } catch (IllegalStateException e) {
            throw ServiceHelper.logException(getLogger(), signature, new ServiceException(
                    "Entity manager is closed when searching entities", e));
        }

        return purchases;
    }

    public Boolean validateCreate(Purchase purchase,StringBuilder errorMessage) {
        List<String> errorMessages = new ArrayList<String>();
        Boolean ret = true;

        if(purchase.getDate()==null) {
            errorMessages.add(messageService.getMessage("purchase.order.date_empty"));
            ret &= false;
        }

        if(purchase.getVendor()==null) {
            errorMessages.add(messageService.getMessage("purchase.order.vendor_empty"));
            ret &= false;
        }

        if(purchase.getVendor()==null) {
            errorMessages.add(messageService.getMessage("purchase.order.vendor_empty"));
            ret &= false;
        }

        if(purchase.getOrders()!=null) {
            Boolean orderDetailsValidation = true;
            for(OrderDetails orderDetails : purchase.getOrders()) {

                if(orderDetails.getProduct()==null) {
                    errorMessages.add(messageService.getMessage("purchase.order.product_empty"));
                    orderDetailsValidation &= false;
                }

                if(orderDetailsValidation==false) {
                    ret &= false;
                    break;
                }
            }
        }

        if(ret==false) {
            errorMessage.append(errorMessages.get(0));
        }

        return ret;
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
        Purchase existingPurchase = entityManager.find(Purchase.class, purchase.getId());
        //CopyOnWriteArrayList<OrderDetails> existingOrderDetails = new CopyOnWriteArrayList<OrderDetails>();
        List<OrderDetails> existingOrderDetails = new ArrayList<OrderDetails>();

        if(purchase.getOrders()!=null) {
            for(OrderDetails orderDetails: existingPurchase.getOrders()) {
                getEntityManager().detach(orderDetails);
                existingOrderDetails.add(orderDetails);
            }
        }

        updatePurchase(purchase);
        Purchase updated = entityManager.find(Purchase.class,purchase.getId());

        qtyBalanceOnUpdate(purchase,updated,existingOrderDetails);
        accBalanceOnUpdate(purchase);

        return purchase;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void qtyBalanceOnCreate(Purchase purchase) throws ServiceException, DatabasePersistenceException {
        Long locationId = 0L;

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
        Double taxTotal = purchase.getTaxTotal();
        Long lastTransactionEntityId = purchase.getId();
        Long lastTransactionChildId = 0L;
        TransactionType lastTransactionType = TransactionType.ORDER;
        Date lastTransactionDateTime = purchase.getDate();

        try {

            AccCheckpoint accCheckPointPayable = updateAccBalance(
                    0L,
                    total,
                    "2110",
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    lastTransactionType,
                    lastTransactionDateTime
            );

            AccCheckpoint accCheckPointInventory = updateAccBalance(
                    0L,
                    subTotal,
                    "1310",
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    lastTransactionType,
                    lastTransactionDateTime
            );

            AccCheckpoint accCheckPointFreight = updateAccBalance(
                    0L,
                    freight,
                    "5700",
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    lastTransactionType,
                    lastTransactionDateTime
            );

            AccCheckpoint accCheckPointTax = updateAccBalance(
                    0L,
                    taxTotal,
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
        Long lastTransactionChildId = 0L;
        TransactionType lastTransactionType = TransactionType.ORDER;
        Date lastTransactionDateTime = purchase.getDate();

        try {

            AccCheckpoint accCheckPointPayable = updateAccBalance(
                    0L,
                    total,
                    "2110",
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    lastTransactionType,
                    lastTransactionDateTime
            );

            AccCheckpoint accCheckPointInventory = updateAccBalance(
                    0L,
                    subTotal,
                    "1310",
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    lastTransactionType,
                    lastTransactionDateTime
            );

            AccCheckpoint accCheckPointFreight = updateAccBalance(
                    0L,
                    freight,
                    "5700",
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    lastTransactionType,
                    lastTransactionDateTime
            );

            AccCheckpoint accCheckPointTax = updateAccBalance(
                    0L,
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
                Double priceTotal = (qty * unitPrice);
                Double discountTotal = priceTotal * (discount/100.0);
                Double subTotal = priceTotal + discountTotal;

                subTotals += subTotal;

                orderDetails.setQuantity(qty);
                orderDetails.setTotalPrice(priceTotal);
                orderDetails.setDiscountTotal(discountTotal);
                orderDetails.setSubTotal(subTotal);
                orderDetails.setDate(purchase.getDate());
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

    public void calculateQtyAndTotalOfReturn(Purchase purchase, OrderDetails orderDetailsReturn, Double diffUOM) {

        Double subTotals = 0.0;

        if(purchase.getOrders()!=null) {
            for (OrderDetails orderDetails : purchase.getOrders()) {
                Double unitPrice = orderDetails.getUnitPrice();
                Double discount = orderDetails.getDiscount();
                Double qtyUOM = orderDetails.getQuantityUOM() + diffUOM;
                Double qty = orderDetails.getQuantityUOM() * orderDetails.getPurchaseUOMConversion();

                if(orderDetailsReturn.getId()==orderDetails.getId()){
                    Double diff = diffUOM * orderDetails.getPurchaseUOMConversion();
                    qty += diff;
                }

                Double priceTotal = (qty * unitPrice);
                Double discountTotal = priceTotal * (discount/100.0);
                Double subTotal = priceTotal + discountTotal;

                subTotals += subTotal;

                orderDetails.setQuantityUOM(qtyUOM);
                orderDetails.setQuantity(qty);
                orderDetails.setTotalPrice(priceTotal);
                orderDetails.setDiscountTotal(discountTotal);
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
                        orderDetailsUpdated.setReceiveDetails(null);
                        entityManager.merge(orderDetailsUpdated);
                        entityManager.remove(receiveDetails);
                    }

                    updated.getOrders().remove(orderDetailsUpdated);
                    qtyBalanceOnDelete(purchase, orderDetailsUpdated);
                    entityManager.remove(orderDetailsUpdated);
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
                OrderDetails orderProductChange = orderProductChange(orderDetails,existingOrderDetails);
                OrderDetails orderMeasurementChange = orderMeasurementChange(orderDetails,existingOrderDetails);

                if(orderProductChange!=null) {

                    if (orderProductChange.getReceiveDetails() != null) {
                        ReceiveDetails receiveDetails = entityManager.find(
                                ReceiveDetails.class, orderProductChange.getReceiveDetails().getId());
                        qtyBalanceOnReceiveDelete(receiveDetails, purchase);
                        orderDetails.setReceiveDetails(null);
                        entityManager.merge(orderDetails);
                        entityManager.remove(receiveDetails);
                    }

                    if(orderProductChange.getReturnDetails() != null) {
                        ReturnDetails returnDetails = entityManager.find(
                                ReturnDetails.class, orderProductChange.getReturnDetails().getId());

                        qtyBalanceOnReturnDelete(returnDetails, purchase);
                        orderDetails.setReturnDetails(null);
                        entityManager.merge(orderDetails);
                        entityManager.remove(returnDetails);
                    }

                    qtyBalanceOnDelete(purchase, orderProductChange);
                }
                else if(orderMeasurementChange!=null) {

                    if (orderMeasurementChange.getReceiveDetails() != null) {
                        ReceiveDetails receiveDetails = entityManager.find(
                                ReceiveDetails.class, orderMeasurementChange.getReceiveDetails().getId());
                        qtyBalanceOnReceiveDelete(receiveDetails, purchase);
                        orderDetails.setReceiveDetails(null);
                        entityManager.merge(orderDetails);
                        entityManager.remove(receiveDetails);
                    }

                    if(orderMeasurementChange.getReturnDetails() != null) {
                        ReturnDetails returnDetails = entityManager.find(
                                ReturnDetails.class, orderMeasurementChange.getReturnDetails().getId());

                        qtyBalanceOnReturnDelete(returnDetails, purchase);
                        orderDetails.setReturnDetails(null);
                        entityManager.merge(orderDetails);
                        entityManager.remove(returnDetails);
                    }
                }

                amount = orderDetails.getQuantity();
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

    public OrderDetails orderProductChange(
            OrderDetails orderDetails,
            List<OrderDetails> existingList) {

        if(existingList!=null && existingList.size()>0) {
            for(OrderDetails existOrder : existingList) {
                if( orderDetails.getId() == existOrder.getId() ) {
                    if(orderDetails.getProduct().getId() != existOrder.getProduct().getId()) {
                        return existOrder;
                    }
                }
            }
        }

        return null;
    }

    public OrderDetails orderMeasurementChange(
            OrderDetails orderDetails,
            List<OrderDetails> existingList) {

        if(existingList!=null && existingList.size()>0) {
            for(OrderDetails existOrder : existingList) {
                if( orderDetails.getId() == existOrder.getId() ) {
                    if(orderDetails.getUsePurchaseUOM() != existOrder.getUsePurchaseUOM()) {
                        return existOrder;
                    }
                }
            }
        }

        return null;
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

    public void updatePurchaseReceived(List<OrderDetails> orderDetailsList, Purchase purchase) throws ServiceException, DatabasePersistenceException {

        Purchase purchaseUpdated = entityManager.find(Purchase.class, purchase.getId());

        if(orderDetailsList!=null) {
            for(OrderDetails orderDetails : orderDetailsList) {
                ReceiveDetails receiveDetails = orderDetails.getReceiveDetails();

                OrderDetails orderDetailsUpdate = getEntityManager().find(OrderDetails.class, orderDetails.getId());
                orderDetailsUpdate.setReceiveDetails(receiveDetails);

                receiveDetails.setPurchaseId(purchase.getId());

                if(receiveDetails.getId()==null) {
                    entityManager.persist(receiveDetails);
                    entityManager.merge(orderDetailsUpdate);
                    purchaseUpdated.getReceiveDetailsList().add(receiveDetails);
                    qtyBalanceOnReceive(receiveDetails, purchase);
                }
                else {
                    entityManager.merge(receiveDetails);
                    entityManager.merge(orderDetailsUpdate);
                    qtyBalanceOnReceiveUpdate(receiveDetails, purchase);
                }

            }
        }

        entityManager.merge(purchaseUpdated);
    }

    public void updatePurchaseReturn(List<OrderDetails> orderDetailsList,Purchase purchase) throws ServiceException, DatabasePersistenceException {

        if(orderDetailsList!=null) {
            for(OrderDetails orderDetails : orderDetailsList) {

                ReturnDetails returnDetails = orderDetails.getReturnDetails();
                Double diff = 0.0;
                OrderDetails orderDetailsUpdate =  entityManager.find(OrderDetails.class, orderDetails.getId());
                orderDetailsUpdate.setReturnDetails(returnDetails);

                if (returnDetails.getId() == null) {
                    entityManager.persist(returnDetails);
                    entityManager.merge(orderDetailsUpdate);

                    diff = -1.0 * returnDetails.getQuantityUOM();
                } else {
                    ReturnDetails existReturnDetails = entityManager.find(ReturnDetails.class, returnDetails.getId());
                    diff = -1.0 * (returnDetails.getQuantityUOM() - existReturnDetails.getQuantityUOM());

                    entityManager.merge(returnDetails);
                    entityManager.merge(orderDetailsUpdate);
                }

                purchase = entityManager.find(Purchase.class, purchase.getId());

                calculateQtyAndTotalOfReturn(purchase, orderDetailsUpdate, diff);
                Purchase existingPurchase = entityManager.find(Purchase.class, purchase.getId());
                CopyOnWriteArrayList<OrderDetails> existingOrderDetails = new CopyOnWriteArrayList<OrderDetails>(existingPurchase.getOrders());

                updatePurchase(purchase);
                Purchase updated = entityManager.find(Purchase.class, purchase.getId());

                try{
                    StockCheckpoint stockCheckpointReturn = updateStockBalance(
                            orderDetails.getProduct().getId(),
                            0L,
                            returnDetails.getQuantity(),
                            StockBalanceType.RETURN,
                            purchase.getId(),
                            returnDetails.getId(),
                            TransactionType.RETURN,
                            returnDetails.getReturnDate()
                    );
                } catch (SQLException e) {
                    throw new ServiceException("Can't create stock account",e);
                }

                qtyBalanceOnUpdate(purchase, updated, existingOrderDetails);
                accBalanceOnUpdate(purchase);
            }
        }
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void qtyBalanceOnReturnDelete(ReturnDetails returnDetails, Purchase purchase) throws ServiceException, DatabasePersistenceException {
        //Delete
        Long productId = returnDetails.getProduct().getId();
        double amount = returnDetails.getQuantity();
        Long lastTransactionEntityId = purchase.getId();
        Long lastTransactionChildId = returnDetails.getId();
        Date lastTransactionDateTime = purchase.getDate();

        try {

            deleteStockCheckpoint(
                    productId,
                    0L,
                    amount,
                    StockBalanceType.RETURN,
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    TransactionType.RETURN,
                    lastTransactionDateTime
            );

        } catch (SQLException e) {
            throw new ServiceException("Can't create stock account",e);
        }
    }

}
