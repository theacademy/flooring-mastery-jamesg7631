package com.mthree.view;

import com.mthree.dao.FlooringMasteryPersistenceException;
import com.mthree.model.Order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlooringMasteryView {
    private UserIO userIO;

    public FlooringMasteryView(UserIO userIO) {
        this.userIO = userIO;
    }

    public void welcomeUser() {
       userIO.print("<<Flooring Program>>");
    }
    public int displayMainMenu() {
       userIO.print("1. Display Orders");
       userIO.print("2. Add an Order");
       userIO.print("3. Edit an Order");
       userIO.print("4. Remove an Order");
       userIO.print("5. Export all Data");
       userIO.print("6. Quit");
       // Add remaining print logic above

        // Also not thought about the best option to replace the min and max with the actual values.
        // Considering enums but not worked out how to use them effectively here
       return userIO.readInt("Please select from the above choices.",1, 6);
    }

    // 1. Display
    public void displayOrderBanner() {
        userIO.print("--- Display Orders ---");
    }

    public String getAllOrdersByDate() {
        return userIO.readString("Enter an Order Date to display all order for that given date.");
    }

    public void displayOrderDateBanner(LocalDate date) {
        userIO.print("Displaying Orders for " + date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
    }
    public void displayOrders(List<Order> orders) {
        // Not much guidance on how order should be displayed to console. Order can have a toStringMethod so can use that?
        for (Order order: orders) {
            String orderInfo = order.toString();
            userIO.print(orderInfo);
        }
        userIO.readString("Please hit enter to continue");
    }

    // Don't know if I should have more sophisticated error display
    public void displayErrorMessage(String message) {
        userIO.print(message);
    }

    // 2. Add an Order
    public void displayAddOrderBanner() {
        // Could make it "Enter Order Details" to match the flowchart exactly
        userIO.print(" --- Add an Order ---");
    }

    public String addOrderByDate() {
        return userIO.readString("Enter an Order Date it must be in the future in the format 'DD/MM/YYYY");
    }

    // Badly named method. Try and rename
    public String displayCustomerNamePrompt() {
        return userIO.readString("Please enter your name.");
    }

    public void displayAllStates() throws FlooringMasteryPersistenceException {
        // Enter the logic for displaying all states and their respective state code
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    public String displayStateNamePrompt() {
        return userIO.readString("Please enter the abbreviation for the state.");
    }

    // 3. Edit an Order

    // 6. Quit
    public void displayExitBanner() {
        // Not sure what the requirement for this is. Please refer back to the specification
        userIO.print("Good bye !");
    }
}
