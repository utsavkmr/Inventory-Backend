package kyro.inventory.web;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.PurchaseService;
import kyro.inventory.model.Purchase;
import kyro.inventory.model.ReceiveDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by fahrur on 12/20/2016.
 */
@RestController
@RequestMapping("api/purchaseReceives")
public class PurchaseReceiveRestController {

    @Autowired
    PurchaseService purchaseService;

    @RequestMapping(value = "/{id}", method= RequestMethod.PUT)
    public void update(@RequestBody List<ReceiveDetails> receiveDetailsList, @PathVariable long id)
            throws ServiceException,DatabasePersistenceException {

        Purchase purchase = purchaseService.get(id);

        purchaseService.updatePurchaseReceived(receiveDetailsList,purchase);

    }

}
