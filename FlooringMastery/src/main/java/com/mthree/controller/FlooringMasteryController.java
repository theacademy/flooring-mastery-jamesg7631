package com.mthree.controller;

import com.mthree.dao.FlooringMasteryOrderDateInvalidException;
import com.mthree.dao.FlooringMasteryOrderDoesNotExistException;
import com.mthree.dao.FlooringMasteryPersistenceException;
import com.mthree.model.Order;
import com.mthree.service.FlooringMasteryCustomerInvalidNameException;
import com.mthree.service.FlooringMasterInvalidStateAbbreviationException;
import com.mthree.service.FlooringMasterServiceLayer;
import com.mthree.view.FlooringMasteryView;

import java.time.LocalDate;
import java.util.List;

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

    public void addAnOrder() {
        view.displayAddOrderBanner();
        String orderDate; // Will remove this line or the line below. Not decided yet!
        LocalDate orderDateObj;
        // get date
        while (true) {
           try {
               orderDate = view.addOrderByDate();
               orderDateObj = service.validateOrderDate(orderDate);
               break;
               // Bad variable name. I didn't put much thought into it as I was unsure if I should even be returning a localdate in the first place
               // Was unsure whether the best practice is to use the localdate or use the string.
               // Getting dangerously close to doing business logic in the controller
           } catch (FlooringMasteryOrderDateInvalidException e) {
               view.displayErrorMessage(e.getMessage());
           }
        }
        // Get Customer Name
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
        // Get state information
        String stateCode;
        while (true) {
            try {
                view.displayAllStates();
                stateCode = view.displayStateNamePrompt();
                service.validateStateAbbreviation();
                service.validateStateAbbreviation();
            } catch (FlooringMasteryPersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            } catch (FlooringMasterInvalidStateAbbreviationException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
    }

    public void editAnOrder() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void removeOrder() {
        throw new UnsupportedOperationException("Not yet implemented");
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
