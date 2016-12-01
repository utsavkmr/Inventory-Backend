package kyro.inventory.dao;

import kyro.inventory.DatabasePersistenceException;
import kyro.inventory.ServiceException;
import kyro.inventory.model.City;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.junit.Assert.*;

import java.util.List;

/**
 * Created by fahrur on 11/21/2016.
 */
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:kyro/inventory/dao/beforeTestRun.sql")
})
public class CityServiceTest extends BaseTest {

    @Test
    public void createCityTest() throws ServiceException, DatabasePersistenceException {
        City city = new City();
        city.setName(stringTest);
        city.setIsActive(true);

        cityService.create(city);
    }

    @Test
    public void updateCityTest() throws ServiceException, DatabasePersistenceException {
        City city = new City();
        city.setName(stringTest);
        city.setIsActive(true);

        cityService.create(city);

        city.setName(stringTest);
        cityService.update(city);
    }

    @Test
    public void getCityTest() throws ServiceException, DatabasePersistenceException {
        City city = new City();
        city.setName(stringTest);
        city.setIsActive(true);

        cityService.create(city);

        City city2 = cityService.get(city.getId());
    }

    @Test
    public void getAllCityTest() throws ServiceException, DatabasePersistenceException {
        City city = new City();
        city.setName(stringTest);
        city.setIsActive(true);

        cityService.create(city);

        List<City> cities = cityService.getAll(false);
        assertNotNull(cities);
    }

}
