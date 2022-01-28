package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CarMaintanceService;
import com.btkAkademi.rentACar.business.abstracts.RentalService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.CarMaintanceListDto;
import com.btkAkademi.rentACar.business.requests.carMaintanceRequests.CreateMaintanceRequest;
import com.btkAkademi.rentACar.business.requests.carMaintanceRequests.UpdateCarMaintanceRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.CarMaintanceDao;
import com.btkAkademi.rentACar.entities.concretes.CarMaintance;

@Service
public class CarMaintanceManager implements CarMaintanceService {

	private CarMaintanceDao carMaintanceDao;
	private ModelMapperService modelMapperService;
	private RentalService rentalService;

	@Autowired
	public CarMaintanceManager(CarMaintanceDao carMaintanceDao, ModelMapperService modelMapperService,
			RentalService rentalService) {
		super();
		this.carMaintanceDao = carMaintanceDao;
		this.modelMapperService = modelMapperService;
		this.rentalService = rentalService;
	}

	@Override
	public Result add(CreateMaintanceRequest createMaintanceRequest) {

		Result result = BusinessRules.run(checkIfCarIsRented(createMaintanceRequest.getCarId()));

		if (result != null) {
			return result;
		}

		CarMaintance carMaintance = modelMapperService.forRequest().map(createMaintanceRequest, CarMaintance.class);
		this.carMaintanceDao.save(carMaintance);
		return new SuccessResult(Messages.carAddedToMaintance);
	}

	@Override
	public DataResult<List<CarMaintanceListDto>> getAll() {
		List<CarMaintance> carMaintanceList = this.carMaintanceDao.findAll();
		List<CarMaintanceListDto> response = carMaintanceList.stream()
				.map(carMaintance -> modelMapperService.forDto().map(carMaintance, CarMaintanceListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarMaintanceListDto>>(response);
	}

	@Override
	public boolean isCarInMaintenance(int id) {
		if (this.carMaintanceDao.findByCarIdAndReturnedDateIsNull(id) != null) {
			return true;
		}

		return false;
	}

	private Result checkIfCarIsRented(int carId) {
		if (rentalService.isCarRented(carId)) {
			return new ErrorResult("Araç kirada");
		} else
			return new SuccessResult();
	}

	@Override
	public Result update(UpdateCarMaintanceRequest updateCarMaintanceRequest) {
		CarMaintance carMaintance = this.modelMapperService.forRequest().map(updateCarMaintanceRequest,
				CarMaintance.class);

		this.carMaintanceDao.save(carMaintance);
		return new SuccessResult("carMaintananceUpdated");
	}

	@Override
	public Result delete(int id) {
		if (carMaintanceDao.existsById(id)) {
			carMaintanceDao.deleteById(id);
			return new SuccessResult("carMaintananceDeleted");
		} else
			return new ErrorResult();
	}

}
