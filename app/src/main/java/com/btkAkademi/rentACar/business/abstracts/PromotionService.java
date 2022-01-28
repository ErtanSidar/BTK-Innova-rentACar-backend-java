package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.PaymentListDto;
import com.btkAkademi.rentACar.business.dtos.PromotionListDto;
import com.btkAkademi.rentACar.business.requests.promotionRequests.CreatePromotionRequest;
import com.btkAkademi.rentACar.business.requests.promotionRequests.UpdatePromotionRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.Promotion;

public interface PromotionService {
	DataResult<List<PromotionListDto>> findAll();
	
	Result add(CreatePromotionRequest createPromotionRequest);

	Result update(UpdatePromotionRequest updatePromotionRequest);

	Result delete(int id);
	
	DataResult<Promotion> findById(int id);
}
