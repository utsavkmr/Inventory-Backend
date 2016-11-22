package kyro.inventory.web;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.LocationService;
import kyro.inventory.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Location REST Controller
 *
 * @author fahrur
 * @version 1.0
 */
@RestController
@RequestMapping("api/locations")
public class LocationRestController extends BaseRestController {

    /**
     * Location Service
     */
    @Autowired
    LocationService locationService;

    /**
     * Get All Locations
     * @return the locations
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping("")
    public List<Location> search() throws ServiceException, DatabasePersistenceException {
        List<Location> locations = locationService.getAll(false);
        return locations;
    }

    /**
     * Get the location
     * @param id the id
     * @return the location
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public Location get(@PathVariable long id) throws ServiceException,
            DatabasePersistenceException {
        Location location = locationService.get(id);
        return location;
    }

    /**
     * Create the location
     * @param location the location
     * @return the location
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "", method= RequestMethod.POST)
    public Location create(@RequestBody Location location) throws ServiceException,
            DatabasePersistenceException {
        locationService.create(location);
        return location;
    }

    /**
     * Update the location
     * @param location the location
     * @param id the id
     * @return the location
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.PUT)
    public Location update(@RequestBody Location location,@PathVariable long id) throws ServiceException,
            DatabasePersistenceException {
        locationService.update(location);
        return location;
    }

}
