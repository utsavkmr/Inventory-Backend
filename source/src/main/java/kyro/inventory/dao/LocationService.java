package kyro.inventory.dao;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.model.Location;

import java.util.List;

/**
 * Location Service
 *
 * @author fahrur
 * @version 1.0
 */
public interface LocationService {

    /**
     * Create Location
     * @param location the location
     * @return the location
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public Location create(Location location) throws ServiceException, DatabasePersistenceException;

    /**
     * Update the location
     * @param location the location
     * @return the location
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Location update(Location location) throws ServiceException, DatabasePersistenceException;

    /**
     * Get the location
     * @param id the location
     * @return the location
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Location get(Long id) throws ServiceException, DatabasePersistenceException;

    /**
     * Get all location
     * @param isActive the location
     * @return the location
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public List<Location> getAll(Boolean isActive) throws ServiceException, DatabasePersistenceException;
}
