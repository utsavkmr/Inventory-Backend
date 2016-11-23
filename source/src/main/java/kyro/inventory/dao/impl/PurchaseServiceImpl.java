package kyro.inventory.dao.impl;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.PurchaseService;
import kyro.inventory.model.OrderDetails;
import kyro.inventory.model.Purchase;
import org.omg.CORBA.Object;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

/**
 * Purchase Service Impl
 *
 * @author fahrur
 * @version 1.0
 */
@Service
@Transactional
public class PurchaseServiceImpl extends BaseServiceImpl<Purchase>
        implements PurchaseService {

    /**
     * Constructor
     */
    public PurchaseServiceImpl() {
        super(Purchase.class);
    }

    /**
     * Create Purchase
     * @param purchase the purchase
     * @return the purchase
     * @throws ServiceException the exceptionn
     * @throws DatabasePersistenceException the exception
     */
    public Purchase create(Purchase purchase) throws ServiceException, DatabasePersistenceException {
        entityManager.persist(purchase);
        qtyBalanceOnCreate(purchase);
        return purchase;
    }

    /**
     * Update the purchase
     * @param purchase the purchase
     * @return the purchase
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    public Purchase update(Purchase purchase) throws ServiceException, DatabasePersistenceException {
        entityManager.merge(purchase);
        return purchase;
    }

    public void qtyBalanceOnCreate(Purchase purchase) throws ServiceException, DatabasePersistenceException {
        Long locationId = purchase.getLocation().getId();

        if(purchase.getOrders()!=null && purchase.getOrders().size()>0) {
            for (OrderDetails orderDetails : purchase.getOrders()) {

                Long productId = orderDetails.getProduct().getId();

                Query q = entityManager.createNativeQuery("SELECT * FROM inv_account a WHERE a.productId = ? AND a.locationId AND stockBalanceType = 'ORDER'");
                q.setParameter(1, productId);
                q.setParameter(1, locationId);
                List accounts = q.getResultList();
                if(accounts.size()==0) {
                    System.out.println("Null");
                }
            }
        }

    }

}
