package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.CreditCardListDto;
import com.btkAkademi.rentACar.business.requests.creditCartRequests.CreateCreditCardRequests;
import com.btkAkademi.rentACar.business.requests.creditCartRequests.UpdateCreditCardInfoRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

public interface CreditCardService {
	
	DataResult<List<CreditCardListDto>> findAll(int pageNo, int pageSize);

	Result add(CreateCreditCardRequests createCreditCardRequests);

	Result update(UpdateCreditCardInfoRequest updateCreditCardInfoRequest);

	Result delete(int id);
}
