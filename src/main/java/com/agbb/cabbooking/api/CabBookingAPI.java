package com.agbb.cabbooking.api;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agbb.cabbooking.dto.BookingDTO;
import com.agbb.cabbooking.exception.CabBookingException;
import com.agbb.cabbooking.service.CabBookingService;

@RestController
@RequestMapping(value = "/api")
public class CabBookingAPI {
	@Autowired
	private CabBookingService cabBookingService;
	@Autowired
	private Environment environment;
	
	@GetMapping(value = "cab/{bookingType}")
	public ResponseEntity<List<BookingDTO>> getDetailsByBookingType(@PathVariable String bookingType)
	throws CabBookingException{
		List<BookingDTO> bookingDTOs = cabBookingService.getDetailsByBookingType(bookingType);
		return new ResponseEntity<>(bookingDTOs, HttpStatus.OK);
	}
	
	@PostMapping(value = "/cab")
	public ResponseEntity<String> bookCab(@RequestBody BookingDTO bookingDTO) throws CabBookingException{
		
		Integer bookingId = cabBookingService.bookCab(bookingDTO);
		String successMessage = environment.getProperty("API.BOOKING_SUCCESS")+bookingId;
		
		return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		
	}
}
