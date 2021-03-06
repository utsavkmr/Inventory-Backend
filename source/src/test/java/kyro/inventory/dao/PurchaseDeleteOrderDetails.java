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
 * Created by fahrur on 12/1/2016.
 */
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
                "classpath:kyro/inventory/dao/beforeTestRun.sql",
                "classpath:kyro/inventory/dao/purchaseDeleteOrderDetails.sql",
        })
})
public class PurchaseDeleteOrderDetails extends BaseTest {

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
}
