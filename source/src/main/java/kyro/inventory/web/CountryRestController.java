package kyro.inventory.web;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.CountryService;
import kyro.inventory.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Country REST Controller
 *
 * @author fahrur
 * @version 1.0
 */
@RestController
@RequestMapping("api/countries")
public class CountryRestController extends BaseRestController {

    /**
     * Country Service
     */
    @Autowired
    CountryService countryService;

    /**
     * Get All Countries
     * @return the countries
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping("")
    public List<Country> search() throws ServiceException, DatabasePersistenceException {
        List<Country> countries = countryService.getAll(false);
        return countries;
    }

    /**
     * Get the country
     * @param id the id
     * @return the country
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public Country get(@PathVariable long id) throws ServiceException,
            DatabasePersistenceException {
        Country country = countryService.get(id);
        return country;
    }

    /**
     * Create the country
     * @param country the country
     * @return the country
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "", method= RequestMethod.POST)
    public Country create(@RequestBody Country country) throws ServiceException,
            DatabasePersistenceException {
        countryService.create(country);
        return country;
    }

    /**
     * Update the country
     * @param country the country
     * @param id the id
     * @return the country
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.PUT)
    public Country update(@RequestBody Country country,@PathVariable long id) throws ServiceException,
            DatabasePersistenceException {
        countryService.update(country);
        return country;
    }

}
