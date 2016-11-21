package kyro.inventory.dao.impl;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.CityService;
import kyro.inventory.dao.CountryService;
import kyro.inventory.model.Country;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static kyro.inventory.dao.impl.ServiceHelper.*;

/**
 * Country Service Impl
 *
 * @author fahrur
 * @version 1.0
 */
@Service
@Transactional
public class CountryServiceImpl extends BaseServiceImpl<Country>
        implements CountryService {

    /**
     * Constructor
     */
    public CountryServiceImpl() {
        super(Country.class);
    }

    /**
     * Create Country
     * @param country the country
     * @return the country
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public Country create(Country country) throws ServiceException, DatabasePersistenceException {
        genericAuditableCreate(country);
        return country;
    }

    /**
     * Update the country
     * @param country the country
     * @return the country
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Country update(Country country) throws ServiceException, DatabasePersistenceException {
        genericAuditableUpdate(country);
        return country;
    }
}
