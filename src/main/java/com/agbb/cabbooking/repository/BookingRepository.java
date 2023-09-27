package com.agbb.cabbooking.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.agbb.cabbooking.entity.Booking;

public interface BookingRepository extends CrudRepository<Booking, Integer>{
	public List<Booking> findByBookingType(String bookingType);
}
