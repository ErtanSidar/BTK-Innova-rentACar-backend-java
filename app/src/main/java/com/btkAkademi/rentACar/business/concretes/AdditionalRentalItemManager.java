package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.AdditionalRentalItemService;
import com.btkAkademi.rentACar.business.dtos.AdditionalRentalItemListDto;
import com.btkAkademi.rentACar.business.dtos.AdditionalServiceListDto;
import com.btkAkademi.rentACar.business.requests.additionalRentalItemRequests.CreateAdditionalRentalItemRequest;
import com.btkAkademi.rentACar.business.requests.additionalRentalItemRequests.UpdateAdditionalRentalItemRequest;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.AdditionalRentalItemDao;
import com.btkAkademi.rentACar.entities.concretes.AdditionalRentalItem;
import com.btkAkademi.rentACar.entities.concretes.AdditionalService;

@Service
public class AdditionalRentalItemManager implements AdditionalRentalItemService{
	
	private AdditionalRentalItemDao additionalRentalItemDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public AdditionalRentalItemManager(AdditionalRentalItemDao additionalRentalItemDao,
			ModelMapperService modelMapperService) {
		super();
		this.additionalRentalItemDao = additionalRentalItemDao;
		this.modelMapperService = modelMapperService;
	}
	
	@Override
	public Result add(CreateAdditionalRentalItemRequest createAdditionaRentalItemRequest) {

		for (AdditionalServiceListDto additionalService : createAdditionaRentalItemRequest
				.getAdditionalServiceListDto()) {
			AdditionalService response = modelMapperService.forRequest().map(additionalService,
					AdditionalService.class);

			AdditionalRentalItem additionalRentalItem = modelMapperService.forRequest()
					.map(createAdditionaRentalItemRequest, AdditionalRentalItem.class);
			additionalRentalItem.setAdditionalService(response);
			this.additionalRentalItemDao.save(additionalRentalItem);

		}



		return new SuccessResult("additional service added");
	}

	@Override
	public DataResult<List<AdditionalRentalItemListDto>> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result delete(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result update(UpdateAdditionalRentalItemRequest updateAdditionaItemRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<List<AdditionalRentalItemListDto>> findByRentalId(int rentalId) {
		List<AdditionalRentalItem> request = this.additionalRentalItemDao.findByRentalId(rentalId);
		List<AdditionalRentalItemListDto> response = request.stream().map(additionalRentalItem -> modelMapperService
				.forDto().map(additionalRentalItem, AdditionalRentalItemListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<AdditionalRentalItemListDto>>(response);
	}
}
