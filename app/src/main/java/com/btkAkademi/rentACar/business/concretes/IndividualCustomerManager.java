package com.btkAkademi.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.IndividualCustomerService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.IndividualCustomerListDto;
import com.btkAkademi.rentACar.business.requests.individualRequest.CreateIndividualCustomerRequest;
import com.btkAkademi.rentACar.business.requests.individualRequest.UpdateIndividualCustomerRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.IndividualCustomerDao;
import com.btkAkademi.rentACar.entities.concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

	private IndividualCustomerDao individualDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualDao, ModelMapperService modelMapperService) {
		super();
		this.individualDao = individualDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualRequest) {

		Result result = BusinessRules.run(checkIfIndividualEmailExists(createIndividualRequest.getEmail()),
				checkIfAge(createIndividualRequest.getBirthDate()));

		if (result != null) {
			return result;
		}

		IndividualCustomer individualCustomer = modelMapperService.forRequest().map(createIndividualRequest,
				IndividualCustomer.class);
		this.individualDao.save(individualCustomer);
		return new SuccessResult(Messages.individualAdded);
	}

	private Result checkIfIndividualEmailExists(String individualEmail) {
		IndividualCustomer individualCustomer = this.individualDao.findByEmail(individualEmail);
		if (individualCustomer != null) {
			return new ErrorResult(Messages.individualEmailExists);
		}
		return new SuccessResult();

	}

	@Override
	public DataResult<IndividualCustomer> findById(int id) {
		// TODO Auto-generated method stub
		return new SuccessDataResult<IndividualCustomer>(this.individualDao.findById(id));
	}

	private Result checkIfAge(LocalDate birthDate) {
		if (Period.between(birthDate, LocalDate.now()).getYears() < 18) {
			return new ErrorResult(Messages.ageError);
		}
		return new SuccessResult();

	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		Result result = BusinessRules.run(checkIfAge(updateIndividualCustomerRequest.getBirthDate()));
		if (result != null) {
			return result;
		}

		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(updateIndividualCustomerRequest, IndividualCustomer.class);
		// avoid null password
		individualCustomer.setPassword(individualDao.findById(individualCustomer.getId()).getPassword());
		this.individualDao.save(individualCustomer);
		return new SuccessResult("bireysel müşteri güncelendi");
	}

	@Override
	public Result delete(int id) {
		if (individualDao.existsById(id)) {
			individualDao.deleteById(id);
			return new SuccessResult("müşteri silindi");
		} else
			return new ErrorResult();
	}

	@Override
	public DataResult<List<IndividualCustomerListDto>> findAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<IndividualCustomer> individualCustomers = this.individualDao.findAll(pageable).getContent();
		List<IndividualCustomerListDto> response = individualCustomers.stream()
				.map(individualCustomer -> modelMapperService.forDto().map(individualCustomer,
						IndividualCustomerListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<IndividualCustomerListDto>>(response);
	}

}
