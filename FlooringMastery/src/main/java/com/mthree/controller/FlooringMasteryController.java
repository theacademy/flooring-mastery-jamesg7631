package com.mthree.controller;

import com.mthree.dao.FlooringMasteryOrderDoesNotExistException;
import com.mthree.dao.FlooringMasteryPersistenceException;
import com.mthree.model.Order;
import com.mthree.service.FlooringMasterServiceLayer;
import com.mthree.view.FlooringMasteryView;

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

            }
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

        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        } catch (FlooringMasteryOrderDoesNotExistException e) {
            // Seems to be we should separate exceptions into specific types
            // I thought we should only use exceptions for exceptional circumstances
            // I know its in the requirements, but throwing an exception seems extreme for retrieving no orders
            // Especially when it seems perfectly plausible orders were not placed for a future date
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


}
