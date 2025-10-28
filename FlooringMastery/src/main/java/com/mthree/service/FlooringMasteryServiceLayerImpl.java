package com.mthree.service;

import com.mthree.dao.FlooringMasteryDao;
import com.mthree.model.Order;
import com.mthree.model.TaxCode;

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

    @Override
    public void addOrder(Order order) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

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
