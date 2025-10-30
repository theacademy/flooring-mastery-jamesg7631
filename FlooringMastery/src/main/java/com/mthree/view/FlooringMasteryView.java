package com.mthree.view;

import com.mthree.model.Order;
import com.mthree.model.Product;
import com.mthree.model.TaxCode;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
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
        return userIO.readString("Enter an Order Date to display all order for that given date. (mm-dd-yyyy)");
    }

    public void displayOrderDateBanner(LocalDate date) {
        userIO.print("Displaying Orders for " + date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
    }
    public void displayOrders(List<Order> orders) {
        // Not much guidance on how order should be displayed to console. Order can have a toStringMethod so can use that?
        userIO.print("");
        for (Order order: orders) {
            this.displayOrder(order);
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
        return userIO.readString("Enter an Order Date it must be in the future in the format 'mm-dd-yyyy'");
    }

    // Badly named method. Try and rename
    public String displayCustomerNamePrompt() {
        return userIO.readString("Please enter your name.");
    }

    public void displayAllStatesBanner() {
        userIO.print(" --- Display all States ---");
    }

    public void displayAllStates(List<TaxCode> taxCodes) {
        for (TaxCode taxCode: taxCodes) {
            String stateInformation = String.format("State Abbreviation: %s, State: %s", taxCode.getStateAbbreviation(), taxCode.getState());
            userIO.print(stateInformation);
        }
    }

    public String displayStateNamePrompt() {
        return userIO.readString("Please enter the abbreviation for the state.");
    }

    public void displayProductBanner() {
        userIO.print("--- Display all Products ---");
    }

    public void displayAllProducts(List<Product> allProducts) {
        for (int i = 0; i < allProducts.size(); i++) {
            displayProduct(allProducts.get(i),i + 1);
        }
    }

    public void displayProduct(Product product, int productNumber) {
        String output = String.format("Product Number: %d, Product Type: %s, Cost per Sq Foot: %s, Labor Cost per Sq Foot: %s, ",
                productNumber, product.getProductType(), product.getCostPerSquareFoot(), product.getLaborCostPerSquareFoot());
        userIO.print(output);
    }

    public String displayProductPrompt() {
        return userIO.readString("Enter the product number.");
    }

    public String displayAreaPrompt() {
        return userIO.readString("Please enter the product area in square foot.");
    }

    public void displayOrder(Order order)  {
        // I don't see specific guidance on the output format of the order.
        // For now, I will keep as is!
        userIO.print(order.toString());
    }

    public String confirmOrderPrompt() {
        return userIO.readString("Would you like to place the order ? (Y/N)");
    }

    public void displayOrderAddedSuccessfully() {
        userIO.print("Order added successfully!");
    }

    // 3. Edit an Order
    public void displayEditOrderBanner() {
        userIO.print("--- Edit an Order ---");
    }

    public int getOrderNumber() {
        return userIO.readInt("Please provide an order number.", 0, Integer.MAX_VALUE);
    }

    public String getStateName(String currentStateName) {
        // I really need to take out so I can make use of previous validation logic
        String newStateName = userIO.readString("Please enter the abbreviation for the state name (" +
                currentStateName + ")");

        if (newStateName.equals("")) {
            return currentStateName;
        }
        return newStateName;
    }

    public String displayProductPrompt(String currentProductName) {
        // Broke my method naming convention deliberately
        // This is while writing the edit logic I have noticed similar functionality
        // I was thinking if I try and mirror the method names it will make future refactoring easier
        String newProductName = userIO.readString("Enter the product number. Current selected product is " + currentProductName);
        return newProductName.equals("") ? currentProductName : newProductName;
    }

    public String displayAreaPrompt(String currentArea) {
        String newArea = userIO.readString("Please enter the product area in square foot. + (" + currentArea);
        if (newArea.equals("")) {
            return currentArea;
        }
        return newArea;
    }

    public String getCustomerName(String currentName) {
        // Personally I would add to the prompt "or press enter to keep current name"
        // Probably in wrong place below. Will try and fix.
        // I imagine I should return this to controller and then the controller call some method in the service layer
        // which takes the new parameters in and updates the object
        // This approach is very time consuming and hard to do in the timeframe
        String returnedString = userIO.readString("Enter customer name (" + currentName +"):");
        if (returnedString.isEmpty()) {
            return currentName;
        }
        return returnedString;
    }

    // 4. Remove Order

    public void displayRemoveOrderBanner() {
        userIO.print("--- Display Remove Order Banner ---");
    }

    public boolean confirmOrderRemoval() {
        while (true) {
            String response = userIO.readString("Can you confirm you would like to remove the order (Y/N)").toLowerCase();
            if (response.equals("y")) {
                return true;
            } else if (response.equals("n")) {
               return false;
            }
        }
    }




    // 6. Quit
    public void displayExitBanner() {
        // Not sure what the requirement for this is. Please refer back to the specification
        userIO.print("Good bye !");
    }



}
