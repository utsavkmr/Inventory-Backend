package kyro.inventory.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * BaseSearchParameters
 *
 * @author fahrur
 * @version 1.0
 */
public class BaseSearchParameters {

    public Integer page;

    public Integer limit;

    public String sort;

    public SortType order;

    public Map<String, String> filters = new HashMap<String, String>();

    public BaseSearchParameters() {
    }

}
