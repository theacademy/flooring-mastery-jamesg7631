package com.mthree.dao;

import com.mthree.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDaoStubImpl implements ProductDao {

    private Map<Integer, Product> products = new HashMap<>();

    public ProductDaoStubImpl() {
        // Create hard-coded products for testing
        Product p1 = new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"));
        Product p2 = new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75"));
        products.put(1, p1);
        products.put(2, p2);
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Product getProduct(int productNumber) {
        // This will return null if the productNumber is not 1 or 2,
        // which is perfect for testing failure cases.
        return products.get(productNumber);
    }
}
