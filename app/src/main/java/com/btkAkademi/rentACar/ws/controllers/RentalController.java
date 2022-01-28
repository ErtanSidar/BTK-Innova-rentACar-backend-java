package com.btkAkademi.rentACar.ws.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.btkAkademi.rentACar.business.abstracts.RentalService;
import com.btkAkademi.rentACar.business.dtos.RentalListDto;
import com.btkAkademi.rentACar.business.requests.rentalRequests.CreateRentalRequest;
import com.btkAkademi.rentACar.business.requests.rentalRequests.UpdateRentalRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rentals")
@CrossOrigin
public class RentalController {

	private RentalService rentalService;

	@Autowired
	public RentalController(RentalService rentalService) {
		super();
		this.rentalService = rentalService;
	}

	@GetMapping("getall")
	public DataResult<List<RentalListDto>> findAll(@RequestParam int pageNo,
			@RequestParam(defaultValue = "10") int pageSize) {
		return rentalService.findAll(pageNo, pageSize);
	}
	
	@GetMapping("getrentals")
	public DataResult<List<RentalListDto>> getRentals() {
		return rentalService.findByReturnDateIsNull();
	}

	@PostMapping("addIndividual")
	public Result addIndividual(@RequestBody @Valid CreateRentalRequest createRentalRequest) {
		return this.rentalService.addIndividual(createRentalRequest);
	}
	
	@PostMapping("addCorporate")
	public Result addCorporate(@RequestBody @Valid CreateRentalRequest createRentalRequest) {
		return this.rentalService.addCorporate(createRentalRequest);
	}

	@PutMapping("update")
	public Result update(@RequestBody @Valid UpdateRentalRequest updateRentalRequest) {
		return this.rentalService.update(updateRentalRequest);
	}

	@DeleteMapping("delete/{id}")
	public Result delete(@PathVariable int id) {
		return this.rentalService.delete(id);
	}
}
