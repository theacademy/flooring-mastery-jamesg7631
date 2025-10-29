package com.mthree.service;

import com.mthree.dao.ProductDao;
import com.mthree.dao.OrderDateInvalidException;
import com.mthree.dao.OrderDoesNotExistException;
import com.mthree.dao.FlooringMasteryPersistenceException;
import com.mthree.model.Order;
import com.mthree.model.Product;
import com.mthree.model.State;
import com.mthree.model.TaxCode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
public class FlooringMasteryServiceLayerImpl implements FlooringMasterServiceLayer {
    private ProductDao dao;

    public FlooringMasteryServiceLayerImpl(ProductDao dao) {
        this.dao = dao;
    }

    // Business Logic for adding an order here
    // Ask question why do we not do that in the object itself
    // Seems with Spring framework we make our models dumb
    // Order date must be in the future
    // Customer name not blank
    // State must be on tax file. So must read in tax file

    // --- Order Section ---

    public LocalDate validateOrderDate(String date) throws OrderDateInvalidException {
        try {
            LocalDate orderDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            return orderDate;
        } catch (Exception e) {
            throw new OrderDateInvalidException(e.getMessage());
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
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public TaxCode getTaxCode(String stateAbbreviation) throws FlooringMasterInvalidStateAbbreviationException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Product getProduct(String productNumber) throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public BigDecimal calculateCosts(BigDecimal taxCode, BigDecimal area, Product product) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public BigDecimal validateProductArea(String area) throws FlooringMasteryInvalidAreaException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // Edit Order

    @Override
    public void editOrder(Order originalOrder, Order replacementOrder) {
        // If I get time add logic to handle the case where for whatever reason removal fails.
        // If removal fails we probably don't want to handle the other order
        dao.removeOrder(originalOrder);
        dao.addOrder(replacementOrder);
    }

    @Override
    public void removeOrder(String orderNumber) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<Order> getAllOrders() {
        // Probably bad practice but short on time
        return (List<Order>) dao.getAllOrders().values();
    }


    @Override
    public boolean validateUserResponse(String userResponse) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber) {
        Map<Integer, Order> orderByDate = dao.getOrderByDate(orderDate);
        Order order = orderByDate.get(orderNumber);
        if (order == null) {
            throw new OrderDoesNotExistException( "Order: " + orderNumber + " cannot be found");
        }
        return order;
    }

    // --- Tax Code Section ---
    @Override
    public List<TaxCode> getAllTaxCodes() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
