package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.RentalListDto;
import com.btkAkademi.rentACar.business.requests.rentalRequests.CreateRentalRequest;
import com.btkAkademi.rentACar.business.requests.rentalRequests.UpdateRentalRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.Rental;

public interface RentalService {

	Result addCorporate(CreateRentalRequest createRentalRequest);

	Result addIndividual(CreateRentalRequest createRentalRequest);

	DataResult<RentalListDto> getById(int id);

	DataResult<Rental> getByCarId(int id);

	DataResult<List<RentalListDto>> getAll();

	boolean isCarRented(int id);

	DataResult<Rental> findById(int id);
	
	DataResult<List<RentalListDto>> findByReturnDateIsNull();

	DataResult<List<RentalListDto>> findAll(int pageNo, int pageSize);

	Result update(UpdateRentalRequest updateRentalRequest);

	Result delete(int id);
}
