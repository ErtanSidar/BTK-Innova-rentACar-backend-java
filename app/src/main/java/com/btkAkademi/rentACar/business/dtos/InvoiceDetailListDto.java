package com.btkAkademi.rentACar.business.dtos;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailListDto {

	private int id;
	
	private LocalDate createdDate;
	
	private int carId;

}
