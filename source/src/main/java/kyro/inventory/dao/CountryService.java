package kyro.inventory.dao;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.model.City;
import kyro.inventory.model.Country;

import java.util.List;

/**
 * Country Service
 *
 * @author fahrur
 * @version 1.0
 */
public interface CountryService {

    /**
     * Create Country
     * @param country the country
     * @return the country
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public Country create(Country country) throws ServiceException, DatabasePersistenceException;

    /**
     * Update the country
     * @param country the country
     * @return the country
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Country update(Country country) throws ServiceException, DatabasePersistenceException;

    /**
     * Get the country
     * @param id the country
     * @return the country
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Country get(Long id) throws ServiceException, DatabasePersistenceException;

    /**
     * Get all country
     * @param isActive the country
     * @return the country
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public List<Country> getAll(Boolean isActive) throws ServiceException, DatabasePersistenceException;
}
