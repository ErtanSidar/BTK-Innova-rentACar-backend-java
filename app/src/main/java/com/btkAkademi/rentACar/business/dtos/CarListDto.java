package com.btkAkademi.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarListDto {
	private int id;
	private double dailyPrice;
	private int modelYear;
	private String description;
	private int findexScore;
	private int kilometer;
	private int minAge;
	private int segmentId;
	private String colorName;
	private String brandName;
}
