package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CorporateCustomerService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.CorporateCustomerListDto;
import com.btkAkademi.rentACar.business.requests.corporateRequests.CreateCorporateCustomerRequest;
import com.btkAkademi.rentACar.business.requests.corporateRequests.UpdateCorporateCustomerRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.CorporateCustomerDao;
import com.btkAkademi.rentACar.entities.concretes.CorporateCustomer;
import com.btkAkademi.rentACar.entities.concretes.IndividualCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	private ModelMapperService modelMapperService;
	private CorporateCustomerDao corporateCustomerDao;

	@Autowired
	public CorporateCustomerManager(ModelMapperService modelMapperService, CorporateCustomerDao corporateCustomerDao) {
		super();
		this.modelMapperService = modelMapperService;
		this.corporateCustomerDao = corporateCustomerDao;
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateRequest) {

		Result result = BusinessRules.run(checkIfCompanyNameExists(createCorporateRequest.getCompanyName()));

		if (result != null) {
			return result;
		}

		CorporateCustomer corporateCustomer = modelMapperService.forRequest().map(createCorporateRequest,
				CorporateCustomer.class);
		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(Messages.corporateAdded);
	}
	
	@Override
	public DataResult<CorporateCustomer> findById(int id) {
		// TODO Auto-generated method stub
		return new SuccessDataResult<CorporateCustomer>(this.corporateCustomerDao.findById(id));
	}

	private Result checkIfCompanyNameExists(String companyName) {
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.findByCompanyName(companyName);
		if (corporateCustomer != null) {
			return new ErrorResult(Messages.companyNameExists);
		}
		return new SuccessResult();

	}

	@Override
	public DataResult<List<CorporateCustomerListDto>> findAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<CorporateCustomer> corporateCustomers = this.corporateCustomerDao.findAll(pageable).getContent();
		List<CorporateCustomerListDto> response = corporateCustomers.stream().map(
				corporateCustomer -> modelMapperService.forDto().map(corporateCustomer, CorporateCustomerListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CorporateCustomerListDto>>(response);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest,
				CorporateCustomer.class);
		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult("corporate müşteri güncelendi");
	}

	@Override
	public Result delete(int id) {
		if (corporateCustomerDao.existsById(id)) {
			corporateCustomerDao.deleteById(id);
			return new SuccessResult("corporate müşteri silindi");
		}
		return new ErrorResult();
	}

}
