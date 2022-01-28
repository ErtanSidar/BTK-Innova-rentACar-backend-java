package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.IndividualCustomerListDto;
import com.btkAkademi.rentACar.business.requests.individualRequest.CreateIndividualCustomerRequest;
import com.btkAkademi.rentACar.business.requests.individualRequest.UpdateIndividualCustomerRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.IndividualCustomer;

public interface IndividualCustomerService {
	
	Result add(CreateIndividualCustomerRequest createIndividualRequest);

	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest);

	Result delete(int id);

	DataResult<List<IndividualCustomerListDto>> findAll(int pageNo, int pageSize);

	DataResult<IndividualCustomer> findById(int id);
}
