package com.mthree.controller;

import com.mthree.dao.FlooringMasteryOrderDateInvalidException;
import com.mthree.dao.FlooringMasteryOrderDoesNotExistException;
import com.mthree.dao.FlooringMasteryPersistenceException;
import com.mthree.model.Order;
import com.mthree.model.Product;
import com.mthree.model.TaxCode;
import com.mthree.pojo.ProductCosts;
import com.mthree.service.*;
import com.mthree.view.FlooringMasteryView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;

public class FlooringMasteryController {
    private FlooringMasteryView view;
    private FlooringMasterServiceLayer service;

    public FlooringMasteryController(FlooringMasteryView view, FlooringMasterServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        int menuSelection = 0; // Why not just ?

        try {
            while (true) {
                menuSelection = getMenuSelection();
                switch (menuSelection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addAnOrder();
                        break;
                    case 3:
                        editAnOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        exportAllData();
                        break;
                    case 6:
                        break;
                    default:
                        unknownCommand();
                }
                exitMessage();
            }
            } catch (FlooringMasteryOrderDoesNotExistException e) {
                // Seems to be we should separate exceptions into specific types
                // I thought we should only use exceptions for exceptional circumstances
                // I know its in the requirements, but throwing an exception seems extreme for retrieving no orders
                // Especially when it seems perfectly plausible orders were not placed for a future date
                view.displayErrorMessage(e.getMessage());
            } catch (FlooringMasteryPersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            }
    }

    private int getMenuSelection() {
        return this.view.displayMainMenu();
    }

    private void displayOrders() throws FlooringMasteryPersistenceException, FlooringMasteryOrderDoesNotExistException{
        view.displayOrderBanner();
        List<Order> orderList = service.getAllOrders();
        view.displayOrders(orderList);
    }



