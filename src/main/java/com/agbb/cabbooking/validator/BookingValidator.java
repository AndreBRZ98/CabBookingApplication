package com.agbb.cabbooking.validator;

import com.agbb.cabbooking.dto.BookingDTO;
import com.agbb.cabbooking.exception.CabBookingException;

public class BookingValidator {
	
	public void validate(BookingDTO bookingDTO) throws CabBookingException{
		if(!validatePhoneNo(bookingDTO.getPhoneNo())) {
			throw new CabBookingException("Validator.INVALID_PHONENO");
		}
	}
	
	public boolean validatePhoneNo(Long phoneNo) {
		String phoneNoStr = Long.toString(phoneNo);
		return phoneNoStr.matches("[6-9]{1}[0-9]{9}");
	}

}
