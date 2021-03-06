package com.btkAkademi.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.CorporateCustomer;
import com.btkAkademi.rentACar.entities.concretes.IndividualCustomer;

public interface CorporateCustomerDao extends JpaRepository<CorporateCustomer, Integer> {
	CorporateCustomer findByCompanyName(String companydName);
	CorporateCustomer findById(int id);
}
