package com.mthree.service;

import com.mthree.dao.OrderDateInvalidException;
import com.mthree.dao.FlooringMasteryPersistenceException;
import com.mthree.model.Order;
import com.mthree.model.Product;
import com.mthree.model.State;
import com.mthree.model.TaxCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FlooringMasterServiceLayer {
    // --- Order Section ---
    // Add Order Section
     void addOrder(Order order);
     LocalDate validateOrderDate(String orderDate) throws OrderDateInvalidException;
     void validateCustomerName(String name) throws FlooringMasteryCustomerInvalidNameException;
     List<State> getAllStates() throws FlooringMasteryPersistenceException;
     TaxCode getTaxCode(String stateAbbreviation) throws FlooringMasterInvalidStateAbbreviationException;
     List<Product> getAllProducts() throws FlooringMasteryPersistenceException;
     Product getProduct(String productNumber) throws FlooringMasteryPersistenceException;
     BigDecimal validateProductArea(String area) throws FlooringMasteryInvalidAreaException;
     BigDecimal calculateCosts(BigDecimal taxCode, BigDecimal area, Product product);
     boolean validateUserResponse(String userResponse);
     List<Order> getAllOrders();

     // Edit Order Section
     //void editOrder(); I wanted to have this but it did not work out

    void editOrder(Order originalOrder, Order replacementOrder);

    void removeOrder(String orderNumber);

     Order getOrder(LocalDate orderDate, int orderNumber);

    // --- Tax Code ---
     List<TaxCode> getAllTaxCodes();


}
