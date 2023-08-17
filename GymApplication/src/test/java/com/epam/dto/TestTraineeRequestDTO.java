package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.request.TraineeRequestDTO;

@ExtendWith(MockitoExtension.class)
public class TestTraineeRequestDTO {

	 @Test
	  void testTraineeRequestDTOBuilder() {
	        Date mockDate = mock(Date.class);

	        TraineeRequestDTO requestDTO = TraineeRequestDTO.builder()
	                .username("john_doe")
	                .firstName("John")
	                .lastName("Doe")
	                .adddress("123 Main St")
	                .dateOfBirth(mockDate)
	                .isActive(true)
	                .build();

	        assertEquals("john_doe", requestDTO.getUsername());
	        assertEquals("John", requestDTO.getFirstName());
	        assertEquals("Doe", requestDTO.getLastName());
	        assertEquals("123 Main St", requestDTO.getAdddress());
	        assertEquals(mockDate, requestDTO.getDateOfBirth());
	        assertTrue(requestDTO.isActive());
	    }
}
