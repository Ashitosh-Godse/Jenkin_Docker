package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.request.TraineeProfileDTO;

@ExtendWith(MockitoExtension.class)
public class TestTraineeProfileDTO {
	@Test
	  void testBuilder() {
	        Date mockDate = mock(Date.class);

	        TraineeProfileDTO profileDTO = TraineeProfileDTO.builder()
	                .firstName("John")
	                .lastName("Doe")
	                .dateOfBirth(mockDate)
	                .adddress("123 Main St")
	                .email("john@example.com")
	                .build();

	        assertEquals("John", profileDTO.getFirstName());
	        assertEquals("Doe", profileDTO.getLastName());
	        assertEquals(mockDate, profileDTO.getDateOfBirth());
	        assertEquals("123 Main St", profileDTO.getAdddress());
	        assertEquals("john@example.com", profileDTO.getEmail());
	    }
	  
}
