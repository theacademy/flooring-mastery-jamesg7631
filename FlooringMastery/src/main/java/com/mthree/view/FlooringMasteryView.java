package com.mthree.view;

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

    public String getOrderDate() {
        return userIO.readString("Enter an Order Date to display all order for that given date.");
    }

    public void displayOrderDateBanner(LocalDate date) {
        userIO.print("Displaying Orders for " + date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
    }
    public void displayOrders(List<Order> orders) {
        StringBuilder output = new StringBuilder();
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

}
