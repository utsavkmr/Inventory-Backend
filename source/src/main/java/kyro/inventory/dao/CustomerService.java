package kyro.inventory.dao;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.model.Customer;
import kyro.inventory.model.Vendor;

import java.util.List;

/**
 * Created by fahrur on 1/9/2017.
 */
public interface CustomerService {

    /**
     * Create Customer
     * @param customer the customer
     * @return the customer
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public Customer create(Customer customer) throws ServiceException, DatabasePersistenceException;

    /**
     * Update the customer
     * @param customer the customer
     * @return the customer
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Customer update(Customer customer) throws ServiceException, DatabasePersistenceException;

    /**
     * Get the customer
     * @param id the customer
     * @return the customer
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Customer get(Long id) throws ServiceException, DatabasePersistenceException;

    /**
     * Get all customer
     * @param isActive the customer
     * @return the customer
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public List<Customer> getAll(Boolean isActive) throws ServiceException, DatabasePersistenceException;
}
