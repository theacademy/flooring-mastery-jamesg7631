package com.mthree.dao;

import com.mthree.model.Order;
import com.mthree.model.TaxCode;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@Component
public class FlooringMasteryDaoImpl implements FlooringMasteryDao{
    private Map<LocalDate, Map<Integer,Order>> orders;

    private final String ORDER_FILE;
    private static final String DELIMETER = ",";
    // Order Section

    public FlooringMasteryDaoImpl(String orderTextFile) {
        this.ORDER_FILE = orderTextFile;
    }
    public FlooringMasteryDaoImpl() {
        this.ORDER_FILE = "SampleFileData_floor/SampleFileData/Orders/";
    }

    @Override
    public void addOrder(Order order) throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void editOrder(Order order) throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void removeOrder(Order order) throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Order getOrder(String orderNumber) throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Map<Integer, Order> getOrderByDate(LocalDate orderDate) {
        loadOrders();
        // Took inspiration from the UML here. Although now I have I can't see a
        // way to handle things nicely.
        return orders.get(orderDate);
    }

    private Order unmarshallOrder(String orderAsText) {
        try {
            String[] orderToken = orderAsText.split(DELIMETER);
            int orderNumber = Integer.parseInt(orderToken[0]);
            String customerName = orderToken[1];
            String state = orderToken[2];
            BigDecimal taxRate = new BigDecimal(orderToken[3]);
            String productType = orderToken[4];
            BigDecimal area = new BigDecimal(orderToken[5]);
            BigDecimal costPerSquareFoot = new BigDecimal(orderToken[6]);
            BigDecimal laborCostPerSquareFoot = new BigDecimal(orderToken[7]);
            BigDecimal materialCost = new BigDecimal(orderToken[8]);
            BigDecimal laborCost = new BigDecimal(orderToken[9]);
            BigDecimal tax = new BigDecimal(orderToken[10]);
            BigDecimal total = new BigDecimal(orderToken[11]);
            Order order = new Order(orderNumber,
                    customerName,
                    state,
                    taxRate,
                    productType,
                    area,
                    costPerSquareFoot,
                    laborCostPerSquareFoot,
                    materialCost,
                    laborCost,
                    tax,
                    total);
            return order;
        } catch (Exception e) {
            throw new FlooringMasteryPersistenceException("Failed to unmarshall order data", e);
        }
    }

    @Override
    public Map<Integer, Order> getAllOrders() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private List<String> getOrderFileNames() {
        Path path = Paths.get(ORDER_FILE);
        List<String> fileNames = new ArrayList<>();
        try (Stream<Path> stream = Files.list(path)) {
            stream.forEach(p -> {
                fileNames.add(p.getFileName().toString());
            });
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Failed to retrieve the file names for the orders");
        }
        return fileNames;
    }

    private LocalDate getDateFromOrderName(String orderFileName) {
        try{
            int dateBeginningIndex = 8;
            int dateEndIndex = 15;
            String pre_ = orderFileName.substring(dateBeginningIndex, dateEndIndex);
            LocalDate date = LocalDate.parse(pre_, DateTimeFormatter.ofPattern("MMddyyyy"));
            return date;
        } catch (Exception e) {
            throw new FlooringMasteryPersistenceException("Failed to convert order file to date object", e);
        }
    }

    // --- Tax Code ---
    @Override
    public List<TaxCode> getAllTaxCodes() throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void loadOrders() throws FlooringMasteryPersistenceException {
        // Read in orders
        Scanner fileReader;
        List<String> orderFileNames = this.getOrderFileNames();
        int maxOrderNumber = -1;
        for (String orderFile: orderFileNames) {
            try {
                fileReader = new Scanner(new BufferedReader(new FileReader(ORDER_FILE)));
            } catch (FileNotFoundException e) {
                throw new FlooringMasteryPersistenceException("Could not load orders from memory.", e);
            }

            LocalDate orderDate = this.getDateFromOrderName(orderFile);
            String currentLine;
            Order currentOrder;

            Map<Integer, Order> ordersForCurrentDate = new HashMap<>();

            while (fileReader.hasNextLine()) {
                currentLine = fileReader.nextLine();
                currentOrder = unmarshallOrder(currentLine);
                if (currentOrder.getOrderNumber() > maxOrderNumber) {
                    maxOrderNumber = currentOrder.getOrderNumber();
                }
                ordersForCurrentDate.put(currentOrder.getOrderNumber(), currentOrder);
            }
            this.orders.put(orderDate, ordersForCurrentDate);
        }

    }

}
