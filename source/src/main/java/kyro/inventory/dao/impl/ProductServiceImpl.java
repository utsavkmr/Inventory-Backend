package kyro.inventory.dao.impl;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.ProductService;
import kyro.inventory.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Product Service Impl
 *
 * @author fahrur
 * @version 1.0
 */
@Service
@Transactional
public class ProductServiceImpl extends BaseServiceImpl<Product>
        implements ProductService {

    /**
     * Constructor
     */
    public ProductServiceImpl() {
        super(Product.class);
    }

    /**
     * Create Product
     * @param product the product
     * @return the product
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public Product create(Product product) throws ServiceException, DatabasePersistenceException {
        genericAuditableCreate(product);
        return product;
    }

    /**
     * Update the product
     * @param product the product
     * @return the product
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Product update(Product product) throws ServiceException, DatabasePersistenceException {
        genericAuditableUpdate(product);
        return product;
    }

}
