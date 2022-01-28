package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.AdditionalRentalItemService;
import com.btkAkademi.rentACar.business.abstracts.AdditionalServiceService;
import com.btkAkademi.rentACar.business.abstracts.CarService;
import com.btkAkademi.rentACar.business.abstracts.CorporateCustomerService;
import com.btkAkademi.rentACar.business.abstracts.IndividualCustomerService;
import com.btkAkademi.rentACar.business.abstracts.InvoiceService;
import com.btkAkademi.rentACar.business.abstracts.PaymentService;
import com.btkAkademi.rentACar.business.abstracts.RentalService;
import com.btkAkademi.rentACar.business.dtos.AdditionalRentalItemListDto;
import com.btkAkademi.rentACar.business.dtos.AdditionalServiceListDto;
import com.btkAkademi.rentACar.business.dtos.InvoiceCorporateCustomerListDto;
import com.btkAkademi.rentACar.business.dtos.InvoiceDetailListDto;
import com.btkAkademi.rentACar.business.dtos.InvoiceIndividualCustomerListDto;
import com.btkAkademi.rentACar.business.dtos.PaymentListDto;
import com.btkAkademi.rentACar.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.InvoiceDao;
import com.btkAkademi.rentACar.entities.concretes.Car;
import com.btkAkademi.rentACar.entities.concretes.CorporateCustomer;
import com.btkAkademi.rentACar.entities.concretes.IndividualCustomer;
import com.btkAkademi.rentACar.entities.concretes.Invoice;
import com.btkAkademi.rentACar.entities.concretes.Payment;
import com.btkAkademi.rentACar.entities.concretes.Promotion;
import com.btkAkademi.rentACar.entities.concretes.Rental;

@Service
public class InvoiceManager implements InvoiceService{
	
	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;
	private IndividualCustomerService individualCustomerService;
	private CorporateCustomerService corporateCustomerService;
	private CarService carService;
	private RentalService rentalService;
	private PaymentService paymentService;
	private AdditionalServiceService additionalServiceService;
	private AdditionalRentalItemService additonalRentalItemService;
	
	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService,
			IndividualCustomerService individualCustomerService, CorporateCustomerService corporateCustomerService,
			CarService carService, RentalService rentalService, PaymentService paymentService,
			AdditionalServiceService additionalServiceService,
			AdditionalRentalItemService additonalRentalItemService) {
		super();
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService = corporateCustomerService;
		this.carService = carService;
		this.rentalService = rentalService;
		this.paymentService = paymentService;
		this.additionalServiceService = additionalServiceService;
		this.additonalRentalItemService=additonalRentalItemService;
	}

	@Override
	public DataResult<List<InvoiceDetailListDto>> findAll() {
		List<Invoice> invoiceList = this.invoiceDao.findAll();
		List<InvoiceDetailListDto> response = invoiceList.stream()
				.map(Invoice -> modelMapperService.forDto().map(Invoice, InvoiceDetailListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<InvoiceDetailListDto>>(response);
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		Invoice invoice = modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		this.invoiceDao.save(invoice);
		return new SuccessResult("fatura eklendi");
	}

	@Override
	public DataResult<InvoiceIndividualCustomerListDto> createInvoiceForIndividualCustomer(int rentalId) {
		DataResult<Car> car = this.carService.findByRentals_Id(rentalId);
		DataResult<Rental> rental = this.rentalService.findById(rentalId);
		DataResult<IndividualCustomer> individualCustomer = this.individualCustomerService
				.findById(rental.getData().getCustomer().getId());
//		List<AdditionalServiceListDto> additionalServices = this.additionalServiceService.findByRentalId(rentalId).getData();
		Invoice invoice = this.invoiceDao.findByRentalId(rentalId);
		InvoiceIndividualCustomerListDto invoiceIndividualCustomerListDto = InvoiceIndividualCustomerListDto.builder()
				.ıdentityNumber(individualCustomer.getData().getIdentityNumber())
				.firstName(individualCustomer.getData().getFirstName())
				.lastName(individualCustomer.getData().getLastName()).email(individualCustomer.getData().getEmail())
				.rentDate(rental.getData().getRentDate()).returnedDate(rental.getData().getReturnDate())
				.totalPrice(priceCalculation(rentalId)).creationDate(invoice.getCreatedDate())
				.additionalTotalPrice(additionalServiceCalculation(rentalId))
				.dailyPrice(car.getData().getDailyPrice())
				.build();

		return new SuccessDataResult<InvoiceIndividualCustomerListDto>(invoiceIndividualCustomerListDto);
	}
	
	@Override
	public DataResult<InvoiceCorporateCustomerListDto> createInvoiceForCorporateCustomer(int rentalId) {
		DataResult<Car> car = this.carService.findByRentals_Id(rentalId);
		DataResult<Rental> rental = this.rentalService.findById(rentalId);
		DataResult<CorporateCustomer> corporateCustomer = this.corporateCustomerService
				.findById(rental.getData().getCustomer().getId());
		List<AdditionalServiceListDto> additionalServices = this.additionalServiceService.findByRentalId(rentalId)
				.getData();
		Invoice invoice = this.invoiceDao.findByRentalId(rentalId);
		InvoiceCorporateCustomerListDto invoiceCorporateCustomerListDto = InvoiceCorporateCustomerListDto.builder()
				.companyName(corporateCustomer.getData().getCompanyName())
				.email(corporateCustomer.getData().getEmail())
				.additionalTotalPrice(additionalServiceCalculation(rentalId))
				.totalPrice(priceCalculation(rentalId))
				.creationDate(invoice.getCreatedDate())
				.dailyPrice(car.getData().getDailyPrice())
				.taxNumber(corporateCustomer.getData().getTaxNumber())
				.rentDate(rental.getData().getRentDate())
				.returnedDate(rental.getData().getReturnDate())
				.additonalServices(additionalServices)
				.build(); 
		
		return new SuccessDataResult<InvoiceCorporateCustomerListDto>(invoiceCorporateCustomerListDto);
	}
	
	private int priceCalculation(int rentalId) {
		DataResult<Car> car = this.carService.findByRentals_Id(rentalId);
		DataResult<Rental> rental = this.rentalService.findById(rentalId);
		int total = additionalServiceCalculation(rentalId);
		int rentDay = rental.getData().getReturnDate().getDayOfMonth() - rental.getData().getRentDate().getDayOfMonth();
		if (rentDay == 0) {
			rentDay = 1;
		}

		return (int) (total + rentDay * car.getData().getDailyPrice());

	}
	private int additionalServiceCalculation(int rentalId) {
		List<AdditionalRentalItemListDto> additionalRentalItem = this.additonalRentalItemService
				.findByRentalId(rentalId).getData();

		int total = 0;
		for (AdditionalRentalItemListDto additional : additionalRentalItem) {
			total += additional.getAdditionalServicePrice();

		}
		return total;
		
	}

}
