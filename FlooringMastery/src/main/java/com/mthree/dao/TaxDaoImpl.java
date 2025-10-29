package com.mthree.dao;

import com.mthree.model.TaxCode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.*;

public class TaxDaoImpl implements TaxDao{
    private List<TaxCode> allTaxes;
    private final String TAXES_FILE;
    private final String DELIMITER = ",";

    public TaxDaoImpl(String productFile) {
        this.TAXES_FILE = productFile;
        this.allTaxes = new ArrayList<>();
    }

    public TaxDaoImpl() {
        this.TAXES_FILE = Paths.get("SampleFileData_floor", "SampleFileData", "Data", "Taxes.txt").toString();
        this.allTaxes = new ArrayList<>();
    }

    @Override
    public List<TaxCode> getAllTaxCodes() throws FlooringMasteryPersistenceException {
        this.loadFile();
        return this.allTaxes;
    }

    private void loadFile() throws FlooringMasteryPersistenceException {
        this.allTaxes = new ArrayList<>();

        // Don't think I need to close the scanner since a try with resources has been used
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(this.TAXES_FILE)))) {
            String currentLine;
            TaxCode currentTaxCode;
            // Consume the header
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                String[] parts = currentLine.split(",");
                currentTaxCode = new TaxCode(parts[0], parts[1], new BigDecimal(parts[2]));
                this.allTaxes.add(currentTaxCode);
            }
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("File not found: " + this.TAXES_FILE, e);
        }
    }
}
