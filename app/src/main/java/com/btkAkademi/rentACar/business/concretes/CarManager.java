package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CarService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.CarListDto;
import com.btkAkademi.rentACar.business.requests.carRequests.CreateCarRequest;
import com.btkAkademi.rentACar.business.requests.carRequests.UpdateCarRequests;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorDataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.CarDao;
import com.btkAkademi.rentACar.entities.concretes.Car;

@Service
public class CarManager implements CarService {

	private CarDao carDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService) {
		super();
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<CarListDto>> getAll() {
		//Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Car> brandList = this.carDao.findAll();
		List<CarListDto> response = brandList.stream()
				.map(brand -> modelMapperService.forDto().map(brand, CarListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarListDto>>(response);
	}

	@Override
	public DataResult<CarListDto> findCarById(int id) {
		if (carDao.existsById(id)) {

			CarListDto response = modelMapperService.forDto().map(carDao.findById(id), CarListDto.class);

			return new SuccessDataResult<CarListDto>(response);
		} else
			return new ErrorDataResult<>();
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		
		Car car = modelMapperService.forRequest().map(createCarRequest, Car.class);
		this.carDao.save(car);
		return new SuccessResult(Messages.carAdded);
	}

	@Override
	public Result update(UpdateCarRequests updateCarRequests) {
		Car car = this.carDao.findById(updateCarRequests.getId());
		if (car == null) {
			return new ErrorResult(Messages.notFoundCar);
		}
		car = modelMapperService.forRequest().map(updateCarRequests, Car.class);
		this.carDao.save(car);
		return new SuccessResult(Messages.updateCar);
	}

	@Override
	public Result delete(int id) {
		if (carDao.existsById(id)) {
			carDao.deleteById(id);
			return new SuccessResult(Messages.carDeleted);
		}

		return new ErrorResult();
	}

	@Override
	public DataResult<List<CarListDto>> findAllBySegmentId(int segmentId) {
		List<Car> cars = carDao.findAllBySegmentId(segmentId);
		List<CarListDto> response = cars.stream().map(car -> modelMapperService.forDto().map(car, CarListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarListDto>>(response);
	}

	@Override
	public DataResult<Car> findByRentals_Id(int id) {
		return new SuccessDataResult<Car>(this.carDao.findByRental_Id(id));
	}

	@Override
	public DataResult<Car> findById(int id) {
		return new SuccessDataResult<Car>(this.carDao.findById(id));
	}

	@Override
	public DataResult<List<Integer>> findAvailableCarBySegment(int segmentId) {
			if (carDao.findAvailableCarBySegment(segmentId).size() < 1) {
				return new ErrorDataResult<List<Integer>>();
			} else
				return new SuccessDataResult<List<Integer>>(this.carDao.findAvailableCarBySegment(segmentId));

		
		
	}
	@Override
	public DataResult<CarListDto> findByCarId(int id) {
		if (carDao.existsById(id)) {

			CarListDto response = modelMapperService.forDto().map(carDao.findById(id), CarListDto.class);

			return new SuccessDataResult<CarListDto>(response);
		} else
			return new ErrorDataResult<>("Araba bulunamadı");
	}

}
