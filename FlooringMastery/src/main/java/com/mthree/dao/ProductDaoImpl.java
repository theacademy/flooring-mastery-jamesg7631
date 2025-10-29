package com.mthree.dao;

import com.mthree.model.Product;
import com.mthree.model.TaxCode;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {
    private Map<String, Product> allProducts;
    private final String DELIMITER = ",";
    private final String PRODUCT_FILE;

    public ProductDaoImpl() {
        this.PRODUCT_FILE = Paths.get("SampleFileData_floor", "SampleFileData", "Data", "Products.txt").toString();
        this.allProducts = new HashMap<>();
    }

    public ProductDaoImpl(String fileName) {
        this.PRODUCT_FILE = fileName;
        this.allProducts = new HashMap<>();
    }

    @Override
    public List<Product> getAllProducts() {
        loadFile();
        List<Product> result = new ArrayList<>();
        for(Product p: result) {
            result.add(p);
        }
        return result;
    }

    private Product unMarshallProduct(String productAsText) {
        String[] productTokens = productAsText.split(DELIMITER);
        String productType = productTokens[0];
        BigDecimal costPerSquareFoot = new BigDecimal(productTokens[1]);
        BigDecimal laborCost = new BigDecimal(productTokens[2]);
        Product product = new Product(productType, costPerSquareFoot, laborCost);
        return product;
    }

    private void loadFile() {
        this.allProducts = new HashMap<>();
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(this.PRODUCT_FILE)))) {
            String currentLine;
            Product currentProduct;
            // Consume the header
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                currentProduct = this.unMarshallProduct(currentLine);
                this.allProducts.put(currentProduct.getProductType(), currentProduct);
            }
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("File not found: " + this.PRODUCT_FILE, e);
        }
    }
}
