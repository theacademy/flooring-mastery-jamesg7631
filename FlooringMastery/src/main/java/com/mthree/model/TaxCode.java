package com.mthree.model;

import java.math.BigDecimal;

public class TaxCode {
    // Why do I have taxCode I don't think its mentioned.
    // Should I not have the state abbreviation instead
    private String taxCode;
    private String state;
    private BigDecimal taxRate;

    public TaxCode(String taxCode, String state, BigDecimal taxRate) {
        this.taxCode = taxCode;
        this.state = state;
        this.taxRate = taxRate;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
