package kyro.inventory.dao.impl;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.PurchaseService;
import kyro.inventory.dao.SalesService;
import kyro.inventory.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fahrur on 1/9/2017.
 */
@Service
@Transactional
public class SalesServiceImpl extends BaseAccountingServiceImpl<Sales>
        implements SalesService  {

    public SalesServiceImpl() {
        super(Sales.class);
    }

    public Sales create(Sales sales) throws ServiceException, DatabasePersistenceException {
        calculateQtyAndTotal(sales);
        if(sales.getSalesList()!=null && sales.getSalesList().size()>0) {
            for (SalesDetails salesDetails : sales.getSalesList()) {
                salesDetails.setLocation(sales.getLocation());
            }
        }
        qtyBalanceOnCreate(sales);
        accBalanceOnCreate(sales);
        return sales;
    }

    public Sales update(Sales sales) throws ServiceException, DatabasePersistenceException {
        calculateQtyAndTotal(sales);
        Sales existingSales = entityManager.find(Sales.class, sales.getId());
        List<SalesDetails> existingSalesDetails = new ArrayList<SalesDetails>();

        for(SalesDetails salesDetails: existingSales.getSalesList()) {
            getEntityManager().detach(salesDetails);
            existingSalesDetails.add(salesDetails);
        }

        updateSales(sales);
        Sales updated = entityManager.find(Sales.class,sales.getId());

        qtyBalanceOnUpdate(sales,updated,existingSalesDetails);
        //accBalanceOnUpdate(purchase);
        return sales;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateSales(Sales sales) throws ServiceException, DatabasePersistenceException {
        entityManager.merge(sales);
        entityManager.flush();
    }

    public void calculateQtyAndTotal(Sales sales) {

        Double subTotals = 0.0;

        if(sales.getSalesList()!=null) {
            for (SalesDetails salesDetails : sales.getSalesList()) {
                Double unitPrice = salesDetails.getUnitPrice();
                Double discount = salesDetails.getDiscount();
                Double qty = salesDetails.getQuantityUOM() * salesDetails.getSalesUOMConversion();
                Double priceTotal = (qty * unitPrice);
                Double discountTotal = priceTotal * (discount/100.0);
                Double subTotal = priceTotal + discountTotal;

                subTotals += subTotal;

                salesDetails.setQuantity(qty);
                salesDetails.setTotalPrice(priceTotal);
                salesDetails.setDiscountTotal(discountTotal);
                salesDetails.setSubTotal(subTotal);
                salesDetails.setDate(sales.getDate());
            }
        }

        Double taxPercent = sales.getTaxPercent();
        Double taxTotal = subTotals * (taxPercent/100.0);
        Double freight = sales.getFreight();

        Double total = subTotals + taxTotal + freight;

        sales.setSubTotal(subTotals);
        sales.setTaxTotal(taxTotal);
        sales.setTotal(total);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void qtyBalanceOnCreate(Sales sales) throws ServiceException, DatabasePersistenceException {

        if(sales.getSalesList()!=null && sales.getSalesList().size()>0) {
            for (SalesDetails salesDetails : sales.getSalesList()) {

                Long locationId = salesDetails.getLocation().getId();

                if(sales.getId()==null) {
                    //sales.setPurchaseId(sales.getId());
                    entityManager.persist(sales);
                }

                Long productId = salesDetails.getProduct().getId();
                double amount = salesDetails.getQuantity();
                double minusAmount = -1 * amount;
                StockBalanceType balanceType = StockBalanceType.SALES;
                Long lastTransactionEntityId = sales.getId();
                Long lastTransactionChildId = salesDetails.getId();
                TransactionType lastTransactionType = TransactionType.SALES;
                Date lastTransactionDateTime = salesDetails.getDate();

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

                    StockCheckpoint stockCheckpoint1 = updateStockBalance(
                            productId,
                            locationId,
                            minusAmount,
                            StockBalanceType.ON_HAND,
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
    public void accBalanceOnCreate(Sales sales) throws ServiceException, DatabasePersistenceException {
        Double total = sales.getTotal();
        Double subTotal = sales.getSubTotal();
        Double freight = sales.getFreight();
        Double taxTotal = sales.getTaxTotal();
        Long lastTransactionEntityId = sales.getId();
        Long lastTransactionChildId = 0L;
        TransactionType lastTransactionType = TransactionType.SALES;
        Date lastTransactionDateTime = sales.getDate();

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
    public void qtyBalanceOnUpdate(Sales sales, Sales updated, List<SalesDetails> existingSalesDetails) throws ServiceException, DatabasePersistenceException {

        if(existingSalesDetails!=null && existingSalesDetails.size()>0) {

            for(SalesDetails salesDetailsUpdated : existingSalesDetails) {
                Boolean isExist = false;

                for(SalesDetails salesDetails : sales.getSalesList()) {
                    if(salesDetailsUpdated.getId()==salesDetails.getId()) {
                        isExist = true;
                        break;
                    }
                }

                /*
                if(!isExist) {
                    if(orderDetailsUpdated.getReceiveDetails()!=null) {
                        ReceiveDetails receiveDetails = entityManager.find(
                                ReceiveDetails.class, orderDetailsUpdated.getReceiveDetails().getId());
                        qtyBalanceOnReceiveDelete(receiveDetails,purchase);
                        orderDetailsUpdated.setReceiveDetails(null);
                        entityManager.merge(orderDetailsUpdated);
                        entityManager.remove(receiveDetails);
                    }

                    if(orderDetailsUpdated.getReturnDetails()!=null) {
                        ReturnDetails returnDetails = entityManager.find(
                                ReturnDetails.class, orderDetailsUpdated.getReturnDetails().getId());
                        qtyBalanceOnReturnDelete(returnDetails, purchase);
                        orderDetailsUpdated.setReturnDetails(null);
                        entityManager.merge(orderDetailsUpdated);
                        entityManager.remove(returnDetails);
                    }

                    OrderDetails orderDetailsDelete = entityManager.find(
                            OrderDetails.class, orderDetailsUpdated.getId());
                    updated.getOrders().remove(orderDetailsDelete);
                    qtyBalanceOnDelete(purchase, orderDetailsUpdated);
                    entityManager.remove(orderDetailsDelete);
                }
                */

                if(!isExist) {
                    SalesDetails salesDetailsDelete = entityManager.find(
                            SalesDetails.class, salesDetailsUpdated.getId());
                    qtyBalanceOnDelete(sales, salesDetailsDelete);
                    entityManager.remove(salesDetailsDelete);
                }
            }
        }

        for (SalesDetails salesDetails : updated.getSalesList()) {

            //Update
            Long productId = salesDetails.getProduct().getId();
            Long locationId = salesDetails.getLocation().getId();
            double amount = salesDetails.getQuantity();
            double minusAmount = -1 * amount;
            StockBalanceType balanceType = StockBalanceType.SALES;
            Long lastTransactionEntityId = sales.getId();
            Long lastTransactionChildId = salesDetails.getId();
            TransactionType lastTransactionType = TransactionType.SALES;
            Date lastTransactionDateTime = sales.getDate();

            try {
                /*
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
                */

                SalesDetails salesProductLocationChange = salesProductLocationChange(salesDetails,existingSalesDetails);

                amount = salesDetails.getQuantity();

                if(salesProductLocationChange!=null) {

                    Long existProductId = salesProductLocationChange.getProduct().getId();
                    Long existLocatonId = salesProductLocationChange.getLocation().getId();
                    Long existSalesId = salesProductLocationChange.getId();
                    double existAmount = salesProductLocationChange.getQuantity();
                    double existAmountMinus = -1 * existAmount;

                    deleteStockCheckpoint(
                            existProductId,
                            existLocatonId,
                            existAmount,
                            balanceType,
                            lastTransactionEntityId,
                            existSalesId,
                            lastTransactionType,
                            lastTransactionDateTime
                    );

                    deleteStockCheckpoint(
                            existProductId,
                            existLocatonId,
                            existAmountMinus,
                            StockBalanceType.ON_HAND,
                            lastTransactionEntityId,
                            existSalesId,
                            lastTransactionType,
                            lastTransactionDateTime
                    );

                    StockCheckpoint stockCheckpointUpdate = updateStockBalance(
                            productId,
                            locationId,
                            amount,
                            balanceType,
                            lastTransactionEntityId,
                            lastTransactionChildId,
                            lastTransactionType,
                            lastTransactionDateTime
                    );

                    StockCheckpoint stockCheckpoint1 = updateStockBalance(
                            productId,
                            locationId,
                            minusAmount,
                            StockBalanceType.ON_HAND,
                            lastTransactionEntityId,
                            lastTransactionChildId,
                            lastTransactionType,
                            lastTransactionDateTime
                    );
                }
                else {
                    StockCheckpoint stockCheckpointUpdate = updateStockBalance(
                            productId,
                            locationId,
                            amount,
                            balanceType,
                            lastTransactionEntityId,
                            lastTransactionChildId,
                            lastTransactionType,
                            lastTransactionDateTime
                    );

                    StockCheckpoint stockCheckpoint1 = updateStockBalance(
                            productId,
                            locationId,
                            minusAmount,
                            StockBalanceType.ON_HAND,
                            lastTransactionEntityId,
                            lastTransactionChildId,
                            lastTransactionType,
                            lastTransactionDateTime
                    );
                }
            } catch (SQLException e) {
                throw new ServiceException("Can't create stock account",e);
            }

        }
    }

    public SalesDetails salesProductLocationChange(
            SalesDetails salesDetails,
            List<SalesDetails> existingList) {

        if(existingList!=null && existingList.size()>0) {
            for(SalesDetails existOrder : existingList) {
                if( salesDetails.getId() == existOrder.getId() ) {
                    if(
                        salesDetails.getProduct().getId() != existOrder.getProduct().getId() ||
                        salesDetails.getLocation().getId() != existOrder.getLocation().getId()
                            ) {
                        return existOrder;
                    }
                }
            }
        }

        return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void qtyBalanceOnDelete(Sales sales,SalesDetails salesDetails) throws ServiceException, DatabasePersistenceException {

        //Update
        Long productId = salesDetails.getProduct().getId();
        Long locationId = salesDetails.getLocation().getId();
        double amount = salesDetails.getQuantity();
        double minusAmount = -1 * amount;
        StockBalanceType balanceType = StockBalanceType.SALES;
        Long lastTransactionEntityId = sales.getId();
        Long lastTransactionChildId = salesDetails.getId();
        TransactionType lastTransactionType = TransactionType.SALES;
        Date lastTransactionDateTime = sales.getDate();

        try {
            deleteStockCheckpoint(
                    productId,
                    locationId,
                    amount,
                    balanceType,
                    lastTransactionEntityId,
                    lastTransactionChildId,
                    lastTransactionType,
                    lastTransactionDateTime
            );

            deleteStockCheckpoint(
                    productId,
                    locationId,
                    minusAmount,
                    StockBalanceType.ON_HAND,
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
