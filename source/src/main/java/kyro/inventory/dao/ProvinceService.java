package kyro.inventory.dao;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.model.Province;

import java.util.List;

/**
 * Province Service
 *
 * @author fahrur
 * @version 1.0
 */
public interface ProvinceService {

    /**
     * Create Province
     * @param province the province
     * @return the province
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public Province create(Province province) throws ServiceException, DatabasePersistenceException;

    /**
     * Update the province
     * @param province the province
     * @return the province
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Province update(Province province) throws ServiceException, DatabasePersistenceException;

    /**
     * Get the province
     * @param id the province
     * @return the province
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Province get(Long id) throws ServiceException, DatabasePersistenceException;

    /**
     * Get all province
     * @param isActive the province
     * @return the province
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public List<Province> getAll(Boolean isActive) throws ServiceException, DatabasePersistenceException;
}
