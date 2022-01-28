package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CityService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.CityListDto;
import com.btkAkademi.rentACar.business.requests.cityRequests.CreateCityRequest;
import com.btkAkademi.rentACar.business.requests.cityRequests.UpdateCityRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.CityDao;
import com.btkAkademi.rentACar.entities.concretes.City;

@Service
public class CityManager implements CityService {

	private CityDao cityDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CityManager(CityDao cityDao, ModelMapperService modelMapperService) {
		this.cityDao = cityDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCityRequest createCityRequest) {
		/*
		 * Result result = BusinessRules.run();
		 * 
		 * if(!result.isSuccess()) { return result; }
		 */
		City city = modelMapperService.forRequest().map(createCityRequest, City.class);
		this.cityDao.save(city);
		return new SuccessResult("Şehir eklendi");
	}

	@Override
	public Result update(UpdateCityRequest updateCityRequest) {
		Result result = BusinessRules.run(checkIfCityNameExists(updateCityRequest.getCityName()));
		if (result != null) {
			return result;
		}
		City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);
		this.cityDao.save(city);
		return new SuccessResult(Messages.cityUpdated);
	}

	@Override

	public Result delete(int id) {
		if (cityDao.existsById(id)) {
			cityDao.deleteById(id);
			return new SuccessResult(Messages.cityDeleted);
		}
		return new ErrorResult();
	}

	private Result checkIfCityNameExists(String cityName) {
		if (cityDao.findByName(cityName) != null) {
			return new ErrorResult(Messages.cityNameExists);
		}
		return new SuccessResult();
	}

	@Override
	public DataResult<List<CityListDto>> findAll() {
		List<City> cityList = this.cityDao.findAllByOrderByNameAsc();
		List<CityListDto> response = cityList.stream()
				.map(city -> modelMapperService.forDto().map(city, CityListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CityListDto>>(response);
	}

}