    private void confirmOrder(Order order) {
        while (true) {
            try {
                view.displayOrder(order);
                String userResponse = view.confirmOrderPrompt();
                boolean success = service.validateUserResponse(userResponse);
                break;
            } catch (FlooringMasteryInvalidConfirmationResponseException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
    }

    private BigDecimal getArea() {
        BigDecimal area;
        while (true) {
            try {
               String areaString = view.displayAreaPrompt();
               area = service.validateProductArea(areaString);
               break;
            } catch (FlooringMasteryInvalidAreaException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
        return area;
    }

    public BigDecimal getArea(BigDecimal currentArea) {
        BigDecimal area;
        while (true) {
            try {
                String areaString = view.displayProductPrompt(currentArea.toPlainString());
                area = service.validateProductArea(areaString);
                break;
            } catch (FlooringMasteryInvalidAreaException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
        return area;
    }

    private Product getProduct() {
        Product product;
        while (true) {
            try {
                List<Product> allProducts = service.getAllProducts();
                view.displayAllProducts(allProducts);
                String productNumber = view.displayProductPrompt();
                product = service.getProduct(productNumber);
                break;
            } catch (FlooringMasteryPersistenceException | FlooringMasteryInvalidProductNameException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
        return product;
    }

    private Product getProduct(String currentProductName) {
        Product product;
        while (true) {
            try {
                List<Product> allProducts = service.getAllProducts();
                view.displayAllProducts(allProducts);
                String productNumber = view.displayProductPrompt(currentProductName);
                product = service.getProduct(productNumber);
                break;
            } catch (FlooringMasteryPersistenceException | FlooringMasteryInvalidProductNameException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
        return product;
    }


    private TaxCode getTaxCode() {
        TaxCode stateTaxCode;
        while (true) {
            try {
                view.displayAllStates();
                String stateCode = view.displayStateNamePrompt();
                stateTaxCode = service.getTaxCode(stateCode);// Add line below for getting taxCode or rename this method to get it
                break;
            } catch (FlooringMasteryPersistenceException | FlooringMasterInvalidStateAbbreviationException e) {
                // Would this approach cause an issue where you get stuck and can't return to main menu
                view.displayErrorMessage(e.getMessage());
            }
        }
        return stateTaxCode;
    }

    private TaxCode getTaxCode(String currentState) {
        TaxCode stateTaxCode;
        while (true) {
            try {
                view.displayAllStates();
                String stateCode = view.getStateName(currentState);
                stateTaxCode = service.getTaxCode(stateCode);// Add line below for getting taxCode or rename this method to get it
                break;
            } catch (FlooringMasteryPersistenceException | FlooringMasterInvalidStateAbbreviationException e) {
                // Would this approach cause an issue where you get stuck and can't return to main menu
                view.displayErrorMessage(e.getMessage());
            }
        }
        return stateTaxCode;
    }

    private String getCustomerName() {
        String customerName;
        while (true) {
            try{
                customerName = view.displayCustomerNamePrompt();
                // The spec doesn't say how we should handle the case where name is invalid. Just tells us what an invalid name is
                service.validateCustomerName(customerName);
                break;
            } catch (FlooringMasteryCustomerInvalidNameException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
        return customerName;
    }

    private String getCustomerName(String currentName) {
        String customerName;
        while (true) {
            try{
                customerName = view.getCustomerName(currentName);
                // The spec doesn't say how we should handle the case where name is invalid. Just tells us what an invalid name is
                service.validateCustomerName(customerName);
                break;
            } catch (FlooringMasteryCustomerInvalidNameException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
        return customerName;
    }

    private LocalDate getOrderDate () {
        LocalDate orderDateObj;
        String orderDate;
        while (true) {
           try {
               orderDate = view.addOrderByDate();
               orderDateObj = service.validateOrderDate(orderDate);
               return orderDateObj;
               // Bad variable name. I didn't put much thought into it as I was unsure if I should even be returning a localdate in the first place
               // Was unsure whether the best practice is to use the localdate or use the string.
               // Getting dangerously close to doing business logic in the controller
           } catch (FlooringMasteryOrderDateInvalidException e) {
               view.displayErrorMessage(e.getMessage());
           }
        }
    }


    public void addAnOrder() {
        boolean returnToMainMenu = false;
        view.displayAddOrderBanner();
        // get date
        LocalDate orderDateObj = getOrderDate();
        // Get Customer Name
        String customerName;
        customerName = getCustomerName();
        // Get state information
        TaxCode stateTaxCode = getTaxCode();
        // Get Product Information
        Product product = getProduct();
        // Record the Area of the order
        BigDecimal area = getArea();
        // Calculate Order Costs
        ProductCosts costs = new ProductCosts(area, stateTaxCode, product);
        Order order = Order.createOrder(customerName, stateTaxCode, product, area, costs);
        // Display the Order Details
        confirmOrder(order);


        // Save Order to file
        service.addOrder(order);
    }

    public void editAnOrder() {
        // throws  FlooringMasteryOrderDoesNotExistException
        view.displayEditOrderBanner();
        LocalDate orderDate = getOrderDate();
        int orderNumber = view.getOrderNumber();
        Order order = service.getOrder(orderDate, orderNumber);
        String customerName = getCustomerName(order.getCustomerName());
        // State
        TaxCode stateTaxCode = getTaxCode(order.getState());
        // Product Type
        Product product = getProduct(order.getProductType());
        // Area
        BigDecimal area = getArea(order.getArea());
        ProductCosts costs = new ProductCosts(area, stateTaxCode, product);
        Order modifiedOrder = Order.modifyOrder(order.getOrderNumber(), customerName, stateTaxCode,
                product, area, costs);
        confirmOrder(order);
        // Just a thought. My logic relies on the order attributes not being changed by mistake
        // Might be worth making the order attributes final
        service.editOrder(order, modifiedOrder);
        }

    public void removeOrder() {
        view.displayExitBanner();
        LocalDate orderDate = getOrderDate();
        int orderNumber = view.getOrderNumber();
        Order order = service.getOrder(orderDate, orderNumber);
    }

    public void exportAllData() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void unknownCommand() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
}
