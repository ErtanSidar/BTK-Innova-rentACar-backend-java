package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.PromotionService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.PaymentListDto;
import com.btkAkademi.rentACar.business.dtos.PromotionListDto;
import com.btkAkademi.rentACar.business.requests.promotionRequests.CreatePromotionRequest;
import com.btkAkademi.rentACar.business.requests.promotionRequests.UpdatePromotionRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.PromotionDao;
import com.btkAkademi.rentACar.entities.concretes.Color;
import com.btkAkademi.rentACar.entities.concretes.Payment;
import com.btkAkademi.rentACar.entities.concretes.Promotion;

@Service
public class PromotionManager implements PromotionService{
	
	private PromotionDao promotionDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public PromotionManager(PromotionDao promotionDao, ModelMapperService modelMapperService) {
		super();
		this.promotionDao = promotionDao;
		this.modelMapperService = modelMapperService;
	}
	
	@Override
	public DataResult<List<PromotionListDto>> findAll() {
		List<Promotion> promotionList = this.promotionDao.findAll();
		List<PromotionListDto> response = promotionList.stream()
				.map(Promotion -> modelMapperService.forDto().map(Promotion, PromotionListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<PromotionListDto>>(response);
	}
	
	@Override
	public Result add(CreatePromotionRequest createPromotionRequest) {
		
		Result result = BusinessRules.run(				
				checkIfPromoCodeExistsByPromotionCode(createPromotionRequest.getPromotionCode())
				)	;		
		if(result!=null) {			
			return result;
		}
		
		Promotion promotion = modelMapperService.forRequest().map(createPromotionRequest, Promotion.class);
		this.promotionDao.save(promotion);
		return new SuccessResult("promoson eklendi");
	}

	@Override
	public Result update(UpdatePromotionRequest updatePromotionRequest) {
		Promotion promotion = this.modelMapperService.forRequest().map(updatePromotionRequest, Promotion.class);		
		this.promotionDao.save(promotion);
		return new SuccessResult("Promosyon kodu güncelendi");
	}

	@Override
	public Result delete(int id) {
		if(promotionDao.existsById(id)) {
			promotionDao.deleteById(id);
			return new SuccessResult("Promosyon kodu silindi");
		}else return new ErrorResult();
	}
	
	private Result checkIfPromoCodeExistsByPromotionCode(String promotionCode){
		if(promotionDao.findByPromotionCode(promotionCode)!=null) {
			return new ErrorResult("Promosyon kodu zaten var");
		}
		else return new SuccessResult();
	}

	@Override
	public DataResult<Promotion> findById(int id) {
		return new SuccessDataResult<Promotion>(this.promotionDao.findById(id));
	}

}
