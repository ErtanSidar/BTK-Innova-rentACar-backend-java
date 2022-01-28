package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.AdditionalServiceService;
import com.btkAkademi.rentACar.business.abstracts.RentalService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.AdditionalServiceListDto;
import com.btkAkademi.rentACar.business.requests.additionalServiceRequests.CreateAdditionalServiceRequest;
import com.btkAkademi.rentACar.business.requests.additionalServiceRequests.UpdateAdditionalServiceRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.AdditionalServiceDao;
import com.btkAkademi.rentACar.entities.concretes.AdditionalService;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {

	private AdditionalServiceDao additionalServiceDao;
	private ModelMapperService modelMapperService;
	private RentalService rentalService;

	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService,
			RentalService rentalService) {
		this.additionalServiceDao = additionalServiceDao;
		this.modelMapperService = modelMapperService;
		this.rentalService = rentalService;
	}

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		/*
		 * Result result = BusinessRules.run();
		 * 
		 * if(!result.isSuccess()) { return result; }
		 */

		AdditionalService additionalService = modelMapperService.forRequest().map(createAdditionalServiceRequest,
				AdditionalService.class);
		this.additionalServiceDao.save(additionalService);
		return new SuccessResult("Ek hizmet eklendi");
	}

	@Override
	public List<AdditionalService> findByRental_Id(int id) {
		return null;
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		Result result = BusinessRules.run(checkIfRentalExists(updateAdditionalServiceRequest.getRentalId()));

		if (result != null) {
			return result;
		}
		AdditionalService additionalService = modelMapperService.forRequest().map(updateAdditionalServiceRequest,
				AdditionalService.class);
		additionalServiceDao.save(additionalService);
		return new SuccessResult(Messages.additionalServiceUpdated);
	}

	// delete
	@Override
	public Result delete(int id) {
		if (additionalServiceDao.existsById(id)) {
			additionalServiceDao.deleteById(id);
			return new SuccessResult("Ek Hizmet Silindi");
		} else
			return new ErrorResult();
	}

	private Result checkIfRentalExists(int rentalId) {
		if (!rentalService.findById(rentalId).isSuccess()) {
			return new ErrorResult(Messages.rentalIsNotFound);
		} else
			return new SuccessResult();
	}

	@Override
	public DataResult<List<AdditionalServiceListDto>> getAll() {
		List<AdditionalService> additionalServicesList = this.additionalServiceDao.findAll();
		List<AdditionalServiceListDto> response = additionalServicesList.stream().map(
				AdditionalService -> modelMapperService.forDto().map(AdditionalService, AdditionalServiceListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<AdditionalServiceListDto>>(response);
	}


	@Override
	public DataResult<List<AdditionalServiceListDto>> findByRentalId(int id) {
		return null;
	}

}
