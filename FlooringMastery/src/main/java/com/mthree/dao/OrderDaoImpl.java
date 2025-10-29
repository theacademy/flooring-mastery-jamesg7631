package com.mthree.dao;

import com.mthree.model.Order;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@Component
public class OrderDaoImpl implements OrderDao {
    private Map<LocalDate, Map<Integer,Order>> orders;

    private final String ORDER_FILE_DIR;
    private final String EXPORT_FILE;
    private static final String DELIMETER = ",";
    private static final String HEADER = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
    // Order Section

    public OrderDaoImpl(String orderTextFile, String exportFile) {
        this.ORDER_FILE_DIR = orderTextFile;
        this.EXPORT_FILE = exportFile;
        this.orders = new HashMap<>();
    }
    public OrderDaoImpl() {
        this.ORDER_FILE_DIR = "SampleFileData_floor/SampleFileData/Orders/";
        this.EXPORT_FILE = "SampleFileData_floor/SampleFileData/Backup/DataExport.txt";
        this.orders = new HashMap<>();
    }

    private String getOrderFileName(LocalDate orderDate) {
        return "Orders_" + orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy")) + ".txt";
    }

    private String marshallOrder(Order order) {
        // OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total
        return order.getOrderNumber() + DELIMETER +
                order.getCustomerName() + DELIMETER +
                order.getState() + DELIMETER +
                order.getTaxRate().toPlainString() + DELIMETER +
                order.getProductType() + DELIMETER +
                order.getArea().toPlainString() + DELIMETER +
                order.getCostPerSquareFoot().toPlainString() + DELIMETER +
                order.getLaborCostPerSquareFoot().toPlainString() + DELIMETER +
                order.getMaterialCost().toPlainString() + DELIMETER +
                order.getLaborCost().toPlainString() + DELIMETER +
                order.getTax().toPlainString() + DELIMETER +
                order.getTotal().toPlainString();
    }

    @Override
    public void addOrder(LocalDate orderDate, Order order) throws FlooringMasteryPersistenceException {
        loadOrders();
        Map<Integer, Order> ordersForDate = this.orders.get(orderDate);
        if (ordersForDate == null) {
            ordersForDate = new HashMap<>();
            this.orders.put(orderDate, ordersForDate);
        }
        ordersForDate.put(order.getOrderNumber(), order);
        writeOrders(orderDate);
    }

    @Override
    public void editOrder(Order order) throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void removeOrder(LocalDate orderDate, Order order) throws FlooringMasteryPersistenceException {
        loadOrders();
        Map<Integer, Order> ordersForDate = this.orders.get(orderDate);
        if (ordersForDate == null) {
            throw new FlooringMasteryPersistenceException("No orders for date " + orderDate + " to remove from.");
        }
        ordersForDate.remove(order.getOrderNumber());
        writeOrders(orderDate);
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber) throws FlooringMasteryPersistenceException {
        loadOrders();
        Map<Integer, Order> ordersForDate = this.orders.get(orderDate);
        if (ordersForDate == null) {
            throw new OrderDoesNotExistException("No orders found for date: " + orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy")));
        }

        Order order = ordersForDate.get(orderNumber);
        if (order == null) {
            throw new OrderDoesNotExistException(String.format("No order with number " + orderNumber + " found for date: " + orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy"))));
        }

        return order;
    }

    @Override
    public Map<Integer, Order> getOrderByDate(LocalDate orderDate) {
        this.orders = new HashMap<>();
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
    public List<Order> getAllOrders() throws FlooringMasteryPersistenceException {
        loadOrders();
        List<Order> allOrders = new ArrayList<>();
        for (Map<Integer, Order> dailyOrdersMap: this.orders.values()) {
            for (Order order : dailyOrdersMap.values()) {
                allOrders.add(order);
            }
        }
        return allOrders;
    }

    private List<String> getOrderFileNames() {
        Path path = Paths.get(ORDER_FILE_DIR);
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
            int dateEndIndex = 16;
            String pre_ = orderFileName.substring(dateBeginningIndex, dateEndIndex);
            LocalDate date = LocalDate.parse(pre_, DateTimeFormatter.ofPattern("MMddyyyy"));
            return date;
        } catch (Exception e) {
            throw new FlooringMasteryPersistenceException("Failed to convert order file to date object", e);
        }
    }

    // --- Tax Code ---


    private void loadOrders() throws FlooringMasteryPersistenceException {
        // Need to break this method down. Currently, will be a nightmare to test
        // Clear in-memory map to reload from files
        this.orders = new HashMap<>();
        Scanner fileReader;
        List<String> orderFileNames = this.getOrderFileNames();
        int maxOrderNumber = 0; // Start at 0

        if (orderFileNames.isEmpty()) {
            Order.nextOrderNumber = maxOrderNumber + 1;
            return;
        }

        for (String orderFile: orderFileNames) {
            try {
                fileReader = new Scanner(new BufferedReader(new FileReader(Paths.get(ORDER_FILE_DIR, orderFile).toString())));
            } catch (FileNotFoundException e) {
                throw new FlooringMasteryPersistenceException("Could not load orders from memory.", e);
            }

            LocalDate orderDate = this.getDateFromOrderName(orderFile);
            String currentLine;
            Order currentOrder;

            Map<Integer, Order> ordersForCurrentDate = new HashMap<>();

            // Skip header line
            try{
                if (fileReader.hasNextLine()) {
                    fileReader.nextLine();
                } else {
                    continue; // Empty file, skip
                }
            } catch (Exception e) {
                throw new FlooringMasteryPersistenceException("File is empty or corrupt: " + orderFile, e);
            }

            while (fileReader.hasNextLine()) {
                currentLine = fileReader.nextLine();
                currentOrder = unmarshallOrder(currentLine);
                if (currentOrder.getOrderNumber() > maxOrderNumber) {
                    maxOrderNumber = currentOrder.getOrderNumber();
                }
                ordersForCurrentDate.put(currentOrder.getOrderNumber(), currentOrder);
            }
            fileReader.close();
            if (!ordersForCurrentDate.isEmpty()) {
                this.orders.put(orderDate, ordersForCurrentDate);
            }
        }
        Order.nextOrderNumber = maxOrderNumber + 1;
    }

    // Should actually not be in this class. Need to move
    private void writeOrders(LocalDate orderDate) throws FlooringMasteryPersistenceException {
        PrintWriter out;
        String orderFile = getOrderFileName(orderDate);
        Map<Integer, Order> ordersForDate = this.orders.get(orderDate);

        try {
            out = new PrintWriter(new FileWriter(Paths.get(ORDER_FILE_DIR, orderFile).toString()));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not save order data.", e);
        }

        // Write header
        out.println(HEADER);

        // Write orders
        if (ordersForDate != null) {
            for (Order order : ordersForDate.values()) {
                out.println(marshallOrder(order));
                out.flush();
            }
        }
        out.close();
    }

}
