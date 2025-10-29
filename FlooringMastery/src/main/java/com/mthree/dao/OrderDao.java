package com.mthree.dao;

import com.mthree.model.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderDao {

    // --- Orders ---
    public void addOrder(Order order) throws FlooringMasteryPersistenceException;
    public void editOrder(Order order) throws FlooringMasteryPersistenceException;
    public void removeOrder(Order order) throws FlooringMasteryPersistenceException;

    void removeOrder(LocalDate orderDate, Order order) throws FlooringMasteryPersistenceException;

    public Order getOrder(String orderNumber) throws FlooringMasteryPersistenceException;

    Order getOrder(LocalDate orderDate, int orderNumber) throws FlooringMasteryPersistenceException;

    Map<Integer, Order> getOrderByDate(LocalDate orderDate);
    List<Order> getAllOrders();
    void addOrder(LocalDate orderDate, Order order) throws FlooringMasteryPersistenceException;
}
