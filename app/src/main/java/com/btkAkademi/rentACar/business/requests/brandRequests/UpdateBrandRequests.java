package com.btkAkademi.rentACar.business.requests.brandRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBrandRequests {
	private int id;
	private String name;
}
