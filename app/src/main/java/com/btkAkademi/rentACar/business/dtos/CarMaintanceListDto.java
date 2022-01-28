package com.btkAkademi.rentACar.business.dtos;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintanceListDto {
	
	private int id;
	
	private int carId;
	
	private LocalDate givenDate;
	
	private LocalDate returnedDate;
}
