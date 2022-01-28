package com.btkAkademi.rentACar.business.concretes;

import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.AdditionalRentalItemService;
import com.btkAkademi.rentACar.business.abstracts.AdditionalServiceService;
import com.btkAkademi.rentACar.business.abstracts.CarService;
import com.btkAkademi.rentACar.business.abstracts.PaymentService;
import com.btkAkademi.rentACar.business.abstracts.PosSystemService;
import com.btkAkademi.rentACar.business.abstracts.PromotionService;
import com.btkAkademi.rentACar.business.abstracts.RentalService;
import com.btkAkademi.rentACar.business.dtos.AdditionalRentalItemListDto;
import com.btkAkademi.rentACar.business.dtos.PaymentListDto;
import com.btkAkademi.rentACar.business.requests.paymentRequests.CreatePaymentRequest;
import com.btkAkademi.rentACar.business.requests.paymentRequests.UpdatePaymentRequest;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.PaymentDao;
import com.btkAkademi.rentACar.entities.concretes.AdditionalService;
import com.btkAkademi.rentACar.entities.concretes.Car;
import com.btkAkademi.rentACar.entities.concretes.Payment;
import com.btkAkademi.rentACar.entities.concretes.Promotion;
import com.btkAkademi.rentACar.entities.concretes.Rental;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	private RentalService rentalService;
	private AdditionalServiceService additionalServiceService;
	private CarService carService;
	private PosSystemService posSystemService;
	private PromotionService promotionService;
	private AdditionalRentalItemService additionalRentalItemService;

	@Autowired
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, RentalService rentalService,
			AdditionalServiceService additionalServiceService, CarService carService,
			PosSystemService posSystemService,
			PromotionService promotionService,
			AdditionalRentalItemService additionalRentalItemService
			) {
		super();
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.rentalService = rentalService;
		this.additionalServiceService = additionalServiceService;
		this.carService = carService;
		this.posSystemService = posSystemService;
		this.promotionService=promotionService;
		this.additionalRentalItemService=additionalRentalItemService;
	}

	@Override
	public DataResult<List<PaymentListDto>> findAll() {
		List<Payment> paymentList = this.paymentDao.findAll();
		List<PaymentListDto> response = paymentList.stream()
				.map(CarDamage -> modelMapperService.forDto().map(CarDamage, PaymentListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<PaymentListDto>>(response);
	}

	@Override
	public Result add(CreatePaymentRequest createPaymentRequest) {

		/*
		 * Result result= BusinessRules.run(
		 * checkIfCreditCardValid(createPaymentRequest.getCardInfo()) );
		 * 
		 * if(result != null) { return result; }
		 */

		Payment payment = modelMapperService.forRequest().map(createPaymentRequest, Payment.class);

		if (isThereCodeOrNot(createPaymentRequest.getPromotionId())) {
			payment.setTotalPrice(priceCalculation(createPaymentRequest.getRentalId()));
		} else {
			payment.setTotalPrice(dailyTotalPriceCalculationPromosyon(createPaymentRequest.getRentalId(),
					createPaymentRequest.getPromotionId()));

		}

		this.paymentDao.save(payment);
		return new SuccessResult("para odendi");
	}

	@Override
	public Result update(UpdatePaymentRequest updatePaymentRequest) {
		Payment payment = this.modelMapperService.forRequest().map(updatePaymentRequest, Payment.class);

		this.paymentDao.save(payment);

		return new SuccessResult("payment updated");
	}

	@Override
	public Result delete(int id) {
		if (paymentDao.existsById(id)) {
			paymentDao.deleteById(id);
			return new SuccessResult("payment deleted");
		}
		return new ErrorResult();
	}

	private int priceCalculation(int rentalId) {
		DataResult<Car> car = this.carService.findByRentals_Id(rentalId);
		DataResult<Rental> rental = this.rentalService.findById(rentalId);
		
		List<AdditionalRentalItemListDto> additionalRentalItem = this.additionalRentalItemService
				.findByRentalId(rentalId).getData();

		int total = 0;
		for (AdditionalRentalItemListDto additional : additionalRentalItem) {
			total += additional.getAdditionalServicePrice();

		}
		
		
		
		
		int rentDay = rental.getData().getReturnDate().getDayOfMonth() - rental.getData().getRentDate().getDayOfMonth();
		if (rentDay == 0) {
			rentDay = 1;
		}
		return (int) (total + rentDay * car.getData().getDailyPrice());
		
		
	}
	
//		
	private boolean isThereCodeOrNot(int promosyonId) {
		if (promosyonId == 0) {
			return true;
		}
		return false;

	}
	private int dailyTotalPriceCalculationPromosyon(int rentalId, int promosyonId) {

		DataResult<Promotion> promosyon = this.promotionService.findById(promosyonId);
		int total = priceCalculation(rentalId);
		return total - (total) * (promosyon.getData().getDiscountAmount()) / 100;
	}

	/*
	 * private Result checkIfCreditCardValid() { return
	 * this.posSystemService.checkIfCreditCardIsValid(cardInfo); }
	 */

}
