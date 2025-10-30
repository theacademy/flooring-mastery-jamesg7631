package com.mthree.dao;

import com.mthree.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoImplTest {

    @TempDir
    Path tempDir;

    private ProductDao testProductDao;

    @BeforeEach
    void setUp() throws IOException {
        // Create a test file in the temporary directory
        Path testFile = tempDir.resolve("testProducts.txt");

        // Write sample product data to the test file
        List<String> lines = List.of(
                "ProductType,CostPerSquareFoot,LaborCostPerSquareFoot",
                "Tile,3.50,4.15",
                "Wood,5.15,4.75"
        );
        Files.write(testFile, lines);

        // Initialize the DAO with the path to the test file
        testProductDao = new ProductDaoImpl(testFile.toString());
    }

    @Test
    void testGetAllProducts() {
        // Act
        List<Product> products = testProductDao.getAllProducts();

        // Assert
        assertNotNull(products);
        assertEquals(2, products.size());

        Product firstProduct = products.get(0);
        assertEquals("Tile", firstProduct.getProductType());
        assertEquals(new BigDecimal("3.50"), firstProduct.getCostPerSquareFoot());
        assertEquals(new BigDecimal("4.15"), firstProduct.getLaborCostPerSquareFoot());
    }

    @Test
    void testGetProductValid() {
        // Act
        Product product = testProductDao.getProduct(2); // Should be "Wood"

        // Assert
        assertNotNull(product);
        assertEquals("Wood", product.getProductType());
    }

    @Test
    void testGetProductInvalid() {
        // Act
        Product product = testProductDao.getProduct(99); // Invalid product number

        // Assert
        assertNull(product);
    }
}
