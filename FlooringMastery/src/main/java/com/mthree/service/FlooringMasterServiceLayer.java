package com.mthree.service;

import com.mthree.dao.FlooringMasteryOrderDateInvalidException;
import com.mthree.dao.FlooringMasteryPersistenceException;
import com.mthree.model.Order;
import com.mthree.model.State;
import com.mthree.model.TaxCode;

import java.time.LocalDate;
import java.util.List;

public interface FlooringMasterServiceLayer {
    // --- Order Section ---
     void addOrder(Order order);
     LocalDate validateOrderDate(String orderDate) throws FlooringMasteryOrderDateInvalidException;
     void validateCustomerName(String name) throws FlooringMasteryCustomerInvalidNameException;
     List<State> getAllStates() throws FlooringMasteryPersistenceException;
     void validateStateAbbreviation() throws FlooringMasterInvalidStateAbbreviationException;

     void editOrder();

     void removeOrder(String orderNumber);

     Order getOrder(String orderNumber);
     List<Order> getAllOrders();

    // --- Tax Code ---
     List<TaxCode> getAllTaxCodes();



}
