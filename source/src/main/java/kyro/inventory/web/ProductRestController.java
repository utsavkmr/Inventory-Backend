package kyro.inventory.web;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.ProductCategoryService;
import kyro.inventory.dao.ProductService;
import kyro.inventory.model.Product;
import kyro.inventory.model.ProductCategory;
import kyro.inventory.model.ProductSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import kyro.inventory.dao.impl.ServiceHelper;

/**
 * City REST Controller
 *
 * @author fahrur
 * @version 1.0
 */
@RestController
@RequestMapping("api/products")
public class ProductRestController extends BaseRestController {

    /**
     * Product Category Service
     */
    @Autowired
    ProductService productService;

    /**
     * Get All Product
     * @return the Product Categories
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping("")
    public List<Product> getAll() throws ServiceException, DatabasePersistenceException {
        List<Product> products = productService.getAll(false);
        return products;
    }

    @RequestMapping(value = "/search", method= RequestMethod.POST)
    public List<Product> search(@RequestBody ProductSearchCriteria criteria, HttpServletResponse response)
            throws ServiceException, DatabasePersistenceException
    {
        List<Product> products = productService.search(criteria);
        ServiceHelper.setSearchResponseTotalPage(response, criteria);
        return products;
    }

    /**
     * Get the Product
     * @param id the id
     * @return the Product Category
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public Product get(@PathVariable long id) throws ServiceException,
            DatabasePersistenceException {
        Product product = productService.get(id);
        return product;
    }

    /**
     * Create the Product
     * @param product the Product
     * @return the Product
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "", method= RequestMethod.POST)
    public Product create(@RequestBody Product product) throws ServiceException,
            DatabasePersistenceException {
        productService.create(product);
        return product;
    }

    /**
     * Update the Product
     * @param product the Product
     * @param id the id
     * @return the Product
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.PUT)
    public Product update(@RequestBody Product product,@PathVariable long id) throws ServiceException,
            DatabasePersistenceException {
        productService.update(product);
        return product;
    }

}
