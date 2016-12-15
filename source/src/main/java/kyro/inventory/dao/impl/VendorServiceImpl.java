package kyro.inventory.dao.impl;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.VendorService;
import kyro.inventory.model.BaseSearchParameters;
import kyro.inventory.model.Vendor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Vendor Service Impl
 *
 * @author fahrur
 * @version 1.0
 */
@Service
@Transactional
public class VendorServiceImpl extends BaseServiceImpl<Vendor>
        implements VendorService {

    /**
     * Constructor
     */
    public VendorServiceImpl() {
        super(Vendor.class);
    }

    /**
     * Create City
     * @param vendor the vendor
     * @return the vendor
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public Vendor create(Vendor vendor) throws ServiceException, DatabasePersistenceException {
        genericAuditableCreate(vendor);
        return vendor;
    }

    /**
     * Update the vendor
     * @param vendor the vendor
     * @return the vendor
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Vendor update(Vendor vendor) throws ServiceException, DatabasePersistenceException {
        genericAuditableUpdate(vendor);
        return vendor;
    }

    public List<Vendor> search(BaseSearchParameters criteria)
            throws ServiceException, DatabasePersistenceException {

        Integer page = criteria.page;
        Integer limit = criteria.limit;

        List<Vendor> ret = null;

        final String signature = CLASS_NAME + ".search(S criteria)";

        String className = "Vendor";

        try {
            StringBuffer sb = new StringBuffer("SELECT e FROM ").append(className).append(" e ");
            StringBuffer sbWhere = new StringBuffer("WHERE (1=1)");
            sbWhere.append(" AND e.isActive = true");

            if(criteria.name !=null ) {
                sbWhere.append(" AND e.name LIKE :name");
            }

            sb.append(sbWhere);

            // Append ORDER BY clause
            sb.append(" ORDER BY ").append("id").append(" ").append("asc");

            // Create query
            TypedQuery<Vendor> query = getEntityManager().createQuery(sb.toString(), Vendor.class);

            if(criteria.name!=null) {
                query.setParameter("name","%"+criteria.name+"%");
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
