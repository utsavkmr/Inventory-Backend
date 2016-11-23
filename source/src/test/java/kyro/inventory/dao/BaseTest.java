package kyro.inventory.dao;

import kyro.inventory.model.ActiveAuditableEntity;
import kyro.inventory.model.Country;
import kyro.inventory.model.LoginUserThread;
import kyro.inventory.model.UserLogin;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManagerFactory;

import static org.junit.Assert.assertEquals;

import java.util.*;

/**
 * Created by fahrur on 11/21/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:kyro/inventory/dao/TestContext.xml")
@SqlGroup({
        @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:kyro/inventory/dao/beforeTestRun.sql")
})
public abstract class BaseTest {

    protected final String stringTest = "test";

    protected final Long longTest = 1L;

    protected final Double doubleTest = 1.0;

    protected final Boolean boolTest = true;

    protected final Date dateTest = new Date(115,0,1);

    protected final Long IdTest = 1L;

    @Autowired
    CityService cityService;

    @Autowired
    CountryService countryService;

    @Autowired
    ProvinceService provinceService;

    @Autowired
    VendorService vendorService;

    @Autowired
    ProductService productService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    LocationService locationService;

    @Before
    public void before() {
        UserLogin userLogin = new UserLogin();
        userLogin.setId(1L);
        userLogin.setUsername(stringTest);

        LoginUserThread.loginUser.set(userLogin);
    }

}
