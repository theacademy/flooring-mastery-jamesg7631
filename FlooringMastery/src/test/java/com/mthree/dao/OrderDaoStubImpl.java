package com.mthree.dao;

import com.mthree.model.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoStubImpl implements OrderDao {

    // In-memory database: Map<Date, Map<OrderNumber, Order>>
    private Map<LocalDate, Map<Integer, Order>> orders;
    private int testOrderNumberCounter = 1;

    public OrderDaoStubImpl() {
        this.orders = new HashMap<>();
        // Simulate loading and setting the next order number
        Order.nextOrderNumber = testOrderNumberCounter;
    }

    @Override
    public void removeOrder(LocalDate orderDate, Order order) throws FlooringMasteryPersistenceException {
        Map<Integer, Order> ordersForDate = orders.get(orderDate);
        if (ordersForDate != null) {
            ordersForDate.remove(order.getOrderNumber());
            if (ordersForDate.isEmpty()) {
                orders.remove(orderDate);
            }
        }
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber) throws FlooringMasteryPersistenceException {
        Map<Integer, Order> ordersForDate = orders.get(orderDate);
        if (ordersForDate == null) {
            throw new OrderDoesNotExistException("Stub: No orders found for date: " + orderDate);
        }
        Order order = ordersForDate.get(orderNumber);
        if (order == null) {
            throw new OrderDoesNotExistException("Stub: No order with number " + orderNumber);
        }
        return order;
    }

    @Override
    public Map<Integer, Order> getOrderByDate(LocalDate orderDate) {
        // This simulates the real DAO's behavior of returning null if no file exists
        return orders.get(orderDate);
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> allOrders = new ArrayList<>();
        for (Map<Integer, Order> dailyOrders : orders.values()) {
            allOrders.addAll(dailyOrders.values());
        }
        return allOrders;
    }

    @Override
    public void addOrder(LocalDate orderDate, Order order) throws FlooringMasteryPersistenceException {
        // Simulate setting the next order number if it's a new order
        if (order.getOrderNumber() >= Order.nextOrderNumber) {
            Order.nextOrderNumber = order.getOrderNumber() + 1;
        }

        Map<Integer, Order> ordersForDate = orders.get(orderDate);
        if (ordersForDate == null) {
            ordersForDate = new HashMap<>();
            orders.put(orderDate, ordersForDate);
        }
        ordersForDate.put(order.getOrderNumber(), order);
    }

    /**
     * Helper method for tests to clear the stub's memory.
     */
    public void clearAllOrders() {
        orders.clear();
        testOrderNumberCounter = 1;
        Order.nextOrderNumber = testOrderNumberCounter;
    }
}