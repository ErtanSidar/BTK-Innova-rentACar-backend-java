package com.btkAkademi.rentACar.core.utilities.adapters;

import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.PosSystemService;
import com.btkAkademi.rentACar.core.utilities.fakeManagers.FakePosSystemManager;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.entities.concretes.CreditCardInfo;

@Service
public class PosSystemAdapter implements PosSystemService{
	FakePosSystemManager manager = new FakePosSystemManager();
	
	@Override
	public Result checkIfCreditCardIsValid(CreditCardInfo cardInfo) {
		if(manager.makePayment(cardInfo.getCustomer().getEmail(), cardInfo.getCreditCard(), cardInfo.getValidDate(), cardInfo.getCVC() )) {
			return new SuccessResult();
		}
			return new ErrorResult("bilgiler hatalı");
	}
}
