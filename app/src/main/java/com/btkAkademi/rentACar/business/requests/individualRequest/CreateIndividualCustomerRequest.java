package com.btkAkademi.rentACar.business.requests.individualRequest;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomerRequest {
	
	
	private String email;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String identityNumber;
	
	private LocalDate birthDate;

}
