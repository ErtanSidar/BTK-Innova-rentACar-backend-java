package com.btkAkademi.rentACar.business.requests.promotionRequests;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePromotionRequest {

	private String promotionCode;

	private int discountAmount;

	private LocalDate startedDate;

	private LocalDate endedDate;

}
