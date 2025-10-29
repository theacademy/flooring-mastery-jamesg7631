package com.mthree.model;

import com.mthree.pojo.ProductCosts;

import java.math.BigDecimal;

public class Order {
    // Not sure how to keep the below working with persistence. Got to be careful
    public static int nextOrderNumber = 0;
    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate; // I thought surely taxRate are based on State. However, I guess states can change taxRates so it's important to store the value
    private String productType; // If there is a distinct product range use enums?. Surely an order can have multiple product types
    private BigDecimal area;
    private BigDecimal costPerSquareFoot; // cost of What per square foot. I'm assuming the material?
    private BigDecimal laborCostPerSquareFoot;

    // Probably remove attributes and provide methods. Should be in Service layer
    private BigDecimal materialCost; // I'm guessing this is the costPerSquareFoot * area. If so, do I need a separate attribute
    private BigDecimal laborCost; // I'm assuming this is the laborCostPerSquareFoot * area. If so, do I need a separate attribute
    private BigDecimal tax; // Is this taxRate * total. If so, why do we have a separate attribute. Could lead to inconsistencies
    private BigDecimal total; // If the total is the sum of the other costs. Could have as method and not an attribute

    public Order(int orderNumber, String customerName, String state, BigDecimal taxRate, String productType,
                 BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot, BigDecimal area, BigDecimal materialCost,
                 BigDecimal laborCost, BigDecimal tax, BigDecimal total) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
        this.materialCost = materialCost;
        this.laborCost = laborCost;
        this.tax = tax;
        this.total = total;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getState() {
        return state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public static Order createOrder(String customerName, TaxCode taxCode, Product product, BigDecimal area, ProductCosts costs) {
        int orderNumber = Order.nextOrderNumber;
        Order.nextOrderNumber++;
        // Not sure how to separate out most effectively. Do the below but ask Ronnie his thoughts
        // Currently, order method is coupled too many parameters. Ask about Pojo and Dto and mapper?

        return new Order(orderNumber, customerName,
                taxCode.getState(), taxCode.getTaxRate(),
                product.getProductType(), product.getCostPerSquareFoot(), product.getLaborCostPerSquareFoot(),
                area, costs.getMaterialCosts(), costs.getLaborCosts(), costs.getTaxCost(), costs.getTotalCost());
    }

    public static Order modifyOrder(int orderNumber, String customerName, TaxCode taxCode, Product product, BigDecimal area, ProductCosts costs) {
        // Not sure how to separate out most effectively. Do the below but ask Ronnie his thoughts
        // Currently, order method is coupled too many parameters. Ask about Pojo and Dto and mapper?

        return new Order(orderNumber, customerName,
                taxCode.getState(), taxCode.getTaxRate(),
                product.getProductType(), product.getCostPerSquareFoot(), product.getLaborCostPerSquareFoot(),
                area, costs.getMaterialCosts(), costs.getLaborCosts(), costs.getTaxCost(), costs.getTotalCost());
    }





}
