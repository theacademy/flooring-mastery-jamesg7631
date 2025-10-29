package com.mthree.dao;

import com.mthree.model.TaxCode;

import java.util.List;

public interface TaxDao {
    List<TaxCode> getAllTaxCodes() throws FlooringMasteryPersistenceException;
}
