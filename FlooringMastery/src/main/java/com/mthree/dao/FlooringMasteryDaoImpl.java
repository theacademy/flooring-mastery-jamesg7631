package com.mthree.dao;

import com.mthree.model.Order;
import com.mthree.model.TaxCode;

import java.util.List;

public class FlooringMasteryDaoImpl implements FlooringMasteryDao{

    // Order Section

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
