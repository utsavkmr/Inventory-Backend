package kyro.inventory.dao;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.model.Product;

import java.util.List;

/**
 * Product Service
 *
 * @author fahrur
 * @version 1.0
 */
public interface ProductService {

    /**
     * Create Product
     * @param product the product
     * @return the product
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public Product create(Product product) throws ServiceException, DatabasePersistenceException;

    /**
     * Update the product
     * @param product the product
     * @return the product
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Product update(Product product) throws ServiceException, DatabasePersistenceException;

    /**
     * Get the product
     * @param id the id
     * @return the product
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Product get(Long id) throws ServiceException, DatabasePersistenceException;

    /**
     * Get all product
     * @param isActive the isActive
     * @return the product
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public List<Product> getAll(Boolean isActive) throws ServiceException, DatabasePersistenceException;
}
