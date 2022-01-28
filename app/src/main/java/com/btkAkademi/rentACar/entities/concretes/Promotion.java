package com.btkAkademi.rentACar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="promotions")
public class Promotion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="promotion_code")
	private String promotionCode;
	
	@Column(name="discount_amount")
	private int discountAmount;
	
	@Column(name="start_date")
	private LocalDate startedDate;
	
	@Column(name="end_date")
	private LocalDate endedDate;
	
	@OneToMany(mappedBy = "promotion")
	private List<Rental> rentals;
	
}
