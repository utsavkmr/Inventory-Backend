package kyro.inventory.dao.impl;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.CityService;
import kyro.inventory.dao.MeasurementService;
import kyro.inventory.model.Measurement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Measurement Service Impl
 *
 * @author fahrur
 * @version 1.0
 */
@Service
@Transactional
public class MeasurementServiceImpl extends BaseServiceImpl<Measurement>
        implements MeasurementService {

    /**
     * Constructor
     */
    public MeasurementServiceImpl() {
        super(Measurement.class);
    }

    /**
     * Create Measurement
     * @param measurement the measurement
     * @return the measurement
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public Measurement create(Measurement measurement) throws ServiceException, DatabasePersistenceException {
        genericAuditableCreate(measurement);
        return measurement;
    }

    /**
     * Update the measurement
     * @param measurement the measurement
     * @return the measurement
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Measurement update(Measurement measurement) throws ServiceException, DatabasePersistenceException {
        genericAuditableUpdate(measurement);
        return measurement;
    }

}
