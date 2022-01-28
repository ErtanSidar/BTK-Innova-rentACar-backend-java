package com.btkAkademi.rentACar.ws.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btkAkademi.rentACar.business.abstracts.SegmentService;
import com.btkAkademi.rentACar.business.dtos.PromotionListDto;
import com.btkAkademi.rentACar.business.dtos.SegmentListDto;
import com.btkAkademi.rentACar.business.requests.segmentRequests.CreateSegmentRequest;
import com.btkAkademi.rentACar.business.requests.segmentRequests.UpdateSegmentRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/segments")
@CrossOrigin
public class SegmentController {
	
	private SegmentService segmentService;
	
	@Autowired
	public SegmentController(SegmentService segmentService) {
		super();
		this.segmentService = segmentService;
	}
	
	@GetMapping("getall")
	public DataResult<List<SegmentListDto>> findAll() {
		return segmentService.findAll();
	}
	
	@PostMapping("add")
	public Result add(@RequestBody @Valid CreateSegmentRequest createSegmentRequest) {
		System.out.println(createSegmentRequest.getSegmentName());
		return this.segmentService.add(createSegmentRequest);
	}
	@PutMapping("update")
	public Result update(@RequestBody @Valid UpdateSegmentRequest createSegmentRequest) {
		return this.segmentService.update(createSegmentRequest);
	}
	@DeleteMapping("delete/{id}")
	public Result delete(int id) {
		return this.segmentService.delete(id);
	}
	
}
