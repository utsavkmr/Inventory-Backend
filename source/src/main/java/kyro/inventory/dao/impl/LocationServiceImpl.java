package kyro.inventory.dao.impl;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.LocationService;
import kyro.inventory.model.Location;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Location Service Impl
 *
 * @author fahrur
 * @version 1.0
 */
@Service
@Transactional
public class LocationServiceImpl extends BaseServiceImpl<Location>
        implements LocationService {

    /**
     * Constructor
     */
    public LocationServiceImpl() {
        super(Location.class);
    }

    /**
     * Create Location
     * @param location the location
     * @return the location
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public Location create(Location location) throws ServiceException, DatabasePersistenceException {
        genericAuditableCreate(location);
        return location;
    }

    /**
     * Update the location
     * @param location the location
     * @return the location
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Location update(Location location) throws ServiceException, DatabasePersistenceException {
        genericAuditableUpdate(location);
        return location;
    }

}
