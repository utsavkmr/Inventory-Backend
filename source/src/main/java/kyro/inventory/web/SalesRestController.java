package kyro.inventory.web;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.SalesService;
import kyro.inventory.model.Purchase;
import kyro.inventory.model.Sales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fahrur on 1/11/2017.
 */
@RestController
@RequestMapping("api/sales")
public class SalesRestController {

    @Autowired
    SalesService salesService;

    @RequestMapping(value = "", method= RequestMethod.POST)
    public Sales create(@RequestBody Sales sales) throws ServiceException,
            DatabasePersistenceException {
        sales.setPaid(false);
        sales.setClosed(false);

        StringBuilder errorMessage = new StringBuilder();
        //Boolean isValid = salesService.validateCreate(sales,errorMessage);

        /*
        if(isValid==false) {
            throw new ServiceException(errorMessage.toString());
        }
        */

        salesService.create(sales);
        return sales;
    }

}
