package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.PromotionListDto;
import com.btkAkademi.rentACar.business.dtos.SegmentListDto;
import com.btkAkademi.rentACar.business.requests.segmentRequests.CreateSegmentRequest;
import com.btkAkademi.rentACar.business.requests.segmentRequests.UpdateSegmentRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

public interface SegmentService {
	DataResult<List<SegmentListDto>> findAll();
	
	Result add(CreateSegmentRequest createSegmentRequest);
	
	Result update( UpdateSegmentRequest updateSegmentRequest); 
	
	Result delete(int id);
	 ;
}
