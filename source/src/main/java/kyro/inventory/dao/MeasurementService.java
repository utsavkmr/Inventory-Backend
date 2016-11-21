package kyro.inventory.dao;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.model.Measurement;

import java.util.List;

/**
 * Measurement Service
 *
 * @author fahrur
 * @version 1.0
 */
public interface MeasurementService {

    /**
     * Create Measurement
     * @param measurement the measurement
     * @return the measurement
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public Measurement create(Measurement measurement) throws ServiceException, DatabasePersistenceException;

    /**
     * Update the measurement
     * @param measurement the measurement
     * @return the measurement
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Measurement update(Measurement measurement) throws ServiceException, DatabasePersistenceException;

    /**
     * Get the measurement
     * @param id the measurement
     * @return the measurement
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Measurement get(Long id) throws ServiceException, DatabasePersistenceException;

    /**
     * Get all measurement
     * @param isActive the measurement
     * @return the measurement
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public List<Measurement> getAll(Boolean isActive) throws ServiceException, DatabasePersistenceException;
}
