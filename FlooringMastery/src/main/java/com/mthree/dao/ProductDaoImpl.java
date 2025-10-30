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
    private Map<Integer, Product> allProducts;
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
        for(Product p: allProducts.values()) {
            result.add(p);
        }
        return result;
    }

    public Product getProduct(int productNumber) {
        // Nearing the territory of business logic. Need to reconsider when I get time
        // It might be better if we always return the map from the dao instead
        // of the list. That would make it easier to handle business logic in the
        // service layer. For now, I'll leave this as future work

        // This is would be handy as I then could remind the user of valid inputs based on the product numbers
        try {
            loadFile();
            return allProducts.get(productNumber);
        } catch (Exception e) {
            String errorMessage = String.format("Product not found %s", productNumber);
            throw new FlooringMasteryPersistenceException(errorMessage, e);
        }
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

            int productNumber = 1;
            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                currentProduct = this.unMarshallProduct(currentLine);
                this.allProducts.put(productNumber, currentProduct);
                productNumber++;
            }
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("File not found: " + this.PRODUCT_FILE, e);
        }
    }
}
