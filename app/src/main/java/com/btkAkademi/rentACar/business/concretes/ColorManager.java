package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.ColorService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.ColorListDto;
import com.btkAkademi.rentACar.business.requests.colorRequests.CreateColorRequest;
import com.btkAkademi.rentACar.business.requests.colorRequests.UpdateColorRequests;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorDataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.ColorDao;
import com.btkAkademi.rentACar.entities.concretes.Color;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
		super();
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ColorListDto>> getAll() {
		List<Color> colorList = this.colorDao.findAll();
		List<ColorListDto> response = colorList.stream()
				.map(color -> modelMapperService.forDto().map(color, ColorListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ColorListDto>>(response);
	}
	
	@Override
	public DataResult<ColorListDto> findById(int id) {
		if(colorDao.existsById(id)) {
			Color color = this.colorDao.findById(id);
			ColorListDto response = this.modelMapperService.forDto().map(color, ColorListDto.class);
					return new SuccessDataResult<>(response);
		} else
			return new ErrorDataResult<>("Id bulunamadı");
	}
	
	@Override
	public Result add(CreateColorRequest createColorRequest) {

		Result result = BusinessRules.run(checkIfColorNameExists(createColorRequest.getName()));

		if (result != null) {
			return result;
		}

		Color color = modelMapperService.forRequest().map(createColorRequest, Color.class);
		this.colorDao.save(color);
		return new SuccessResult(Messages.colorAdded);
	}

	@Override
	public Result update(UpdateColorRequests updateColorRequests) {
		
		Result result = BusinessRules.run(checkIfColorIdExists(updateColorRequests.getId()));

		if (result != null) {
			return result;
		}
		
		Color color = modelMapperService.forRequest().map(updateColorRequests, Color.class);
		this.colorDao.save(color);
		return new SuccessResult(Messages.updateColor);
	}

	@Override
	public Result delete(int id) {
		
		Result result = BusinessRules.run(checkIfColorIdExists(id));

		if (result != null) {
			return result;
		}
		
		if (colorDao.existsById(id)) {
			colorDao.deleteById(id);
			return new SuccessResult(Messages.colorDeleted);
		} else
			return new ErrorResult();

	}

	private Result checkIfColorNameExists(String colorName) {
		Color color = this.colorDao.findByName(colorName);
		if (color != null) {
			return new ErrorResult(Messages.colorNameExists);
		}
		return new SuccessResult();

	}
	
	private Result checkIfColorIdExists(int id) {
		Color color = this.colorDao.findById(id);
		if (color == null) {
			return new ErrorResult(Messages.notFoundColor);
		}
		return new SuccessResult();
	}

	

}
