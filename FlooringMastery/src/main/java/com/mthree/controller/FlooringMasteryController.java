package com.mthree.controller;

import com.mthree.dao.OrderDateInvalidException;
import com.mthree.dao.OrderDoesNotExistException;
import com.mthree.dao.FlooringMasteryPersistenceException;
import com.mthree.model.Order;
import com.mthree.model.Product;
import com.mthree.model.TaxCode;
import com.mthree.pojo.ProductCosts;
import com.mthree.service.*;
import com.mthree.view.FlooringMasteryView;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class FlooringMasteryController {
    private FlooringMasteryView view;
    private FlooringMasterServiceLayer service;

    public FlooringMasteryController(FlooringMasteryView view, FlooringMasterServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        int menuSelection = 0; // Why not just ?
        boolean keepGoing = true;

            while (keepGoing) {
                try {
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
                            keepGoing = false;
                            break;
                        default:
                            unknownCommand();
                    }
                } catch (OrderDoesNotExistException e) {
                    view.displayErrorMessage(e.getMessage());
                } catch (Exception e) {
                    e.getMessage();
                }
            }
            exitMessage();
    }

    private int getMenuSelection() {
        return this.view.displayMainMenu();
    }

    private void displayOrders() throws FlooringMasteryPersistenceException, OrderDoesNotExistException {
        while (true) {
            try {
                view.displayOrderBanner();
                String date = view.getAllOrdersByDate();
                LocalDate validatedDate = service.validatePlacedOrderDate(date);
                List<Order> orderList = service.getAllOrdersByDate(validatedDate);
                view.displayOrders(orderList);
                return;
            } catch (OrderDoesNotExistException e) {
                throw new OrderDoesNotExistException(e.getMessage());
            } catch (Exception e) {
                view.displayErrorMessage(e.getMessage());
            }
        }

    }



    private boolean confirmOrder(Order order) {
        while (true) {
            try {
                view.displayOrder(order);
                String userResponse = view.confirmOrderPrompt();
                boolean success = service.validateUserResponse(userResponse);
                return success;
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
                String areaString = view.displayAreaPrompt(currentArea.toPlainString());
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
                view.displayProductBanner();
                view.displayAllProducts(allProducts);
                String productNumber = view.displayProductPrompt();
                product = service.getProduct(productNumber);
                break;
            } catch (FlooringMasteryPersistenceException | FlooringMasteryInvalidProductNumberException e) {
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
                int currentProductNumber = service.getProductNumber(currentProductName);
                String productNumber = view.displayProductPrompt(String.valueOf(currentProductNumber));
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
                List<TaxCode> allStates = service.getAllTaxCodes();
                view.displayAllStatesBanner();
                view.displayAllStates(allStates);
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
                List<TaxCode> allStates = service.getAllTaxCodes();
                view.displayAllStatesBanner();
                view.displayAllStates(allStates);
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
            } catch (InvalidCustomerNameException e) {
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
               orderDateObj = service.validateNewOrderDate(orderDate);
               return orderDateObj;
               // Bad variable name. I didn't put much thought into it as I was unsure if I should even be returning a localdate in the first place
               // Was unsure whether the best practice is to use the localdate or use the string.
               // Getting dangerously close to doing business logic in the controller
           } catch (OrderDateInvalidException e) {
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
        String customerName = getCustomerName();
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
        boolean confirmed = confirmOrder(order);

        // I have probably thrown exceptions in the past where I shouldn't have. I did it to avoid having business logic in the controller like below.
        // Which is the lesser of the two evils?
        if (!confirmed) {
            return;
        }
        // Save Order to file
        service.addOrder(orderDateObj, order);
        view.displayOrderAddedSuccessfully();

    }

    public void editAnOrder() {
        // throws  FlooringMasteryOrderDoesNotExistException
        // Seems like business logic. Hard to move as I do occasionally use both view and service layer
        view.displayEditOrderBanner();
        LocalDate orderDate = getOrderDate();
        int orderNumber = view.getOrderNumber();
        Order order = service.getOrder(orderDate, orderNumber);
        String customerName = getCustomerName(order.getCustomerName());
        TaxCode stateTaxCode = getTaxCode(order.getState());
        Product product = getProduct(order.getProductType());
        BigDecimal area = getArea(order.getArea());
        ProductCosts costs = new ProductCosts(area, stateTaxCode, product);
        Order modifiedOrder = Order.modifyOrder(order.getOrderNumber(), customerName, stateTaxCode,
                product, area, costs);
        confirmOrder(order);
        // Just a thought. My logic relies on the order attributes not being changed by mistake
        // Might be worth making the order attributes final
        service.editOrder(orderDate, order, modifiedOrder);
        }

    public void removeOrder() {
        view.displayRemoveOrderBanner();
        LocalDate orderDate = getOrderDate();
        int orderNumber = view.getOrderNumber();
        Order order = service.getOrder(orderDate, orderNumber);
        view.displayOrder(order);
        boolean orderRemoval = view.confirmOrderRemoval();
        if (!orderRemoval) {
            view.displayOrderCancelled();
            return;
        }

        service.removeOrder(orderDate, order);
        view.displayOrderHasBeenRemoved();
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
