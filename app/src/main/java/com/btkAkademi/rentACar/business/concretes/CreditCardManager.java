package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CreditCardService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.CreditCardListDto;
import com.btkAkademi.rentACar.business.requests.creditCartRequests.CreateCreditCardRequests;
import com.btkAkademi.rentACar.business.requests.creditCartRequests.UpdateCreditCardInfoRequest;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.CreditCardDao;
import com.btkAkademi.rentACar.entities.concretes.CreditCardInfo;

@Service
public class CreditCardManager implements CreditCardService {

	private CreditCardDao creditCardDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CreditCardManager(CreditCardDao creditCardDao, ModelMapperService modelMapperService) {
		super();
		this.creditCardDao = creditCardDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<CreditCardListDto>> findAll(int pageNo, int pageSize) {
		List<CreditCardInfo> creditCardInfosList = this.creditCardDao.findAll();
		List<CreditCardListDto> response = creditCardInfosList.stream()
				.map(CreditCardInfo -> modelMapperService.forDto().map(CreditCardInfo, CreditCardListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CreditCardListDto>>(response);
	}

	@Override
	public Result add(CreateCreditCardRequests createCreditCardRequests) {
		CreditCardInfo creditCardInfo = modelMapperService.forRequest().map(createCreditCardRequests,
				CreditCardInfo.class);
		this.creditCardDao.save(creditCardInfo);
		return new SuccessResult(Messages.creditCardAdded);
	}

	@Override
	public Result update(UpdateCreditCardInfoRequest updateCreditCardInfoRequest) {
		CreditCardInfo creditCardInfo = this.modelMapperService.forRequest().map(updateCreditCardInfoRequest,
				CreditCardInfo.class);
		this.creditCardDao.save(creditCardInfo);
		return new SuccessResult("kredi kart bilgileri güncelendi");
	}

	@Override
	public Result delete(int id) {
		if (creditCardDao.existsById(id)) {
			creditCardDao.deleteById(id);
			return new SuccessResult("Credit card infos deleted");
		}
		return new ErrorResult();
	}

}
