package com.btkAkademi.rentACar.ws.controllers;

import java.util.List;

import javax.validation.Valid;

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

import com.btkAkademi.rentACar.business.abstracts.IndividualCustomerService;
import com.btkAkademi.rentACar.business.dtos.IndividualCustomerListDto;
import com.btkAkademi.rentACar.business.requests.individualRequest.CreateIndividualCustomerRequest;
import com.btkAkademi.rentACar.business.requests.individualRequest.UpdateIndividualCustomerRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/individuals")
@CrossOrigin
public class IndividualCustomerController {

	private IndividualCustomerService individualService;

	public IndividualCustomerController(IndividualCustomerService individualService) {
		super();
		this.individualService = individualService;
	}

	@PostMapping("add")
	public Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualRequest) {
		return this.individualService.add(createIndividualRequest);
	}

	@GetMapping("getall")
	public DataResult<List<IndividualCustomerListDto>> findAll(@RequestParam int pageNo,
			@RequestParam(defaultValue = "10") int pageSize) {

		return this.individualService.findAll(pageNo, pageSize);
	}

	@PutMapping("update")
	public Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {

		return this.individualService.update(updateIndividualCustomerRequest);
	}

	@DeleteMapping("delete/{id}")
	public Result delete(@PathVariable int id) {

		return this.individualService.delete(id);
	}
}
