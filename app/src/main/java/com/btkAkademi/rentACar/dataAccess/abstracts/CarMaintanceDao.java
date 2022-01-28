package com.btkAkademi.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.CarMaintance;

public interface CarMaintanceDao extends JpaRepository<CarMaintance, Integer> {
	// @Query("FROM CarMaintance WHERE car.id=:id AND returnedDate IS NULL")
	CarMaintance findByCarIdAndReturnedDateIsNull(int id);
}
