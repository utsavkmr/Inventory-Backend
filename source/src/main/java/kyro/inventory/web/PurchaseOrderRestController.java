package kyro.inventory.web;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.ProductService;
import kyro.inventory.dao.PurchaseService;
import kyro.inventory.model.Product;
import kyro.inventory.model.ProductSearchCriteria;
import kyro.inventory.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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



}
