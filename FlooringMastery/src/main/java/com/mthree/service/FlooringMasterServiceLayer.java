package com.mthree.service;

import com.mthree.dao.OrderDateInvalidException;
import com.mthree.dao.FlooringMasteryPersistenceException;
import com.mthree.model.Order;
import com.mthree.model.Product;
import com.mthree.model.TaxCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FlooringMasterServiceLayer {
    // --- Order Section ---
    // Add Order Section
     LocalDate validateOrderDate(String orderDate) throws OrderDateInvalidException;
     void validateCustomerName(String name) throws FlooringMasteryCustomerInvalidNameException;
     TaxCode getTaxCode(String stateAbbreviation) throws FlooringMasterInvalidStateAbbreviationException;

    void addOrder(LocalDate orderDate, Order order);

    List<Product> getAllProducts() throws FlooringMasteryPersistenceException;
     Product getProduct(String productNumber) throws FlooringMasteryPersistenceException;
     int getProductNumber(String productNumber);
     BigDecimal validateProductArea(String area) throws FlooringMasteryInvalidAreaException;


    boolean validateUserResponse(String userResponse);
    List<Order> getAllOrdersByDate(LocalDate orderDate);
     // Edit Order Section
    void editOrder(LocalDate orderDate, Order originalOrder, Order replacementOrder);

    void removeOrder(LocalDate orderDate, Order order);

     Order getOrder(LocalDate orderDate, int orderNumber);

    // --- Tax Code ---
     List<TaxCode> getAllTaxCodes();


}
