package com.btkAkademi.rentACar.business.requests.promotionRequests;

import java.time.LocalDate;

import com.btkAkademi.rentACar.business.requests.paymentRequests.UpdatePaymentRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePromotionRequest {
	private int id;
	private String promotionCode ;		
	private double discountAmount;
	private LocalDate startDate;	
	private LocalDate endDate;
}
