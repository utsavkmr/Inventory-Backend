package kyro.inventory.dao.impl;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.CityService;
import kyro.inventory.dao.ProductCategoryService;
import kyro.inventory.model.ProductCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static kyro.inventory.dao.impl.ServiceHelper.*;

/**
 * Product Category Service Impl
 *
 * @author fahrur
 * @version 1.0
 */
@Service
@Transactional
public class ProductCategoryServiceImpl  extends BaseServiceImpl<ProductCategory>
        implements ProductCategoryService {
    /**
     * Constructor
     */
    public ProductCategoryServiceImpl() {
        super(ProductCategory.class);
    }

    /**
     * Create ProductCategory
     * @param productCat the productCat
     * @return the productCat
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public ProductCategory create(ProductCategory productCat) throws ServiceException, DatabasePersistenceException {
        genericAuditableCreate(productCat);
        return productCat;
    }

    /**
     * Update the productCat
     * @param productCat the productCat
     * @return the productCat
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public ProductCategory update(ProductCategory productCat) throws ServiceException, DatabasePersistenceException {
        genericAuditableUpdate(productCat);
        return productCat;
    }
}
