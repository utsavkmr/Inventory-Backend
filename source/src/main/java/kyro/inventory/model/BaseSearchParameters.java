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

    /**
     * Page
     */
    public Integer page;

    /**
     * Limit
     */
    public Integer limit;

    /**
     * Sort
     */
    public String sort;

    /**
     * Order
     */
    public SortType order;

    /**
     * Total Row
     */
    public Long totalRow;

    /**
     * Total Page
     */
    public Long totalPage;

    /**
     * Name
     */
    public String name;

    /**
     * Empty Constructor
     */
    public BaseSearchParameters() {
    }

}
