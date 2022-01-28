package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.ColorListDto;
import com.btkAkademi.rentACar.business.requests.colorRequests.CreateColorRequest;
import com.btkAkademi.rentACar.business.requests.colorRequests.UpdateColorRequests;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

public interface ColorService {
	
	DataResult<List<ColorListDto>> getAll();
	
	DataResult<ColorListDto> findById(int id);

	Result add(CreateColorRequest creColorRequest);

	Result update(UpdateColorRequests updateColorRequests);

	Result delete(int id);
}
