package com.mthree.model;

import java.math.BigDecimal;

public class Product {
    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal taxRate;

    public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal taxRate) {
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
