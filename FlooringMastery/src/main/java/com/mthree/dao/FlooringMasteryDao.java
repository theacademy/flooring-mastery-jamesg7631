package com.mthree.dao;

import com.mthree.model.Order;
import com.mthree.model.TaxCode;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface FlooringMasteryDao {

    // --- Orders ---
    public void addOrder(Order order) throws FlooringMasteryPersistenceException;
    public void editOrder(Order order) throws FlooringMasteryPersistenceException;
    public void removeOrder(Order order) throws FlooringMasteryPersistenceException;
    public Order getOrder(String orderNumber) throws FlooringMasteryPersistenceException;
    Map<Integer, Order> getOrderByDate(LocalDate orderDate);
    Map<Integer, Order> getAllOrders();

    // --- Tax Code ---
    List<TaxCode> getAllTaxCodes() throws FlooringMasteryPersistenceException;

}
