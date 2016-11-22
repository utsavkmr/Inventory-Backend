package kyro.inventory.web;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.ProvinceService;
import kyro.inventory.model.City;
import kyro.inventory.model.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Province REST Controller
 *
 * @author fahrur
 * @version 1.0
 */
@RestController
@RequestMapping("api/provinces")
public class ProvinceRestController extends BaseRestController {

    /**
     * City Service
     */
    @Autowired
    ProvinceService provinceService;

    /**
     * Get All Provinces
     * @return the provinces
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping("")
    public List<Province> search() throws ServiceException, DatabasePersistenceException {
        List<Province> provinces = provinceService.getAll(false);
        return provinces;
    }

    /**
     * Get the province
     * @param id the id
     * @return the province
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public Province get(@PathVariable long id) throws ServiceException,
            DatabasePersistenceException {
        Province province = provinceService.get(id);
        return province;
    }

    /**
     * Create the province
     * @param province the province
     * @return the province
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "", method= RequestMethod.POST)
    public Province create(@RequestBody Province province) throws ServiceException,
            DatabasePersistenceException {
        provinceService.create(province);
        return province;
    }

    /**
     * Update the province
     * @param province the province
     * @param id the id
     * @return the province
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.PUT)
    public Province update(@RequestBody Province province,@PathVariable long id) throws ServiceException,
            DatabasePersistenceException {
        provinceService.update(province);
        return province;
    }

}
