package com.btkAkademi.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.CreditCardInfo;

public interface CreditCardDao extends JpaRepository<CreditCardInfo, Integer>{

}
