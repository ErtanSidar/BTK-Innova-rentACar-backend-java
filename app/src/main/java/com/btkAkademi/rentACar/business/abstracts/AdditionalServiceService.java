package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.AdditionalServiceListDto;
import com.btkAkademi.rentACar.business.requests.additionalServiceRequests.CreateAdditionalServiceRequest;
import com.btkAkademi.rentACar.business.requests.additionalServiceRequests.UpdateAdditionalServiceRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.AdditionalService;

public interface AdditionalServiceService {
	
	DataResult<List<AdditionalServiceListDto>> getAll();

	Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest);

	List<AdditionalService> findByRental_Id(int id);
	
	DataResult<List<AdditionalServiceListDto>> findByRentalId(int id);

	Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest);

	Result delete(int id);
}
