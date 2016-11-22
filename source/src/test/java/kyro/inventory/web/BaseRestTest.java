package kyro.inventory.web;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManagerFactory;

import kyro.inventory.dao.CityService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by fahrur on 11/22/2016.
 */
public abstract class BaseRestTest {

    protected final String stringTest = "test";

    protected final Long longTest = 1L;

    protected final Double doubleTest = 1.0;

    protected final Boolean boolTest = true;

    protected final Date dateTest = new Date(115,1,1);

    protected final Long IdTest = 1L;

    @Autowired protected CityService cityService;

}
