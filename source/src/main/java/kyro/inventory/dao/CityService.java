package kyro.inventory.dao;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.model.City;

import java.util.List;

/**
 * City Service
 *
 * @author fahrur
 * @version 1.0
 */
public interface CityService {

    /**
     * Create City
     * @param city the city
     * @return the city
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public City create(City city) throws ServiceException, DatabasePersistenceException;

    /**
     * Update the city
     * @param city the city
     * @return the city
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public City update(City city) throws ServiceException, DatabasePersistenceException;

    /**
     * Get the city
     * @param id the city
     * @return the city
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public City get(Long id) throws ServiceException, DatabasePersistenceException;

    /**
     * Get all city
     * @param isActive the city
     * @return the city
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public List<City> getAll(Boolean isActive) throws ServiceException, DatabasePersistenceException;
}
