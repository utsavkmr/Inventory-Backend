package kyro.inventory.dao.impl;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.ProductService;
import kyro.inventory.model.BaseSearchParameters;
import kyro.inventory.model.Product;
import kyro.inventory.model.ProductSearchCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

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

    public List<Product> search(ProductSearchCriteria criteria)
            throws ServiceException, DatabasePersistenceException {

        Integer page = criteria.page;
        Integer limit = criteria.limit;

        List<Product> ret = null;

        final String signature = CLASS_NAME + ".search(S criteria)";

        String className = "Product";

        try {
            StringBuffer sb = new StringBuffer("SELECT e FROM ").append(className).append(" e ");
            StringBuffer sbWhere = new StringBuffer("WHERE (1=1)");
            sbWhere.append(" AND e.isActive = true");

            if(criteria.name !=null ) {
                sbWhere.append(" AND e.name LIKE :name");
            }

            if(criteria.productCategoryId != null) {
                sbWhere.append(" AND e.productCategory.id = :productCategoryId");
            }

            sb.append(sbWhere);

            // Append ORDER BY clause
            sb.append(" ORDER BY ").append("id").append(" ").append("asc");

            // Create query
            TypedQuery<Product> query = getEntityManager().createQuery(sb.toString(), Product.class);

            if(criteria.name!=null) {
                query.setParameter("name","%"+criteria.name+"%");
            }

            if(criteria.productCategoryId != null) {
                query.setParameter("productCategoryId",criteria.productCategoryId);
            }

            query.setFirstResult((page-1) * limit);
            query.setMaxResults(limit);

            // Execute the query
            ret = query.getResultList();

            String sql = "SELECT COUNT(e) FROM "+className+" e WHERE e.isActive = true";
            Query q = getEntityManager().createQuery(sql);
            Long count = (Long) q.getSingleResult();

            criteria.totalRow = count;
            criteria.totalPage = getTotalPage(criteria.totalRow, criteria);

        } catch (IllegalStateException e) {
            throw ServiceHelper.logException(getLogger(), signature, new ServiceException(
                    "Entity manager is closed when searching entities", e));
        }

        return ret;

    }

}
