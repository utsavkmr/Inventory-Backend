package kyro.inventory.dao;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.model.*;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fahrur on 11/23/2016.
 */
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:kyro/inventory/dao/beforeTestRun.sql",
            "classpath:kyro/inventory/dao/purchaseTestScenario.sql"
        })
})
public class PurchaseServiceTest extends BaseTest {
    @Test
    public void createPurchaseTest() throws ServiceException, DatabasePersistenceException {
        Vendor vendor = vendorService.get(1L);
        Product product = productService.get(1L);
        Location location = locationService.get(1L);

        Purchase purchase = new Purchase();
        purchase.setDate(new Date());
        purchase.setDueDate(new Date());
        purchase.setFreight(0.0);
        purchase.setFulfilled(false);
        purchase.setPaid(false);
        purchase.setLocation(location);
        purchase.setNo("123");
        purchase.setNoVendor("123");
        purchase.setSubTotal(100.0);
        purchase.setTaxPercent(0.0);
        purchase.setTaxTotal(0.0);
        purchase.setVendor(vendor);
        purchase.setClosed(false);

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setDate(new Date());
        orderDetails.setProduct(product);
        orderDetails.setUnitPrice(10.0);
        orderDetails.setQuantityUOM(10.0);
        orderDetails.setDiscount(0.0);
        orderDetails.setSubTotal(100.0);
        orderDetails.setPurchaseUOMConversion(1.0);
        orderDetails.setUsePurchaseUOM(false);

        List<OrderDetails> orders = new ArrayList<OrderDetails>();
        orders.add(orderDetails);

        purchase.setOrders(orders);

        purchaseService.create(purchase);
    }

    @Test
    public void updatePurchaseTest() throws ServiceException, DatabasePersistenceException {
        Vendor vendor = vendorService.get(1L);
        Product product = productService.get(1L);
        Location location = locationService.get(1L);

        Purchase purchase = new Purchase();
        purchase.setId(1L);
        purchase.setDate(new Date());
        purchase.setDueDate(new Date());
        purchase.setFreight(0.0);
        purchase.setFulfilled(false);
        purchase.setPaid(false);
        purchase.setLocation(location);
        purchase.setNo("123");
        purchase.setNoVendor("123");
        purchase.setSubTotal(100.0);
        purchase.setTaxPercent(0.0);
        purchase.setTaxTotal(0.0);
        purchase.setVendor(vendor);
        purchase.setClosed(false);

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setDate(new Date());
        orderDetails.setProduct(product);
        orderDetails.setUnitPrice(10.0);
        orderDetails.setQuantityUOM(5.0);
        orderDetails.setDiscount(0.0);
        orderDetails.setSubTotal(50.0);
        orderDetails.setPurchaseUOMConversion(1.0);
        orderDetails.setUsePurchaseUOM(false);
        orderDetails.setPurchaseId(1L);

        OrderDetails orderDetailsEdit = new OrderDetails();
        orderDetailsEdit.setId(2L);
        orderDetailsEdit.setDate(new Date());
        orderDetailsEdit.setProduct(product);
        orderDetailsEdit.setUnitPrice(5.0);
        orderDetailsEdit.setQuantityUOM(10.0);
        orderDetailsEdit.setDiscount(0.0);
        orderDetailsEdit.setSubTotal(50.0);
        orderDetailsEdit.setPurchaseUOMConversion(1.0);
        orderDetailsEdit.setUsePurchaseUOM(false);
        orderDetailsEdit.setPurchaseId(1L);

        List<OrderDetails> orders = new ArrayList<OrderDetails>();
        orders.add(orderDetails);
        orders.add(orderDetailsEdit);

        purchase.setOrders(orders);

        purchaseService.update(purchase);
    }

    @Test
    public void receivePurchaseTest() throws ServiceException, DatabasePersistenceException {
        Vendor vendor = vendorService.get(1L);
        Product product = productService.get(1L);
        Location location = locationService.get(1L);
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setId(1L);

        Purchase purchase = new Purchase();
        purchase.setId(1L);

        List<ReceiveDetails> receiveDetailsList = new ArrayList<ReceiveDetails>();

        ReceiveDetails receiveDetails = new ReceiveDetails();
        receiveDetails.setOrderDetails(orderDetails);
        receiveDetails.setProduct(product);
        receiveDetails.setLocation(location);
        receiveDetails.setQuantity(10.0);
        receiveDetails.setQuantityUOM(10.0);
        receiveDetails.setPurchaseUOMConversion(1.0);
        receiveDetails.setUsePurchaseUOM(true);
        receiveDetails.setReceiveDate(new Date());

        receiveDetailsList.add(receiveDetails);

        purchaseService.updatePurchaseReceived(receiveDetailsList,purchase);

    }

}
