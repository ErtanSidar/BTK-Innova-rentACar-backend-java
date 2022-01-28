package com.btkAkademi.rentACar.business.requests.additionalServiceRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalServiceRequest {
	
	private int id;
	
	private String name;
	
	private int rentalId;
	
	private double price;
}
