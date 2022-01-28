package com.btkAkademi.rentACar.business.requests.additionalRentalItemRequests;

import com.btkAkademi.rentACar.business.dtos.AdditionalServiceListDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdditionalRentalItemRequest {

	private AdditionalServiceListDto additionalServiceListDto[];
	private int rentalId;
}
