package com.mthree.service;

import com.mthree.dao.OrderDao;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class FlooringMasteryServiceLayerImpl implements FlooringMasterServiceLayer {
    private OrderDao orderDao;
    private TaxCode taxDao;
    private Product productDao;

    public FlooringMasteryServiceLayerImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    // Business Logic for adding an order here
    // Ask question why do we not do that in the object itself
    // Seems with Spring framework we make our models dumb

    // --- Order Section ---

    public LocalDate validateOrderDate(String date) throws OrderDateInvalidException {
        try {
            LocalDate orderDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
            LocalDate todaysDate = LocalDate.now();
            if (!todaysDate.isBefore(orderDate)) {
                // If this becomes common add to a utils folder and name static
                DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                String errorMessage = String.format("Order must be placed after: %s. You entered an order date of %s.",
                        todaysDate.format(dateTimeFormat),
                        orderDate.format(dateTimeFormat));
                throw new OrderDateInvalidException(errorMessage);
            }
            return orderDate;
        } catch (OrderDateInvalidException e) {
            throw new OrderDateInvalidException(e.getMessage());
        } catch (Exception e) {
            throw new OrderDateInvalidException("User input: " + date + " is invalid.", e);
        }
    }

    @Override
    public void validateCustomerName(String name) throws InvalidCustomerNameException{
        if(name.isEmpty()) {
            throw new InvalidCustomerNameException("Customer name must not be blank.");
        }
        for (char c: name.toLowerCase().toCharArray()) {
            // If I get time make this into separate method. Got more important things write now
            if (!((c >= 'a' && c <= 'z') || ('0' <= c && c <= '9') || c == ' ' || c == '.' || c == ',')) {
                String errorMessage = String.format("You entered: %s. All characters must be between [a-z] or [0-9] or be either in ['.', ',', ' ']" , name);
                throw new InvalidCustomerNameException(errorMessage);
            }
        }
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
    public void editOrder(LocalDate orderDate, Order originalOrder, Order replacementOrder) {
        // If I get time add logic to handle the case where for whatever reason removal fails.
        // If removal fails we probably don't want to handle the other order
        orderDao.removeOrder(orderDate, originalOrder);
        orderDao.addOrder(orderDate, replacementOrder);
    }

    @Override
    public void removeOrder(String orderNumber) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // Probably delete the below

    @Override
    public List<Order> getAllOrdersByDate(LocalDate orderDate) {
        List<Order> result = new ArrayList<>();
        Map<Integer, Order> orders = orderDao.getOrderByDate(orderDate);
        if (orders == null) {
            // To ensure with date format. Might be useful to have a utils package and make use of an enum?
            throw new OrderDoesNotExistException("No orders exist on " + orderDate.format(DateTimeFormatter.ofPattern("MM-dd-YYYY")));
        }
        for (Order order: orders.values()) {
            result.add(order);
        }
        return result;
    }



    @Override
    public boolean validateUserResponse(String userResponse) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber) {
        Map<Integer, Order> orderByDate = orderDao.getOrderByDate(orderDate);
        Order order = orderByDate.get(orderNumber);
        if (order == null) {
            throw new OrderDoesNotExistException( "Order: " + orderNumber + " cannot be found");
        }
        return order;
    }

    // --- Tax Code Section ---
    @Override
    public List<TaxCode> getAllTaxCodes() {

    }
}
