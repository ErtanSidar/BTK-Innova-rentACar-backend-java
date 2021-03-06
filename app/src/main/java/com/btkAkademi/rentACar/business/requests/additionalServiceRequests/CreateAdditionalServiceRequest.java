package com.btkAkademi.rentACar.business.requests.additionalServiceRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdditionalServiceRequest {
	private String name;
	private double additionalServicePrice;
	private int rentalId;
}
