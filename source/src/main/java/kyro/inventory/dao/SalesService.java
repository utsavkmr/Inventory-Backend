package kyro.inventory.dao;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.model.Sales;

/**
 * Created by fahrur on 1/9/2017.
 */
public interface SalesService {

    public Sales create(Sales sales) throws ServiceException, DatabasePersistenceException;
    public Sales update(Sales entity) throws ServiceException, DatabasePersistenceException;
}
