package kyro.inventory.dao.impl;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.VendorService;
import kyro.inventory.model.Vendor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
