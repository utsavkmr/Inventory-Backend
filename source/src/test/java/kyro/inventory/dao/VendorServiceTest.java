package kyro.inventory.dao;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.model.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by fahrur on 11/21/2016.
 */
public class VendorServiceTest extends BaseTest {

    @Test
    public void createVendorTest() throws ServiceException, DatabasePersistenceException {
        City city = cityService.get(1L);
        Province province = provinceService.get(1L);
        Country country = countryService.get(1L);

        Address address = new Address();
        address.setCity(city);
        address.setCountry(country);
        address.setPostalCode(stringTest);
        address.setProvince(province);
        address.setStreet(stringTest);
        address.setRemarks(stringTest);

        Vendor vendor = new Vendor();
        vendor.setName(stringTest);
        vendor.setEmail(stringTest);
        vendor.setFax(stringTest);
        vendor.setPhone(stringTest);
        vendor.setRemarks(stringTest);
        vendor.setWebsite(stringTest);
        vendor.setAddress(address);
        vendor.setIsActive(boolTest);

        vendorService.create(vendor);
    }


}
