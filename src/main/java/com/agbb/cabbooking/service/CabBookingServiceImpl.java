package com.agbb.cabbooking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agbb.cabbooking.dto.BookingDTO;
import com.agbb.cabbooking.dto.CabDTO;
import com.agbb.cabbooking.entity.Booking;
import com.agbb.cabbooking.entity.Cab;
import com.agbb.cabbooking.exception.CabBookingException;
import com.agbb.cabbooking.repository.BookingRepository;
import com.agbb.cabbooking.repository.CabRepository;
import com.agbb.cabbooking.validator.BookingValidator;

import jakarta.transaction.Transactional;

@Service(value = "cabBookingService")
@Transactional
public class CabBookingServiceImpl implements CabBookingService {
	
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private CabRepository cabRepository;

	@Override
	public List<BookingDTO> getDetailsByBookingType(String bookingType) throws CabBookingException {

		Iterable<Booking> bookingsByTypeIterable = bookingRepository.findByBookingType(bookingType);
		List<BookingDTO> bookingDTOs = new ArrayList<>();
		bookingsByTypeIterable.forEach( book ->{
			BookingDTO bookingDTO = new BookingDTO();
			bookingDTO.setBookingId(book.getBookingId());
			bookingDTO.setBookingType(book.getBookingType());
			bookingDTO.setCustomerName(book.getCustomerName());
			bookingDTO.setPhoneNo(book.getPhoneNo());
			CabDTO cabDTO = new CabDTO();
			cabDTO.setAvailability(book.getCab().getAvailability());
			cabDTO.setCabNo(book.getCab().getCabNo());
			cabDTO.setDriverPhoneNo(book.getCab().getDriverPhoneNo());
			cabDTO.setModelName(book.getCab().getModelName());
			bookingDTO.setCabDTO(cabDTO);
			bookingDTOs.add(bookingDTO);
		});
		if(bookingDTOs.isEmpty()) {
			throw new CabBookingException("Service.NO_DETAILS_FOUND");
		}
		return bookingDTOs;
	}

	@Override
	public Integer bookCab(BookingDTO bookingDTO) throws CabBookingException {
		BookingValidator bookingValidator = new BookingValidator();
		bookingValidator.validate(bookingDTO);
		Optional <Cab> cabOptional = cabRepository.findById(bookingDTO.getCabDTO().getCabNo());
		Cab cab = cabOptional.orElseThrow(
				()-> new CabBookingException("Service.CAB_NOT_FOUND"));
		if(cab.getAvailability().equals("No")) {
			throw new CabBookingException("Service.CAB_NOT_AVAILABLE");
		}
		Booking booking = new Booking();
		booking.setBookingType(bookingDTO.getBookingType());
		booking.setCab(cab);
		booking.setCustomerName(bookingDTO.getCustomerName());
		booking.setPhoneNo(bookingDTO.getPhoneNo());
		cab.setAvailability("No");
		Integer bookingId = bookingRepository.save(booking).getBookingId();
		return bookingId;
	}

}
