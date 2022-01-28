package com.btkAkademi.rentACar.core.utilities.adapters.abstracts;

import com.btkAkademi.rentACar.core.utilities.results.DataResult;

public interface FindexScore {
	int getScoreOfIndividualCustomer(String identityNumber) ;
	int getScoreOfCorporateCustomer(String taxNumber);
}
