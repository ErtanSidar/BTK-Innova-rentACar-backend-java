package com.btkAkademi.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CarMaintanceService;
import com.btkAkademi.rentACar.business.abstracts.CarService;
import com.btkAkademi.rentACar.business.abstracts.CorporateCustomerService;
import com.btkAkademi.rentACar.business.abstracts.IndividualCustomerService;
import com.btkAkademi.rentACar.business.abstracts.RentalService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.CarListDto;
import com.btkAkademi.rentACar.business.dtos.RentalListDto;
import com.btkAkademi.rentACar.business.requests.rentalRequests.CreateRentalRequest;
import com.btkAkademi.rentACar.business.requests.rentalRequests.UpdateRentalRequest;
import com.btkAkademi.rentACar.core.utilities.adapters.abstracts.FindexScore;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorDataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.CarDao;
import com.btkAkademi.rentACar.dataAccess.abstracts.RentalDao;
import com.btkAkademi.rentACar.entities.concretes.Car;
import com.btkAkademi.rentACar.entities.concretes.CorporateCustomer;
import com.btkAkademi.rentACar.entities.concretes.Customer;
import com.btkAkademi.rentACar.entities.concretes.IndividualCustomer;
import com.btkAkademi.rentACar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {

	private RentalDao rentalDao;
	private ModelMapperService modelMapperService;
	private IndividualCustomerService individualService;
	private CorporateCustomerService corporateCustomerService;
	private CarMaintanceService carMaintanceService;
	private FindexScore findexScore;
	private CarService carService;
	private CarDao carDao;
	


	@Autowired
	@Lazy
	public RentalManager(RentalDao rentalDao, ModelMapperService modelMapperService,
			IndividualCustomerService individualService, CarMaintanceService carMaintanceService,
			FindexScore findexScore, CarService carService,
			CorporateCustomerService corporateCustomerService,
			CarDao carDao
			) {
		super();
		this.rentalDao = rentalDao;
		this.modelMapperService = modelMapperService;
		this.individualService = individualService;
		this.corporateCustomerService=corporateCustomerService;
		this.carMaintanceService = carMaintanceService;
		this.findexScore = findexScore;
		this.carService = carService;
		this.carDao=carDao;
	}

	@Override
	public DataResult<List<RentalListDto>> findAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Rental> rentalList = this.rentalDao.findAll(pageable).getContent();
		List<RentalListDto> response = rentalList.stream()
				.map(rental -> modelMapperService.forDto().map(rental, RentalListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<RentalListDto>>(response);
	}
	
	@Override
	public DataResult<List<RentalListDto>> findByReturnDateIsNull() {
		List<Rental> rentalList = this.rentalDao.findByReturnDateIsNull();
		List<RentalListDto> response = rentalList.stream()
				.map(rental -> modelMapperService.forDto().map(rental, RentalListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<RentalListDto>>(response);
	}

	@Override
	public Result addIndividual(CreateRentalRequest createRentalRequest) {
		if (!checkIfCarIsMaintance(createRentalRequest.getCarId()).isSuccess() || !checkIfCarIsRented(createRentalRequest.getCarId()).isSuccess()) {
			CarListDto car = findAvailableCarSegment(carService.findById(createRentalRequest.getCarId()).getData().getSegment().getId()).getData();
			if(car !=null) {
				createRentalRequest.setCarId(car.getId());
				
			} else return new ErrorResult("bu segmente araba bulunmaadı");
		}

		DataResult<IndividualCustomer> individualCustomer = this.individualService
				.findById(createRentalRequest.getCustomerId());
		
		DataResult<Car> car=this.carService.findById(createRentalRequest.getCarId());
		
		if (individualCustomer.getData() == null) {
			return new ErrorResult(Messages.notFoundIndividualCustomer);
		}

		Result result = BusinessRules.run(
				checkIfIndividualFindexScoreIsValid(individualCustomer.getData().getIdentityNumber(),car.getData().getFindexScore()),
				checkIfCustomerAgeAndCarAge(car.getData().getMinAge(),individualCustomer.getData().getBirthDate().getYear())
				);

		if (result != null) {
			return result;
		}

		Rental rental = modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		this.rentalDao.save(rental);
		return new SuccessResult(Messages.rentalAdded);
	}

	@Override
	public Result addCorporate(CreateRentalRequest createRentalRequest) {
		if (!checkIfCarIsMaintance(createRentalRequest.getCarId()).isSuccess() || !checkIfCarIsRented(createRentalRequest.getCarId()).isSuccess()) {
			CarListDto car = findAvailableCarSegment(carService.findById(createRentalRequest.getCarId()).getData().getSegment().getId()).getData();
			if(car !=null) {
				createRentalRequest.setCarId(car.getId());
				
			} else return new ErrorResult("bu segmente araba bulunmaadı");
		}

		DataResult<CorporateCustomer> corporateCustomer = this.corporateCustomerService
				.findById(createRentalRequest.getCustomerId());
		
		DataResult<Car> car=this.carService.findById(createRentalRequest.getCarId());
		
		if (corporateCustomer.getData() == null) {
			return new ErrorResult("Kurumsal müşteri bulunamadı");
		}

		Result result = BusinessRules.run(checkIfCarIsMaintance(createRentalRequest.getCarId()),
				checkIfCarIsRented(createRentalRequest.getCarId()),
				checkIfCorporateFindexScoreIsValid(corporateCustomer.getData().getTaxNumber(),car.getData().getFindexScore())
				);

		if (result != null) {
			return result;
		}

		Rental rental = modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		this.rentalDao.save(rental);
		return new SuccessResult(Messages.rentalAdded);
	}
	
	private DataResult<CarListDto> findAvailableCarSegment(int SegmentId) {
		if(carService.findAvailableCarBySegment(SegmentId).isSuccess()) {
			CarListDto car = carService.findByCarId(carService.findAvailableCarBySegment(SegmentId).getData().get(0)).getData();
			return new SuccessDataResult<CarListDto>(car);
		}else return new ErrorDataResult<CarListDto>();
	}
	
	private Result checkIfIndividualFindexScoreIsValid(String identityNumber,int findexScore) {
		if(this.findexScore.getScoreOfIndividualCustomer(identityNumber)>findexScore) {
			return new SuccessResult();
		}
		return new ErrorResult("Findex score yetersiz");
	}
	
	private Result checkIfCorporateFindexScoreIsValid(String taxNumber,int findexScore) {
		if(this.findexScore.getScoreOfCorporateCustomer(taxNumber)>findexScore) {
			return new SuccessResult();
		}
		return new ErrorResult("Findex score yetersiz");
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) {

		Result result = BusinessRules.run(
				checkIfDate(updateRentalRequest.getReturnDate(), updateRentalRequest.getRentDate()),
				checkIfKilometer(updateRentalRequest.getRentedKilometer(), updateRentalRequest.getReturnedKilometer()));

		if (result != null) {
			return result;
		}

		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		this.rentalDao.save(rental);
		return new SuccessResult("Kiralama güncelendi");
	}

	@Override
	public Result delete(int id) {
		if (rentalDao.existsById(id)) {
			rentalDao.deleteById(id);
			return new SuccessResult("Kiralama silindi");
		}
		return new ErrorResult();
	}

	@Override
	public DataResult<RentalListDto> getById(int id) {
		Rental rental = this.rentalDao.findById(id);
		RentalListDto response = modelMapperService.forDto().map(rental, RentalListDto.class);

		return new SuccessDataResult<RentalListDto>(response);
	}

	@Override
	public DataResult<Rental> getByCarId(int carId) {
		Rental rental = this.rentalDao.findByCarId(carId);
		return new SuccessDataResult<Rental>(rental);
	}

	@Override
	public DataResult<List<RentalListDto>> getAll() {

		List<Rental> rentalList = this.rentalDao.findAll();
		List<RentalListDto> response = rentalList.stream()
				.map(rental -> modelMapperService.forDto().map(rental, RentalListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentalListDto>>(response);
	}

	private Result checkIfDate(LocalDate returnDate, LocalDate rentDate) {
		if (returnDate.isBefore(rentDate)) {
			return new ErrorResult(Messages.returnDateError);
		}
		return new SuccessResult();

	}

	private Result checkIfKilometer(int rentedKilometer, int returnedKilometer) {
		if (rentedKilometer >= returnedKilometer) {
			return new ErrorResult(Messages.returnKilometerError);
		}
		return new SuccessResult();

	}

	private Result checkIfCarIsRented(int carId) {
		if (isCarRented(carId)) {
			return new ErrorResult(Messages.carCannotBeRented);
		}
		return new SuccessResult();

	}

	private Result checkIfCarIsMaintance(int carId) {

		if (carMaintanceService.isCarInMaintenance(carId)) {
			return new ErrorResult(Messages.carCannotBeRented);
		}
		return new SuccessResult();

	}

	@Override
	public boolean isCarRented(int id) {
		Rental rental = rentalDao.findByCarIdAndReturnDateIsNull(id);
		if (rental != null) {
			return true;
		}
		return false;
	}

	@Override
	public DataResult<Rental> findById(int id) {
		return new SuccessDataResult<Rental>(this.rentalDao.findById(id));
	}
	
	private Result checkIfCustomerAgeAndCarAge(int carAge,int customerAge) {

		if (customerAge>carAge) {
			return new SuccessResult();
		}
		return new ErrorResult("Müşteri yaşı küçük");
		

	}

	
}
