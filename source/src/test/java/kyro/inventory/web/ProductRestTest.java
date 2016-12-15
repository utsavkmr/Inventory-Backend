package kyro.inventory.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import kyro.inventory.model.ProductSearchCriteria;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by fahrur on 12/2/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"/kyro/inventory/web/rest-context.xml"})
@SqlGroup({ @Sql(scripts = "/kyro/inventory/dao/beforeTestRun.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)})
public class ProductRestTest extends BaseRestTest {

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

    @Test
    public void testGetProductRest() throws Exception {

        mockMvc.perform(get("/api/products/"+longTest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));

    }

    @Test
    public void testSearchProductRest() throws Exception {

        ProductSearchCriteria criteria = new ProductSearchCriteria();
        criteria.page = 1;
        criteria.limit = 10;

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/products/search").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(criteria)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("test"));

    }

    @Test
    public void testSearchProductNameRest() throws Exception {

        ProductSearchCriteria criteria = new ProductSearchCriteria();
        criteria.page = 1;
        criteria.limit = 10;
        criteria.name = "test";
        criteria.productCategoryId = 1L;

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/products/search").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(criteria)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("test"));

    }
}
