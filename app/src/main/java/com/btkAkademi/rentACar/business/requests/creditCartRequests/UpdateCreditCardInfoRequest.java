package com.btkAkademi.rentACar.business.requests.creditCartRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreditCardInfoRequest {

	private int id;

	private String creditCard;

	private String validDate;

	private String CVC;

	private int customerId;
}
