package com.btkAkademi.rentACar.ws.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btkAkademi.rentACar.business.abstracts.PromotionService;
import com.btkAkademi.rentACar.business.dtos.PaymentListDto;
import com.btkAkademi.rentACar.business.dtos.PromotionListDto;
import com.btkAkademi.rentACar.business.requests.paymentRequests.CreatePaymentRequest;
import com.btkAkademi.rentACar.business.requests.promotionRequests.CreatePromotionRequest;
import com.btkAkademi.rentACar.business.requests.promotionRequests.UpdatePromotionRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/promotions")
@CrossOrigin
public class PromotionController {
	
	private PromotionService promotionService;
	
	@Autowired
	public PromotionController(PromotionService promotionService) {
		super();
		this.promotionService = promotionService;
	}
	
	@GetMapping("getall")
	public DataResult<List<PromotionListDto>> findAll() {
		return promotionService.findAll();
	}

	@PostMapping("add")
	public Result add(@RequestBody @Valid CreatePromotionRequest createPromotionRequest) {
		return this.promotionService.add(createPromotionRequest);
	}
	
	@PutMapping("update")
	public Result update(@RequestBody @Valid UpdatePromotionRequest updatePromoCodeRequest) {

		return this.promotionService.update(updatePromoCodeRequest);
	}
	
	@DeleteMapping("delete/{id}")
	public Result delete(@PathVariable int id) {
		return this.promotionService.delete(id);
	}
}
