package com.mthree.dao;

import com.mthree.model.TaxCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaxDaoImplTest {

    @TempDir
    Path tempDir;

    private TaxDao testTaxDao;

    @BeforeEach
    void setUp() throws IOException {
        Path testFile = tempDir.resolve("testTaxes.txt");

        List<String> lines = List.of(
                "StateAbbreviation,StateName,TaxRate",
                "TX,Texas,4.45",
                "CA,California,25.00"
        );
        Files.write(testFile, lines);

        testTaxDao = new TaxDaoImpl(testFile.toString());
    }

    @Test
    void testGetAllTaxCodes() {
        // Act
        List<TaxCode> taxCodes = assertDoesNotThrow(() -> testTaxDao.getAllTaxCodes());

        // Assert
        assertNotNull(taxCodes);
        assertEquals(2, taxCodes.size());

        TaxCode firstCode = taxCodes.get(0);
        assertEquals("TX", firstCode.getStateAbbreviation());
        assertEquals("Texas", firstCode.getState());
        assertEquals(new BigDecimal("4.45"), firstCode.getTaxRate());
    }
}
