package com.agbb.cabbooking.service;

import java.util.List;

import com.agbb.cabbooking.dto.BookingDTO;
import com.agbb.cabbooking.exception.CabBookingException;

public interface CabBookingService {

	public List<BookingDTO> getDetailsByBookingType(String bookingType) throws CabBookingException;
	public Integer bookCab(BookingDTO bookingDTO) throws CabBookingException;
	
}
