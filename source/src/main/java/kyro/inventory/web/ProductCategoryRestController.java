package kyro.inventory.web;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.ProductCategoryService;
import kyro.inventory.model.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * City REST Controller
 *
 * @author fahrur
 * @version 1.0
 */
@RestController
@RequestMapping("api/product-categories")
public class ProductCategoryRestController extends BaseRestController {

    /**
     * Product Category Service
     */
    @Autowired
    ProductCategoryService productCategoryService;

    /**
     * Get All Product Categories
     * @return the Product Categories
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping("")
    public List<ProductCategory> search() throws ServiceException, DatabasePersistenceException {
        List<ProductCategory> productCategories = productCategoryService.getAll(false);
        return productCategories;
    }

    /**
     * Get the Product Category
     * @param id the id
     * @return the Product Category
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public ProductCategory get(@PathVariable long id) throws ServiceException,
            DatabasePersistenceException {
        ProductCategory productCat = productCategoryService.get(id);
        return productCat;
    }

    /**
     * Create the Product Category
     * @param productCat the Product Category
     * @return the Product Category
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "", method= RequestMethod.POST)
    public ProductCategory create(@RequestBody ProductCategory productCat) throws ServiceException,
            DatabasePersistenceException {
        productCategoryService.create(productCat);
        return productCat;
    }

    /**
     * Update the Product Category
     * @param productCat the Product Category
     * @param id the id
     * @return the Product Category
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.PUT)
    public ProductCategory update(@RequestBody ProductCategory productCat,@PathVariable long id) throws ServiceException,
            DatabasePersistenceException {
        productCategoryService.update(productCat);
        return productCat;
    }

}
