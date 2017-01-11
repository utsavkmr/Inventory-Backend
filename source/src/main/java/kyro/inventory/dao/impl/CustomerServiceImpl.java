package kyro.inventory.dao.impl;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.CustomerService;
import kyro.inventory.dao.VendorService;
import kyro.inventory.model.Customer;
import kyro.inventory.model.Vendor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fahrur on 1/9/2017.
 */
@Service
@Transactional
public class CustomerServiceImpl extends BaseServiceImpl<Customer>
        implements CustomerService {
    /**
     * Constructor
     */
    public CustomerServiceImpl() {
        super(Customer.class);
    }

    /**
     * Create customer
     * @param customer the customer
     * @return the customer
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public Customer create(Customer customer) throws ServiceException, DatabasePersistenceException {
        genericAuditableCreate(customer);
        return customer;
    }

    /**
     * Update the customer
     * @param customer the customer
     * @return the customer
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Customer update(Customer customer) throws ServiceException, DatabasePersistenceException {
        genericAuditableUpdate(customer);
        return customer;
    }
}
