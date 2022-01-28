package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.SegmentService;
import com.btkAkademi.rentACar.business.dtos.PromotionListDto;
import com.btkAkademi.rentACar.business.dtos.SegmentListDto;
import com.btkAkademi.rentACar.business.requests.segmentRequests.CreateSegmentRequest;
import com.btkAkademi.rentACar.business.requests.segmentRequests.UpdateSegmentRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.SegmentDao;
import com.btkAkademi.rentACar.entities.concretes.Promotion;
import com.btkAkademi.rentACar.entities.concretes.Segment;

@Service
public class SegmentManager implements SegmentService{
	
	private ModelMapperService modelMapperService;
	private SegmentDao segmentDao;
	
	@Autowired
	public SegmentManager(ModelMapperService modelMapperService, SegmentDao segmentDao) {
		super();
		this.modelMapperService = modelMapperService;
		this.segmentDao = segmentDao;
	}
	
	@Override
	public DataResult<List<SegmentListDto>> findAll() {
		List<Segment> segmentList = this.segmentDao.findAll();
		List<SegmentListDto> response = segmentList.stream()
				.map(Segment -> modelMapperService.forDto().map(Segment, SegmentListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<SegmentListDto>>(response);
	}
	
	@Override
	public Result add(CreateSegmentRequest createSegmentRequest) {
		
		Result result = BusinessRules.run(checkIfSegmentNameExists(createSegmentRequest.getSegmentName()));		
		if(result!=null) {			
			return result;
		}
		
		Segment segment = this.modelMapperService.forRequest().map(createSegmentRequest, Segment.class);
		this.segmentDao.save(segment);
		return new SuccessResult("Segment eklendi");
	}

	@Override
	public Result update(UpdateSegmentRequest updateSegmentRequest) {
		Segment segment = this.modelMapperService.forRequest().map(updateSegmentRequest, Segment.class);		
		this.segmentDao.save(segment);
		return new SuccessResult("Segment güncelendi");
	}

	@Override
	public Result delete(int id) {
		if(segmentDao.existsById(id)) {
			segmentDao.deleteById(id);
			return new SuccessResult("Promosyon kodu silindi");
		}else return new ErrorResult();
	}
	private Result checkIfSegmentNameExists(String segmentName) {
		Segment segment=this.segmentDao.findBySegmentName(segmentName);
		if(segment != null) {
			return new ErrorResult("aynı isimde segment bulunuyor");
		}
		return new SuccessResult();
	}

	


}
