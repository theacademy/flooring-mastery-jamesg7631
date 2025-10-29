package com.mthree.dao;

import com.mthree.model.Order;

import java.time.LocalDate;
import java.util.Map;

public interface ProductDao {

    // --- Orders ---
    public void addOrder(Order order) throws FlooringMasteryPersistenceException;
    public void editOrder(Order order) throws FlooringMasteryPersistenceException;
    public void removeOrder(Order order) throws FlooringMasteryPersistenceException;
    public Order getOrder(String orderNumber) throws FlooringMasteryPersistenceException;
    Map<Integer, Order> getOrderByDate(LocalDate orderDate);
    Map<Integer, Order> getAllOrders();


}
