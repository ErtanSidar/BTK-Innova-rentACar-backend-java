package com.btkAkademi.rentACar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionListDto {
	
	private int id;
	
	private String promotionCode;

	private int discountAmount;

	private LocalDate startedDate;

	private LocalDate endedDate;
}
