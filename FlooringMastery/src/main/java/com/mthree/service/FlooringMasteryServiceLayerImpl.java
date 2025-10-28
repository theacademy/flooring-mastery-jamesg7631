package com.mthree.service;

import com.mthree.dao.FlooringMasteryDao;
import com.mthree.dao.FlooringMasteryOrderDateInvalidException;
import com.mthree.dao.FlooringMasteryPersistenceException;
import com.mthree.model.Order;
import com.mthree.model.State;
import com.mthree.model.TaxCode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlooringMasteryServiceLayerImpl implements FlooringMasterServiceLayer {
    private FlooringMasteryDao dao;

    public FlooringMasteryServiceLayerImpl(FlooringMasteryDao dao) {
        this.dao = dao;
    }

    // Business Logic for adding an order here
    // Ask question why do we not do that in the object itself
    // Seems with Spring framework we make our models dumb
    // Order date must be in the future
    // Customer name not blank
    // State must be on tax file. So must read in tax file

    // --- Order Section ---

    public LocalDate validateOrderDate(String date) throws FlooringMasteryOrderDateInvalidException {
        try {
            LocalDate orderDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            return orderDate;
        } catch (Exception e) {
            throw new FlooringMasteryOrderDateInvalidException(e.getMessage());
        }
    }

    @Override
    public void validateCustomerName(String name) throws FlooringMasteryCustomerInvalidNameException {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    // Add Order

    @Override
    public void addOrder(Order order) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<State> getAllStates() throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void validateStateAbbreviation() throws FlooringMasterInvalidStateAbbreviationException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // Edit Order

    @Override
    public void editOrder() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void removeOrder(String orderNumber) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<Order> getAllOrders() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Order getOrder(String orderNumber) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // --- Tax Code Section ---
    @Override
    public List<TaxCode> getAllTaxCodes() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
