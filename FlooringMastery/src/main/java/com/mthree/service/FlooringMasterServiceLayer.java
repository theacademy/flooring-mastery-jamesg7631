package com.mthree.service;

import com.mthree.model.Order;
import com.mthree.model.TaxCode;

import java.util.List;

public interface FlooringMasterServiceLayer {
    // --- Order Section ---
     void addOrder(Order order);

     void editOrder();

     void removeOrder(String orderNumber);

     Order getOrder(String orderNumber);
     List<Order> getAllOrders();

    // --- Tax Code ---
     List<TaxCode> getAllTaxCodes();


}
