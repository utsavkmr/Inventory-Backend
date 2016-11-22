package kyro.inventory.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;

import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import kyro.inventory.model.City;

/**
 * City REST Test
 *
 * @author fahrur
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"/kyro/inventory/web/rest-context.xml"})
@SqlGroup({ @Sql(scripts = "/kyro/inventory/dao/beforeTestRun.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)})
public class CityRestTest extends BaseRestTest {
    /**
     * Represents the web application context.
     */
    @Autowired
    private WebApplicationContext wac;

    /**
     * Represents the mock MVC.
     */
    private MockMvc mockMvc;

    /**
     * Sets up testing environment. It initializes the mock MVC.
     */
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /**
     * Test Get all cities
     * @throws Exception the exception
     */
    @Test
    public void testGetCitiesRest() throws Exception {

        mockMvc.perform(get("/api/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("test"));

    }

    /**
     * Test Get the city
     * @throws Exception the exception
     */
    @Test
    public void testGetCityRest() throws Exception {

        mockMvc.perform(get("/api/cities/"+longTest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));

    }

    /**
     * Test create city rest
     * @throws Exception the exception
     */
    @Test
    public void testCreateCityRest() throws Exception {
        City city = new City();
        city.setName(stringTest);
        city.setIsActive(boolTest);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/cities").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(city)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));

    }

    /**
     * Test update city rest
     * @throws Exception the exception
     */
    @Test
    public void testUpdateCityRest() throws Exception {
        City city = cityService.get(longTest);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(put("/api/cities/"+longTest).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(city)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));

    }
}
