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
import org.springframework.web.bind.annotation.RestController;

import com.btkAkademi.rentACar.business.abstracts.CarMaintanceService;
import com.btkAkademi.rentACar.business.dtos.CarMaintanceListDto;
import com.btkAkademi.rentACar.business.requests.carMaintanceRequests.CreateMaintanceRequest;
import com.btkAkademi.rentACar.business.requests.carMaintanceRequests.UpdateCarMaintanceRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carMaintances")
@CrossOrigin
public class CarMaintanceController {

	private CarMaintanceService carMaintanceService;

	@Autowired
	public CarMaintanceController(CarMaintanceService carMaintanceService) {
		super();
		this.carMaintanceService = carMaintanceService;
	}

	@PostMapping("add")
	public Result add(@RequestBody @Valid CreateMaintanceRequest createMaintanceRequest) {
		return this.carMaintanceService.add(createMaintanceRequest);
	}

	@GetMapping("getall")
	public DataResult<List<CarMaintanceListDto>> getAll() {
		return this.carMaintanceService.getAll();
	}

	@PutMapping("update")
	public Result add(@RequestBody @Valid UpdateCarMaintanceRequest updateCarMaintanceRequest) {
		return this.carMaintanceService.update(updateCarMaintanceRequest);
	}

	@DeleteMapping("delete/{id}")
	public Result add(@PathVariable int id) {
		return this.carMaintanceService.delete(id);
	}

}
