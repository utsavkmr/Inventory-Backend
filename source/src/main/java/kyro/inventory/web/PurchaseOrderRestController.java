package kyro.inventory.web;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.ProductService;
import kyro.inventory.dao.PurchaseService;
import kyro.inventory.dao.impl.ServiceHelper;
import kyro.inventory.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * City REST Controller
 *
 * @author fahrur
 * @version 1.0
 */
@RestController
@RequestMapping("api/purchaseOrders")
public class PurchaseOrderRestController extends BaseRestController {

    /**
     * Product Category Service
     */
    @Autowired
    PurchaseService purchaseService;

    @RequestMapping(value = "/search", method= RequestMethod.POST)
    public List<Purchase> search(@RequestBody PurchaseSearchCriteria criteria, HttpServletResponse response)
            throws ServiceException, DatabasePersistenceException
    {
        List<Purchase> purchases = purchaseService.search(criteria);
        ServiceHelper.setSearchResponseTotalPage(response, criteria);
        return purchases;
    }


    /**
     * Create the Product
     * @param purchase the Product
     * @return the Product
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "", method= RequestMethod.POST)
    public Purchase create(@RequestBody Purchase purchase) throws ServiceException,
            DatabasePersistenceException {
        purchase.setPaid(false);
        purchase.setClosed(false);
        purchase.setFulfilled(false);

        StringBuilder errorMessage = new StringBuilder();
        Boolean isValid = purchaseService.validateCreate(purchase,errorMessage);

        if(isValid==false) {
            throw new ServiceException(errorMessage.toString());
        }

        purchaseService.create(purchase);
        return purchase;
    }

    @RequestMapping(value = "/{id}", method= RequestMethod.PUT)
    public Purchase update(@RequestBody Purchase purchase,@PathVariable long id)
            throws ServiceException,DatabasePersistenceException {

        purchaseService.update(purchase);

        return purchase;
    }

    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public Purchase get(@PathVariable long id) throws ServiceException,
            DatabasePersistenceException {
        Purchase purchase = purchaseService.get(id);

        Boolean receiving = false;

        if(purchase.getOrders()!=null) {
            for(OrderDetails orderDetails : purchase.getOrders()) {
                if(
                    orderDetails.getReturnDetails()!=null ||
                    orderDetails.getReceiveDetails()!=null
                    ) {

                    receiving = true;
                    break;

                }
            }
        }

        purchase.setReceiving(receiving);

        return purchase;
    }




}
