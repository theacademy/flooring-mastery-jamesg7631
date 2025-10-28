package com.mthree.service;

import com.mthree.dao.FlooringMasteryOrderDateInvalidException;
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
     LocalDate validateOrderDate(String orderDate) throws FlooringMasteryOrderDateInvalidException;
     void validateCustomerName(String name) throws FlooringMasteryCustomerInvalidNameException;
     List<State> getAllStates() throws FlooringMasteryPersistenceException;
     TaxCode getTaxCode(String stateAbbreviation) throws FlooringMasterInvalidStateAbbreviationException;
     List<Product> getAllProducts() throws FlooringMasteryPersistenceException;
     Product getProduct(String productNumber) throws FlooringMasteryPersistenceException;
     BigDecimal validateProductArea(String area) throws FlooringMasteryInvalidAreaException;
     BigDecimal calculateCosts(BigDecimal taxCode, BigDecimal area, Product product);
     boolean validateUserResponse(String userResponse);

     // Edit Order Section
     void editOrder();

     void removeOrder(String orderNumber);

     Order getOrder(String orderNumber);
     List<Order> getAllOrders();

    // --- Tax Code ---
     List<TaxCode> getAllTaxCodes();


}
