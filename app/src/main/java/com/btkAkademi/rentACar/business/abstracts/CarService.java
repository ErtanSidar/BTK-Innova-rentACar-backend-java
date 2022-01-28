package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.CarListDto;
import com.btkAkademi.rentACar.business.requests.carRequests.CreateCarRequest;
import com.btkAkademi.rentACar.business.requests.carRequests.UpdateCarRequests;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.Car;

public interface CarService {
	
	DataResult<List<CarListDto>> getAll();

	Result add(CreateCarRequest createCarRequest);

	Result update(UpdateCarRequests updateCarRequests);

	DataResult<Car> findByRentals_Id(int id);

	Result delete(int id);

	DataResult<CarListDto> findCarById(int id);
	
	DataResult<CarListDto> findByCarId(int id);
	
	DataResult<Car> findById(int id);
	
	DataResult<List<CarListDto>> findAllBySegmentId(int segmentId);
	
	DataResult<List<Integer>> findAvailableCarBySegment(int segmentId);
} 

