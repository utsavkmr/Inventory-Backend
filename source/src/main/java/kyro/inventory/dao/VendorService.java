package kyro.inventory.dao;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.model.BaseSearchParameters;
import kyro.inventory.model.Vendor;

import java.util.List;

/**
 * Vendor Service
 *
 * @author fahrur
 * @version 1.0
 */
public interface VendorService {

    /**
     * Create Vendor
     * @param vendor the vendor
     * @return the vendor
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public Vendor create(Vendor vendor) throws ServiceException, DatabasePersistenceException;

    /**
     * Update the vendor
     * @param vendor the vendor
     * @return the vendor
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Vendor update(Vendor vendor) throws ServiceException, DatabasePersistenceException;

    /**
     * Get the vendor
     * @param id the vendor
     * @return the vendor
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Vendor get(Long id) throws ServiceException, DatabasePersistenceException;

    /**
     * Get all vendor
     * @param isActive the vendor
     * @return the vendor
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public List<Vendor> getAll(Boolean isActive) throws ServiceException, DatabasePersistenceException;

    public List<Vendor> search(BaseSearchParameters criteria) throws ServiceException, DatabasePersistenceException;
}
