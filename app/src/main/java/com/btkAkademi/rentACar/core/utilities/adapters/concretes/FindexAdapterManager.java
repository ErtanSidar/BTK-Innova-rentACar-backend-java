package com.btkAkademi.rentACar.core.utilities.adapters.concretes;

import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.core.utilities.adapters.abstracts.FindexScore;
import com.btkAkademi.rentACar.core.utilities.fakeManagers.FakeFindexScoreSystemManager;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
@Service
public class FindexAdapterManager implements FindexScore{
	
	FakeFindexScoreSystemManager fakeFindexScoreSystemManager= new FakeFindexScoreSystemManager();
	
	@Override
	public int getScoreOfIndividualCustomer(String identityNumber) {
		return fakeFindexScoreSystemManager.getScoreOfIndividualCustomer(identityNumber);
	
	}

	@Override
	public int getScoreOfCorporateCustomer(String taxNumber) {
		return fakeFindexScoreSystemManager.getScoreOfCorporateCustomer(taxNumber);
		
	}

}
