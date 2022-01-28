package com.btkAkademi.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.Rental;

public interface RentalDao extends JpaRepository<Rental, Integer>{
	Rental findByCarIdAndReturnDateIsNull(int id);
	List<Rental> findByReturnDateIsNull();
	Rental findByCarId(int id);
	Rental findById(int id);
}
