package kyro.inventory.web;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.VendorService;
import kyro.inventory.dao.impl.ServiceHelper;
import kyro.inventory.model.BaseSearchParameters;
import kyro.inventory.model.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Vendor REST Controller
 *
 * @author fahrur
 * @version 1.0
 */
@RestController
@RequestMapping("api/vendors")
public class VendorRestController extends BaseRestController {

    /**
     * Vendor Service
     */
    @Autowired
    VendorService vendorService;

    /**
     * Get All Cities
     * @return the cities
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping("")
    public List<Vendor> search() throws ServiceException, DatabasePersistenceException {
        List<Vendor> vendors = vendorService.getAll(false);
        return vendors;
    }

    @RequestMapping(value = "/search", method= RequestMethod.POST)
    public List<Vendor> search(@RequestBody BaseSearchParameters criteria, HttpServletResponse response)
            throws ServiceException, DatabasePersistenceException
    {
        List<Vendor> products = vendorService.search(criteria);
        ServiceHelper.setSearchResponseTotalPage(response, criteria);
        return products;
    }

    /**
     * Get the vendor
     * @param id the id
     * @return the vendor
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public Vendor get(@PathVariable long id) throws ServiceException,
            DatabasePersistenceException {
        Vendor vendor = vendorService.get(id);
        return vendor;
    }

    /**
     * Create the vendor
     * @param vendor the vendor
     * @return the vendor
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "", method= RequestMethod.POST)
    public Vendor create(@RequestBody Vendor vendor) throws ServiceException,
            DatabasePersistenceException {
        vendorService.create(vendor);
        return vendor;
    }

    /**
     * Update the vendor
     * @param vendor the vendor
     * @param id the id
     * @return the vendor
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.PUT)
    public Vendor update(@RequestBody Vendor vendor,@PathVariable long id) throws ServiceException,
            DatabasePersistenceException {
        vendorService.update(vendor);
        return vendor;
    }

}
