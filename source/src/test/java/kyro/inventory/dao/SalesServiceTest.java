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
 * Created by fahrur on 1/9/2017.
 */
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
                "classpath:kyro/inventory/dao/beforeTestRun.sql",
                "classpath:kyro/inventory/dao/salesTestScenario.sql"
        })
})
public class SalesServiceTest extends BaseTest {

    @Test
    public void createSalesTest() throws ServiceException, DatabasePersistenceException {
        Vendor vendor = vendorService.get(1L);
        Product product = productService.get(1L);
        Location location = locationService.get(1L);
        Customer customer = customerService.get(1L);

        Sales sales = new Sales();
        sales.setDate(new Date());
        sales.setDueDate(new Date());
        sales.setFreight(0.0);
        sales.setPaid(false);
        sales.setLocation(location);
        sales.setNo("123");
        sales.setCustomer(customer);
        sales.setSubTotal(100.0);
        sales.setTaxPercent(0.0);
        sales.setTaxTotal(0.0);
        sales.setClosed(false);

        SalesDetails salesDetails = new SalesDetails();
        salesDetails.setId(1L);
        salesDetails.setDate(new Date());
        salesDetails.setProduct(product);
        salesDetails.setUnitPrice(20.0);
        salesDetails.setQuantityUOM(20.0);
        salesDetails.setDiscount(0.0);
        salesDetails.setSubTotal(100.0);
        salesDetails.setSalesUOMConversion(1.0);
        salesDetails.setUseSalesUOM(false);

        List<SalesDetails> salesList = new ArrayList<SalesDetails>();
        salesList.add(salesDetails);

        sales.setSalesList(salesList);

        salesService.update(sales);
    }


    @Test
    public void updateSalesTest() throws ServiceException, DatabasePersistenceException {
        Vendor vendor = vendorService.get(1L);
        Product product = productService.get(1L);
        Location location = locationService.get(1L);
        Customer customer = customerService.get(1L);

        Sales sales = new Sales();
        sales.setId(1L);
        sales.setDate(new Date());
        sales.setDueDate(new Date());
        sales.setFreight(0.0);
        sales.setPaid(false);
        sales.setLocation(location);
        sales.setNo("123");
        sales.setCustomer(customer);
        sales.setSubTotal(100.0);
        sales.setTaxPercent(0.0);
        sales.setTaxTotal(0.0);
        sales.setClosed(false);

        SalesDetails salesDetails = new SalesDetails();
        salesDetails.setId(1L);
        salesDetails.setDate(new Date());
        salesDetails.setProduct(product);
        salesDetails.setUnitPrice(10.0);
        salesDetails.setQuantityUOM(10.0);
        salesDetails.setDiscount(0.0);
        salesDetails.setSubTotal(100.0);
        salesDetails.setSalesUOMConversion(1.0);
        salesDetails.setUseSalesUOM(false);
        salesDetails.setLocation(location);

        List<SalesDetails> salesList = new ArrayList<SalesDetails>();
        salesList.add(salesDetails);

        sales.setSalesList(salesList);

        salesService.update(sales);
    }

    @Test
    public void updateSalesTestChangeLocation() throws ServiceException, DatabasePersistenceException {
        Vendor vendor = vendorService.get(1L);
        Product product = productService.get(1L);
        Location location = locationService.get(2L);
        Customer customer = customerService.get(1L);

        Sales sales = new Sales();
        sales.setId(1L);
        sales.setDate(new Date());
        sales.setDueDate(new Date());
        sales.setFreight(0.0);
        sales.setPaid(false);
        sales.setLocation(location);
        sales.setNo("123");
        sales.setCustomer(customer);
        sales.setSubTotal(100.0);
        sales.setTaxPercent(0.0);
        sales.setTaxTotal(0.0);
        sales.setClosed(false);

        SalesDetails salesDetails = new SalesDetails();
        salesDetails.setId(1L);
        salesDetails.setDate(new Date());
        salesDetails.setProduct(product);
        salesDetails.setUnitPrice(10.0);
        salesDetails.setQuantityUOM(10.0);
        salesDetails.setDiscount(0.0);
        salesDetails.setSubTotal(100.0);
        salesDetails.setSalesUOMConversion(1.0);
        salesDetails.setUseSalesUOM(false);
        salesDetails.setLocation(location);

        List<SalesDetails> salesList = new ArrayList<SalesDetails>();
        salesList.add(salesDetails);

        sales.setSalesList(salesList);

        salesService.update(sales);
    }

    @Test
    public void updateSalesTestDelete() throws ServiceException, DatabasePersistenceException {
        Vendor vendor = vendorService.get(1L);
        Product product = productService.get(1L);
        Location location = locationService.get(2L);
        Customer customer = customerService.get(1L);

        Sales sales = new Sales();
        sales.setId(1L);
        sales.setDate(new Date());
        sales.setDueDate(new Date());
        sales.setFreight(0.0);
        sales.setPaid(false);
        sales.setLocation(location);
        sales.setNo("123");
        sales.setCustomer(customer);
        sales.setSubTotal(100.0);
        sales.setTaxPercent(0.0);
        sales.setTaxTotal(0.0);
        sales.setClosed(false);

        List<SalesDetails> salesList = new ArrayList<SalesDetails>();

        sales.setSalesList(salesList);

        salesService.update(sales);
    }



}
