package com.mthree.service;

import com.mthree.dao.*;
import com.mthree.model.Order;
import com.mthree.model.Product;
import com.mthree.model.TaxCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class FlooringMasteryServiceLayerImplTest {

    private FlooringMasteryServiceLayerImpl service;
    private OrderDaoStubImpl orderDaoStub;
    private TaxDaoStubImpl taxDaoStub;
    private ProductDaoStubImpl productDaoStub;

    @BeforeEach
    void setUp() {
        orderDaoStub = new OrderDaoStubImpl();
        productDaoStub = new ProductDaoStubImpl();
        taxDaoStub = new TaxDaoStubImpl();

        service = new FlooringMasteryServiceLayerImpl(orderDaoStub, taxDaoStub, productDaoStub);
    }

    // --- Validation Tests ---

    @Test
    void testValidateNewOrderDateValid() {
        String futureDateStr = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        LocalDate result = assertDoesNotThrow(() -> service.validateNewOrderDate(futureDateStr));
        assertEquals(LocalDate.now().plusDays(1), result);
    }

    @Test
    void testValidateNewOrderDatePast() {
        OrderDateInvalidException ex = assertThrows(OrderDateInvalidException.class,
                () -> service.validateNewOrderDate("01-01-2000"));
        assertTrue(ex.getMessage().contains("Order must be placed after:"));
    }

    @Test
    void testValidateNewOrderDateToday() {
        String todayStr = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        OrderDateInvalidException ex = assertThrows(OrderDateInvalidException.class,
                () -> service.validateNewOrderDate(todayStr));
        assertTrue(ex.getMessage().contains("Order must be placed after:"));
    }

    @Test
    void testValidateCustomerNameEmpty() {
        InvalidCustomerNameException ex = assertThrows(InvalidCustomerNameException.class,
                () -> service.validateCustomerName(""));
        assertEquals("Customer name must not be blank.", ex.getMessage());
    }

    @Test
    void testValidateCustomerNameInvalidChars() {
        InvalidCustomerNameException ex = assertThrows(InvalidCustomerNameException.class,
                () -> service.validateCustomerName("Invalid!Name"));
        assertTrue(ex.getMessage().contains("All characters must be between"));
    }

    @Test
    void testValidateProductAreaValid() {
        BigDecimal result = assertDoesNotThrow(() -> service.validateProductArea("100"));
        assertEquals(new BigDecimal("100"), result);
    }

    @Test
    void testValidateProductAreaTooSmall() {
        FlooringMasteryInvalidAreaException ex = assertThrows(FlooringMasteryInvalidAreaException.class,
                () -> service.validateProductArea("99.99"));
        assertTrue(ex.getMessage().contains("Area must be a number greater than 100 sq foot"));
    }

    // --- Logic Tests (using stubs) ---

    @Test
    void testGetTaxCodeValid() {
        // Arrange (data is already in TaxDaoStubImpl)

        // Act
        TaxCode result = assertDoesNotThrow(() -> service.getTaxCode("tx")); // Test lower-case

        // Assert
        assertNotNull(result);
        assertEquals("TX", result.getStateAbbreviation());
        assertEquals("Texas", result.getState());
    }

    @Test
    void testGetTaxCodeInvalid() {
        // Arrange (data is already in TaxDaoStubImpl)

        // Act & Assert
        FlooringMasteryPersistenceException ex = assertThrows(FlooringMasteryPersistenceException.class,
                () -> service.getTaxCode("FL"));
        assertTrue(ex.getMessage().contains("State: FL could not be found!"));
    }

    @Test
    void testGetProductValid() {
        // Arrange (data is in ProductDaoStubImpl)

        // Act
        Product result = assertDoesNotThrow(() -> service.getProduct("1"));

        // Assert
        assertNotNull(result);
        assertEquals("Tile", result.getProductType());
    }

    @Test
    void testGetProductInvalidNumber() {
        // Arrange (stub returns null for "99")

        // Act & Assert
        FlooringMasteryInvalidProductNumberException ex = assertThrows(
                FlooringMasteryInvalidProductNumberException.class,
                () -> service.getProduct("99"));
        assertTrue(ex.getMessage().contains("Product not found 99"));
    }

    @Test
    void testGetOrderValid() {
        // Arrange
        LocalDate date = LocalDate.now().plusDays(1);
        Order testOrder = new Order(1, "Test", "TX", new BigDecimal("4.45"), "Tile",
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("100"),
                new BigDecimal("350"), new BigDecimal("415"), new BigDecimal("34.24"),
                new BigDecimal("799.24"));
        // "Seed" the data into the stub
        assertDoesNotThrow(() -> orderDaoStub.addOrder(date, testOrder));

        // Act
        Order result = assertDoesNotThrow(() -> service.getOrder(date, 1));

        // Assert
        assertNotNull(result);
        assertEquals(testOrder, result);
    }

    @Test
    void testGetOrderInvalidDate() {
        // Arrange (stub is empty)
        LocalDate date = LocalDate.now().plusDays(1);

        // Act & Assert
        OrderDoesNotExistException ex = assertThrows(OrderDoesNotExistException.class,
                () -> service.getOrder(date, 1));
        assertTrue(ex.getMessage().contains("cannot be found"));
    }
}
