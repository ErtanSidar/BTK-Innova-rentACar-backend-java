package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.InvoiceCorporateCustomerListDto;
import com.btkAkademi.rentACar.business.dtos.InvoiceDetailListDto;
import com.btkAkademi.rentACar.business.dtos.InvoiceIndividualCustomerListDto;
import com.btkAkademi.rentACar.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.btkAkademi.rentACar.business.requests.paymentRequests.CreatePaymentRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

public interface InvoiceService {
	DataResult<List<InvoiceDetailListDto>> findAll();
	DataResult<InvoiceIndividualCustomerListDto> createInvoiceForIndividualCustomer(int rentalId);

	DataResult<InvoiceCorporateCustomerListDto> createInvoiceForCorporateCustomer(int rentalId);
	
	Result add(CreateInvoiceRequest createInvoiceRequest);
}
