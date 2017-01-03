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
                "classpath:kyro/inventory/dao/purchaseReturn.sql",
        })
})
public class PurchaseReturn extends BaseTest {

    @Test
    public void updatePurchaseTest() throws ServiceException, DatabasePersistenceException {
        Vendor vendor = vendorService.get(1L);
        Product product = productService.get(1L);
        Location location = locationService.get(1L);

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setId(1L);

        Purchase purchase = new Purchase();
        purchase.setId(1L);

        ReturnDetails returnDetails = new ReturnDetails();
        returnDetails.setPurchaseId(1L);
        returnDetails.setProduct(product);
        returnDetails.setQuantityUOM(5.0);
        returnDetails.setQuantity(5.0);
        returnDetails.setPurchaseUOMConversion(1.0);
        returnDetails.setUsePurchaseUOM(false);
        returnDetails.setReturnDate(dateTest);

        orderDetails.setReturnDetails(returnDetails);

        List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
        orderDetailsList.add(orderDetails);

        purchaseService.updatePurchaseReturn(orderDetailsList, purchase);
    }
}
