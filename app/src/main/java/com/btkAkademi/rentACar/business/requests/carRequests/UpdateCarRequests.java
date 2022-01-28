package com.btkAkademi.rentACar.business.requests.carRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequests {
	
	private int id;
	
	private double dailyPrice;
	
	private int modelYear;
	
	private String description;
	
	private int findexScore;
	
	private int minAge;
	
	private int segmentId;
	
	private int kilometer;
	
	private int colorId;
	
	private int brandId;
}
