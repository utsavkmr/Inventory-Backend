package kyro.inventory.web;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.dao.MeasurementService;
import kyro.inventory.model.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Measurement REST Controller
 *
 * @author fahrur
 * @version 1.0
 */
@RestController
@RequestMapping("api/measurements")
public class MeasurementRestController extends BaseRestController {

    /**
     * City Service
     */
    @Autowired
    MeasurementService measurementService;

    /**
     * Get All Measurements
     * @return the measurements
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping("")
    public List<Measurement> search() throws ServiceException, DatabasePersistenceException {
        List<Measurement> measurements = measurementService.getAll(false);
        return measurements;
    }

    /**
     * Get the measurement
     * @param id the id
     * @return the measurement
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public Measurement get(@PathVariable long id) throws ServiceException,
            DatabasePersistenceException {
        Measurement measurement = measurementService.get(id);
        return measurement;
    }

    /**
     * Create the measurement
     * @param measurement the measurement
     * @return the measurement
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "", method= RequestMethod.POST)
    public Measurement create(@RequestBody Measurement measurement) throws ServiceException,
            DatabasePersistenceException {
        measurementService.create(measurement);
        return measurement;
    }

    /**
     * Update the measurement
     * @param measurement the measurement
     * @param id the id
     * @return the measurement
     * @throws ServiceException the exception
     * @throws DatabasePersistenceException the exception
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.PUT)
    public Measurement update(@RequestBody Measurement measurement,@PathVariable long id) throws ServiceException,
            DatabasePersistenceException {
        measurementService.update(measurement);
        return measurement;
    }

}
