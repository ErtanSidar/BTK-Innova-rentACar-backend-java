package com.btkAkademi.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.btkAkademi.rentACar.entities.concretes.Car;

public interface CarDao extends JpaRepository<Car,Integer> {
	Car findById(int id);
	Car findByRental_Id(int id);
	List<Car> findAllBySegmentId(int segmentId);
	
	@Query(value = "select cars.id as rental_id,\r\n"
			+ "	rentals.return_date\r\n"
			+ "from cars\r\n"
			+ "left join car_maintances on cars.id = car_maintances.car_id and car_maintances.returned_date is null\r\n"
			+ "left join rentals on cars.id = rentals.car_id and (rentals.return_date is null or rentals.return_date>NOW())\r\n"
			+ "where car_maintances.id is null and rentals.id is null and cars.segment_id  =?1	",nativeQuery = true)
	List<Integer> findAvailableCarBySegment(Integer segmentId);
	
}
