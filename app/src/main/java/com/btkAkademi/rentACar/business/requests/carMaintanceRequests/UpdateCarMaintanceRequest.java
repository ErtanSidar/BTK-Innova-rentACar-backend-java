package com.btkAkademi.rentACar.business.requests.carMaintanceRequests;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarMaintanceRequest {
	private int id;
	private int carId;
	private LocalDate givenDate;
	private LocalDate returnedDate;
}
