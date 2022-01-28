package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.CarDamageListDto;
import com.btkAkademi.rentACar.business.requests.carDamageRequests.CreateCarDamageRequests;
import com.btkAkademi.rentACar.business.requests.carDamageRequests.UpdateCarDamageRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

public interface CarDamageService {
	
	DataResult<List<CarDamageListDto>> findAll();

	Result add(CreateCarDamageRequests createCarDamageRequests);

	Result update(UpdateCarDamageRequest updateCarDamageRequest);

	Result delete(int id);
}
