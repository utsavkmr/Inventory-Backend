package kyro.inventory.web;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.PurchaseService;
import kyro.inventory.model.OrderDetails;
import kyro.inventory.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by fahrur on 1/3/2017.
 */
@RestController
@RequestMapping("api/purchaseReturns")
public class PurchaseReturnRestController {
    @Autowired
    PurchaseService purchaseService;

    @RequestMapping(value = "/{id}", method= RequestMethod.PUT)
    public void update(@RequestBody List<OrderDetails> orderDetailsList, @PathVariable long id)
            throws ServiceException,DatabasePersistenceException {

        Purchase purchase = purchaseService.get(id);

        purchaseService.updatePurchaseReturn(orderDetailsList,purchase);

    }
}
