package com.mthree.model;

import java.math.BigDecimal;

public class Order {
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
}
