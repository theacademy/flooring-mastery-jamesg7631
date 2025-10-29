package com.mthree.dao;

import com.mthree.model.Order;
import com.mthree.model.TaxCode;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class FlooringMasteryDaoImpl implements FlooringMasteryDao{
    private Map<LocalDate, Map<Integer,Order>> orders;

    private final String ORDER_FILE;
    private static final String DELIMETER = ",";
    // Order Section

    public FlooringMasteryDaoImpl(String orderTextFile) {
        this.ORDER_FILE = orderTextFile;
    }

    @Override
    public void addOrder(Order order) throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void editOrder(Order order) throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void removeOrder(Order order) throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Order getOrder(String orderNumber) throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Map<Integer, Order> getOrderByDate(LocalDate orderDate) {
        loadOrders();
        // Took inspiration from the UML here. Although now I have I can't see a
        // way to handle things nicely.
        return orders.get(orderDate);
    }

    // --- Tax Code ---
    @Override
    public List<TaxCode> getAllTaxCodes() throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void loadOrders() throws FlooringMasteryPersistenceException {
        // Read in orders
    }

}
