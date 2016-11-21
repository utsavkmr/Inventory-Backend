package kyro.inventory.dao;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.model.ProductCategory;

import java.util.List;

/**
 * Product Service
 *
 * @author fahrur
 * @version 1.0
 */
public interface ProductCategoryService {

    /**
     * Create ProductCategory
     * @param productCat the productCat
     * @return the productCat
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public ProductCategory create(ProductCategory productCat) throws ServiceException, DatabasePersistenceException;

    /**
     * Update the productCat
     * @param productCat the productCat
     * @return the productCat
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public ProductCategory update(ProductCategory productCat) throws ServiceException, DatabasePersistenceException;

    /**
     * Get the productCat
     * @param id the productCat
     * @return the productCat
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public ProductCategory get(Long id) throws ServiceException, DatabasePersistenceException;

    /**
     * Get all productCat
     * @param isActive the productCat
     * @return the productCat
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public List<ProductCategory> getAll(Boolean isActive) throws ServiceException, DatabasePersistenceException;
}
