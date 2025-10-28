package com.mthree.dao;

import com.mthree.model.Order;
import com.mthree.model.TaxCode;

import java.util.List;
import java.util.Map;

public class FlooringMasteryDaoImpl implements FlooringMasteryDao{
    private Map<String, Order> orders;
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

    // --- Tax Code ---
    @Override
    public List<TaxCode> getAllTaxCodes() throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
