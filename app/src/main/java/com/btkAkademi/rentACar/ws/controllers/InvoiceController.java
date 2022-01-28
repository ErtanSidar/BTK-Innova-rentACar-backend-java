package com.btkAkademi.rentACar.ws.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btkAkademi.rentACar.business.abstracts.InvoiceService;
import com.btkAkademi.rentACar.business.dtos.InvoiceCorporateCustomerListDto;
import com.btkAkademi.rentACar.business.dtos.InvoiceDetailListDto;
import com.btkAkademi.rentACar.business.dtos.InvoiceIndividualCustomerListDto;
import com.btkAkademi.rentACar.business.dtos.PaymentListDto;
import com.btkAkademi.rentACar.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.btkAkademi.rentACar.business.requests.paymentRequests.CreatePaymentRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin
public class InvoiceController {
	
	private InvoiceService invoiceService;
	
	@Autowired
	public InvoiceController(InvoiceService invoiceService) {
		super();
		this.invoiceService = invoiceService;
	}

	@GetMapping("getall")
	public DataResult<List<InvoiceDetailListDto>> findAll() {
		return invoiceService.findAll();
	}
	
	@PostMapping("add")
	public Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest) {
		return this.invoiceService.add(createInvoiceRequest);
	}
	
	@GetMapping("get-invoice-individual-customer/{rentalId}")
	public DataResult<InvoiceIndividualCustomerListDto> getInvoiceForIndividualCustomer(@PathVariable int rentalId) {
		return this.invoiceService.createInvoiceForIndividualCustomer(rentalId);
	}
	
	@GetMapping("get-invoice-corporate-customer/{rentalId}")
	public DataResult<InvoiceCorporateCustomerListDto> createInvoiceForCorporateCustomer(@PathVariable int rentalId) {
		return this.invoiceService.createInvoiceForCorporateCustomer(rentalId);
	}
}
