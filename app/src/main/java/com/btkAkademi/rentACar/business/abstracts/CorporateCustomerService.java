package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.CorporateCustomerListDto;
import com.btkAkademi.rentACar.business.requests.corporateRequests.CreateCorporateCustomerRequest;
import com.btkAkademi.rentACar.business.requests.corporateRequests.UpdateCorporateCustomerRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.CorporateCustomer;
import com.btkAkademi.rentACar.entities.concretes.IndividualCustomer;

public interface CorporateCustomerService {
	
	DataResult<List<CorporateCustomerListDto>> findAll(int pageNo, int pageSize);

	Result add(CreateCorporateCustomerRequest createCorporateRequest);

	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);

	Result delete(int id);
	
	DataResult<CorporateCustomer> findById(int id);
}
