package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CarDamageService;
import com.btkAkademi.rentACar.business.abstracts.CarService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.CarDamageListDto;
import com.btkAkademi.rentACar.business.requests.carDamageRequests.CreateCarDamageRequests;
import com.btkAkademi.rentACar.business.requests.carDamageRequests.UpdateCarDamageRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.CarDamageDao;
import com.btkAkademi.rentACar.entities.concretes.CarDamage;

@Service
public class CarDamageManager implements CarDamageService {

	private ModelMapperService modelMapperService;
	private CarDamageDao carDamageDao;
	private CarService carService;

	@Autowired
	public CarDamageManager(ModelMapperService modelMapperService, CarDamageDao carDamageDao, CarService carService) {
		super();
		this.modelMapperService = modelMapperService;
		this.carDamageDao = carDamageDao;
		this.carService = carService;
	}

	@Override
	public DataResult<List<CarDamageListDto>> findAll() {
		List<CarDamage> carDamageList = this.carDamageDao.findAll();
		List<CarDamageListDto> response = carDamageList.stream()
				.map(CarDamage -> modelMapperService.forDto().map(CarDamage, CarDamageListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarDamageListDto>>(response);
	}

	@Override
	public Result add(CreateCarDamageRequests createCarDamageRequests) {

		CarDamage carDamage = modelMapperService.forRequest().map(createCarDamageRequests, CarDamage.class);
		this.carDamageDao.save(carDamage);
		return new SuccessResult(Messages.carDamageAdded);
	}

	@Override
	public Result update(UpdateCarDamageRequest updateCarDamageRequest) {
		Result result = BusinessRules.run(checkIfCarIsExists(updateCarDamageRequest.getCarId()));
		if (result != null) {
			return result;
		}
		CarDamage carDamage = this.modelMapperService.forRequest().map(updateCarDamageRequest, CarDamage.class);

		this.carDamageDao.save(carDamage);
		return new SuccessResult("car damage güncelendi");
	}

	@Override
	public Result delete(int id) {
		if (carDamageDao.existsById(id)) {
			carDamageDao.deleteById(id);
			return new SuccessResult("Car damage deleted");
		} else
			return new ErrorResult();
	}

	private Result checkIfCarIsExists(int carId) {
		if (!carService.findCarById(carId).isSuccess()) {
			return new ErrorResult("not found car");
		} else
			return new SuccessResult();
	}

}
