package com.mthree.dao;

import com.mthree.model.TaxCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TaxDaoStubImpl implements TaxDao {

    private List<TaxCode> taxCodes = new ArrayList<>();

    public TaxDaoStubImpl() {
        // Create hard-coded tax codes for testing
        taxCodes.add(new TaxCode("TX", "Texas", new BigDecimal("4.45")));
        taxCodes.add(new TaxCode("WA", "Washington", new BigDecimal("9.25")));
        taxCodes.add(new TaxCode("CA", "California", new BigDecimal("25.00")));
    }

    @Override
    public List<TaxCode> getAllTaxCodes() throws FlooringMasteryPersistenceException {
        return taxCodes;
    }
}
