package kyro.inventory.dao.impl;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.CityService;
import kyro.inventory.dao.CountryService;
import kyro.inventory.dao.ProvinceService;
import kyro.inventory.model.Province;
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
public class ProvinceServiceImpl  extends BaseServiceImpl<Province>
        implements ProvinceService {

    /**
     * Constructor
     */
    public ProvinceServiceImpl() {
        super(Province.class);
    }

    /**
     * Create Province
     * @param province the province
     * @return the province
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public Province create(Province province) throws ServiceException, DatabasePersistenceException {
        genericAuditableCreate(province);
        return province;
    }

    /**
     * Update the province
     * @param province the province
     * @return the province
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Province update(Province province) throws ServiceException, DatabasePersistenceException {
        genericAuditableUpdate(province);
        return province;
    }
}
