package com.mthree.pojo;

import com.mthree.model.Product;
import com.mthree.model.TaxCode;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductCosts {
    final private BigDecimal area;
    final private TaxCode taxCode;
    final private Product product;

    public ProductCosts(BigDecimal area, TaxCode taxCode, Product product) {
        this.area = area;
        this.taxCode = taxCode;
        this.product = product;
    }

    public BigDecimal getArea() {
        return area;
    }

    public TaxCode getTaxCode() {
        return taxCode;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getMaterialCosts() {
        return this.area.multiply(product.getCostPerSquareFoot());
    }

    public BigDecimal getLaborCosts() {
        return this.area.multiply(product.getLaborCostPerSquareFoot());
    }

    private BigDecimal totalGrossCost() {
        return this.getMaterialCosts().add(this.getLaborCosts());
    }

    public BigDecimal getTaxCost() {
        if (taxCode.getTaxRate().equals(new BigDecimal(0))) {
            return taxCode.getTaxRate();
        }
        // Not sure if this is correct based on the requirements. However, Half even seemed the most sensible
        return this.totalGrossCost().multiply(this.taxCode.getTaxRate().divide(new BigDecimal(100), RoundingMode.HALF_EVEN));
    }

    public BigDecimal getTotalCost() {
        return this.totalGrossCost().add(this.getTaxCost());
    }
}
