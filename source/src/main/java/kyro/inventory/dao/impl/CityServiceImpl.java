package kyro.inventory.dao.impl;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.CityService;
import kyro.inventory.model.City;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static kyro.inventory.dao.impl.ServiceHelper.*;

/**
 * City Service Impl
 *
 * @author fahrur
 * @version 1.0
 */
@Service
@Transactional
public class CityServiceImpl extends BaseServiceImpl<City>
        implements CityService {

    /**
     * Constructor
     */
    public CityServiceImpl() {
        super(City.class);
    }

    /**
     * Create City
     * @param city the city
     * @return the city
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public City create(City city) throws ServiceException, DatabasePersistenceException {
        genericAuditableCreate(city);
        return city;
    }

    /**
     * Update the city
     * @param city the city
     * @return the city
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public City update(City city) throws ServiceException, DatabasePersistenceException {
        genericAuditableUpdate(city);
        return city;
    }

}
