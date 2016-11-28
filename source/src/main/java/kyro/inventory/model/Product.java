package kyro.inventory.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Product
 *
 * @author fahrur
 * @version 1.0
 */
@Entity(name="Product")
@Table(name="product")
public class Product extends ActiveAuditableEntity {

    /**
     * Code
     */
    @Basic
    @NotNull
    private String code;

    /**
     * Barcode
     */
    @Basic
    private String barcode;

    /**
     * Reorder Point
     */
    @Basic
    private Double reorderPoint;

    /**
     * Remarks
     */
    @Basic
    private String remarks;

    /**
     * Standard UOM
     */
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="standardUOMId")
    @NotNull
    private Measurement standardUOM;

    /**
     * Sales UOM
     */
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="salesUOMId")
    @NotNull
    private Measurement salesUOM;

    /**
     * Sales UOM Conversion
     */
    @Basic
    @NotNull
    private Double salesUOMConversion;

    /**
     * Purchase UOM
     */
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="purchaseUOMId")
    @NotNull
    private Measurement purchaseUOM;


    /**
     * Purchase UOM Conversion
     */
    @Basic
    @NotNull
    private Double purchaseUOMConversion;

    /**
     * Cost Price
     */
    @Basic
    @NotNull
    private Double costPrice;

    /**
     * Product Category
     */
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="productCategoryId")
    private ProductCategory productCategory;

    /**
     * Type
     */
    @Basic
    @NotNull
    private ProductType type;

    /**
     * Sales price
     */
    @Basic
    private Double salesPrice;

    /**
     * Sales standard price
     */
    @Basic
    private Double salesStandardPrice;

    /**
     * Constructor
     */
    public Product() {}

    /**
     * Get the code
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Set the code
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Get the barcode
     * @return the barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Set the barcode
     * @param barcode the barcode
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * Get the reorder point
     * @return the reorder point
     */
    public Double getReorderPoint() {
        return reorderPoint;
    }

    /**
     * Set the reorder point
     * @param reorderPoint the reorder point
     */
    public void setReorderPoint(Double reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    /**
     * Get the remarks
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Set the remarks
     * @param remarks the remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * Get the standard UOM
     * @return the standard UOM
     */
    public Measurement getStandardUOM() {
        return standardUOM;
    }

    /**
     * Set the standard UOM
     * @param standardUOM the standard UOM
     */
    public void setStandardUOM(Measurement standardUOM) {
        this.standardUOM = standardUOM;
    }

    /**
     * Get the sales UOM
     * @return the sales UOM
     */
    public Measurement getSalesUOM() {
        return salesUOM;
    }

    /**
     * Set the sales UOM
     * @param salesUOM the sales UOM
     */
    public void setSalesUOM(Measurement salesUOM) {
        this.salesUOM = salesUOM;
    }

    /**
     * Get the sales UOM Conversion
     * @return the sales UOM Conversion
     */
    public Double getSalesUOMConversion() {
        return salesUOMConversion;
    }

    /**
     * Set the sales UOM Conversion
     * @param salesUOMConversion the sales UOM Conversion
     */
    public void setSalesUOMConversion(Double salesUOMConversion) {
        this.salesUOMConversion = salesUOMConversion;
    }

    /**
     * Get the purchase UOM
     * @return the purchase UOM
     */
    public Measurement getPurchaseUOM() {
        return purchaseUOM;
    }

    /**
     * Set the purchase UOM
     * @param purchaseUOM the purchase UOM
     */
    public void setPurchaseUOM(Measurement purchaseUOM) {
        this.purchaseUOM = purchaseUOM;
    }

    /**
     * Get the purchase UOM Conversion
     * @return the purchase UOM Conversion
     */
    public Double getPurchaseUOMConversion() {
        return purchaseUOMConversion;
    }

    /**
     * Set the purchase UOM Conversion
     * @param purchaseUOMConversion the purchase UOM Conversion
     */
    public void setPurchaseUOMConversion(Double purchaseUOMConversion) {
        this.purchaseUOMConversion = purchaseUOMConversion;
    }

    /**
     * Get the cost price
     * @return the cost price
     */
    public Double getCostPrice() {
        return costPrice;
    }

    /**
     * Set the cost price
     * @param costPrice the cost price
     */
    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    /**
     * Get the product category
     * @return the product category
     */
    public ProductCategory getProductCategory() {
        return productCategory;
    }

    /**
     * Set the product category
     * @param productCategory the product category
     */
    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    /**
     * Get the type
     * @return the type
     */
    public ProductType getType() {
        return type;
    }

    /**
     * Set the type
     * @param type the type
     */
    public void setType(ProductType type) {
        this.type = type;
    }

    /**
     * Get the sales price
     * @return the sales price
     */
    public Double getSalesPrice() {
        return salesPrice;
    }

    /**
     * Set the sales price
     * @param salesPrice the sales price
     */
    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    /**
     * Get the sales standard price
     * @return the sales standard price
     */
    public Double getSalesStandardPrice() {
        return salesStandardPrice;
    }

    /**
     * Set the sales standard price
     * @param salesStandardPrice the sales standard price
     */
    public void setSalesStandardPrice(Double salesStandardPrice) {
        this.salesStandardPrice = salesStandardPrice;
    }
}
