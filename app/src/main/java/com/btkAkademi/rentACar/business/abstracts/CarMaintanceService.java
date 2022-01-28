package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.CarMaintanceListDto;
import com.btkAkademi.rentACar.business.requests.carMaintanceRequests.CreateMaintanceRequest;
import com.btkAkademi.rentACar.business.requests.carMaintanceRequests.UpdateCarMaintanceRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

public interface CarMaintanceService {
	
	Result add(CreateMaintanceRequest createMaintanceRequest);

	DataResult<List<CarMaintanceListDto>> getAll();

	boolean isCarInMaintenance(int id);

	Result update(UpdateCarMaintanceRequest updateCarMaintanceRequest);

	Result delete(int id);
}
