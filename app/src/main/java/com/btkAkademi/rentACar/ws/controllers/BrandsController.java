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
import org.springframework.web.bind.annotation.RestController;

import com.btkAkademi.rentACar.business.abstracts.BrandService;
import com.btkAkademi.rentACar.business.dtos.BrandListDto;
import com.btkAkademi.rentACar.business.requests.brandRequests.CreateBrandRequest;
import com.btkAkademi.rentACar.business.requests.brandRequests.UpdateBrandRequests;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/brands")
@CrossOrigin
public class BrandsController {
	private BrandService brandService;

	public BrandsController(BrandService brandService) {
		super();
		this.brandService = brandService;
	}
	
	@GetMapping("getall")
	public DataResult<List<BrandListDto>> getAll(){
		return this.brandService.getAll();
	}
	
	@GetMapping("findById/{id}")
	public DataResult<BrandListDto> findById(@PathVariable int id) {
		return this.brandService.findById(id);
	}
	
	@PostMapping("add")
	public Result add(@RequestBody @Valid CreateBrandRequest createBrandRequest) {
		return this.brandService.add(createBrandRequest);
	}
	
	@PutMapping("update")
	public Result update(@RequestBody @Valid UpdateBrandRequests updateBrandRequests) {
		return this.brandService.update(updateBrandRequests);
	}
	
	@DeleteMapping("delete/{id}")
	public Result delete(@Valid @PathVariable int id) {
		return this.brandService.delete(id);
	}
}
