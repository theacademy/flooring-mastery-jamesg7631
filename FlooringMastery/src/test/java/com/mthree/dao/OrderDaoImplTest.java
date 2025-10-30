package com.mthree.dao;

import com.mthree.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoImplTest {

    @TempDir
    Path tempDir;

    private OrderDao testOrderDao;
    private Path orderDir;
    private Order testOrder1;
    private LocalDate testDate;

    private final String HEADER = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";

    @BeforeEach
    void setUp() throws IOException {
        // Create the "Orders" directory inside the temp dir, as the DAO expects
        orderDir = tempDir.resolve("Orders");
        Files.createDirectories(orderDir);

        // Define test data
        testDate = LocalDate.of(2025, 12, 25);
        testOrder1 = new Order(1, "Ada Lovelace", "CA", new BigDecimal("25.00"), "Tile",
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("249.00"),
                new BigDecimal("871.50"), new BigDecimal("1033.35"),
                new BigDecimal("476.21"), new BigDecimal("2381.06"));

        Order.nextOrderNumber = 1;

        // Create a sample order file
        Path orderFile = orderDir.resolve("Orders_12252025.txt");
        String line1 = "1,Ada Lovelace,CA,25.00,Tile,249.00,3.50,4.15,871.50,1033.35,476.21,2381.06";
        Files.write(orderFile, List.of(HEADER, line1));

        // Initialize the DAO, pointing to the *parent* of the "Orders" directory
        testOrderDao = new OrderDaoImpl(tempDir.toString() + "/", "dummyExport.txt");
    }

    @Test
    void testGetOrderValid() {
        // Act
        Order retrievedOrder = assertDoesNotThrow(() -> testOrderDao.getOrder(testDate, 1));

        // Assert
        assertNotNull(retrievedOrder);
        assertEquals(testOrder1, retrievedOrder);
        assertEquals(2, Order.nextOrderNumber);
    }

    @Test
    void testGetOrderInvalidOrderNumber() {
        // Act & Assert
        assertThrows(OrderDoesNotExistException.class,
                () -> testOrderDao.getOrder(testDate, 99),
                "Should throw exception for non-existent order number.");
    }

    @Test
    void testAddOrderNewDate() throws IOException {
        // Arrange
        Order.nextOrderNumber = 2; // Set it for the new order
        LocalDate newDate = LocalDate.of(2025, 12, 26);
        Order testOrder2 = new Order(2, "Grace Hopper", "WA", new BigDecimal("9.25"), "Carpet",
                new BigDecimal("2.25"), new BigDecimal("2.10"), new BigDecimal("150.00"),
                new BigDecimal("337.50"), new BigDecimal("315.00"),
                new BigDecimal("60.36"), new BigDecimal("712.86"));

        // Act
        assertDoesNotThrow(() -> testOrderDao.addOrder(newDate, testOrder2));

        // Assert
        // 1. Check if the new file was created
        Path newOrderFile = orderDir.resolve("Orders_12262025.txt");
        assertTrue(Files.exists(newOrderFile), "New order file should be created.");

        // 2. Read the file and check its contents
        List<String> lines = Files.readAllLines(newOrderFile);
        assertEquals(2, lines.size(), "File should have header and one order.");
        assertTrue(lines.get(1).contains("Grace Hopper"), "File should contain new order data.");
    }

    @Test
    void testRemoveOrder() throws IOException {
        // Act
        assertDoesNotThrow(() -> testOrderDao.removeOrder(testDate, testOrder1));

        // Assert
        // 1. Check retrieval
        assertThrows(OrderDoesNotExistException.class,
                () -> testOrderDao.getOrder(testDate, 1),
                "Order should have been removed.");

        // 2. Check file system
        Path orderFile = orderDir.resolve("Orders_12252025.txt");
        List<String> lines = Files.readAllLines(orderFile);

        // The DAO's logic is to rewrite the file with remaining orders.
        // Since there are no more, it should just write the header.
        assertEquals(1, lines.size(), "File should only contain the header.");
        assertEquals(HEADER, lines.get(0));
    }
}