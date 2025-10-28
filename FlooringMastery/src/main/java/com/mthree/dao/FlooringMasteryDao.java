package com.mthree.dao;

import com.mthree.model.Order;
import com.mthree.model.TaxCode;

import java.util.List;

public interface FlooringMasteryDao {

    // --- Orders ---
    public void addOrder(Order order) throws FlooringMasteryPersistenceException;
    public void editOrder(Order order) throws FlooringMasteryPersistenceException;
    public void removeOrder(Order order) throws FlooringMasteryPersistenceException;
    public Order getOrder(String orderNumber) throws FlooringMasteryPersistenceException;

    // --- Tax Code ---
    List<TaxCode> getAllTaxCodes() throws FlooringMasteryPersistenceException;

}
