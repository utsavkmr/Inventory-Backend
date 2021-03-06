package kyro.inventory.dao;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.model.*;

import java.util.List;

/**
 * Purchase Service
 *
 * @author fahrur
 * @version 1.0
 */
public interface PurchaseService {

    public List<Purchase> search(PurchaseSearchCriteria criteria)
            throws ServiceException, DatabasePersistenceException;

    /**
     * Create Purchase
     * @param purchase the purchase
     * @return the purchase
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public Purchase create(Purchase purchase) throws ServiceException, DatabasePersistenceException;

    /**
     * Update the purchase
     * @param purchase the purchase
     * @return the purchase
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Purchase update(Purchase purchase) throws ServiceException, DatabasePersistenceException;

    /**
     * Get the purchase
     * @param id the purchase
     * @return the purchase
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Purchase get(Long id) throws ServiceException, DatabasePersistenceException;

    /**
     * Get all purchase
     * @param isActive the purchase
     * @return the purchase
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public List<Purchase> getAll(Boolean isActive) throws ServiceException, DatabasePersistenceException;

    public void updatePurchaseReceived(List<OrderDetails> orderDetailsList, Purchase purchase) throws ServiceException, DatabasePersistenceException;

    public void updatePurchaseReturn(List<OrderDetails> returnDetailsList, Purchase purchase) throws ServiceException, DatabasePersistenceException;

    public Boolean validateCreate(Purchase purchase,StringBuilder errorMessages);
}
