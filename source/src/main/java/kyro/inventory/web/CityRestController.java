package kyro.inventory.web;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.CityService;
import kyro.inventory.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * City REST Controller
 *
 * @author fahrur
 * @version 1.0
 */
@RestController
@RequestMapping("api/cities")
public class CityRestController extends BaseRestController {

    /**
     * City Service
     */
    @Autowired
    CityService cityService;

    /**
     * Get All Cities
     * @return the cities
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping("")
    public List<City> search() throws ServiceException, DatabasePersistenceException {
        List<City> cities = cityService.getAll(false);
        return cities;
    }

    /**
     * Get the city
     * @param id the id
     * @return the city
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public City get(@PathVariable long id) throws ServiceException,
            DatabasePersistenceException {
        City city = cityService.get(id);
        return city;
    }

    /**
     * Create the city
     * @param city the city
     * @return the city
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "", method= RequestMethod.POST)
    public City create(@RequestBody City city) throws ServiceException,
            DatabasePersistenceException {
        cityService.create(city);
        return city;
    }

    /**
     * Update the city
     * @param city the city
     * @param id the id
     * @return the city
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.PUT)
    public City update(@RequestBody City city,@PathVariable long id) throws ServiceException,
            DatabasePersistenceException {
        cityService.update(city);
        return city;
    }

}
