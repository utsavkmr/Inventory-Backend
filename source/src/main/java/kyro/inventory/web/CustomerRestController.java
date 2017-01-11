package kyro.inventory.web;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.CustomerService;
import kyro.inventory.model.Country;
import kyro.inventory.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by fahrur on 1/11/2017.
 */
@RestController
@RequestMapping("api/customers")
public class CustomerRestController extends BaseRestController {

    @Autowired
    CustomerService customerService;

    @RequestMapping("")
    public List<Customer> search() throws ServiceException, DatabasePersistenceException {
        List<Customer> customers = customerService.getAll(false);
        return customers;
    }
}
