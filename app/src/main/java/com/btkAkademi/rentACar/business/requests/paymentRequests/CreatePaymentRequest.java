package com.btkAkademi.rentACar.business.requests.paymentRequests;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {

	private int rentalId;
	private int cardInfoId;
	private int promotionId;
	private LocalDate paymentDate;
	private boolean cardSaveRequested;
}
