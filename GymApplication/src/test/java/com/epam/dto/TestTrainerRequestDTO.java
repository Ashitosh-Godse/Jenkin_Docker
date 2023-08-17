package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.request.TrainerRequestDTO;

@ExtendWith(MockitoExtension.class)
public class TestTrainerRequestDTO {
	 @Test
	 void testTrainerRequestDTOBuilder() {
	        String userName = "johndoe";
	        String firstName = "John";
	        String lastName = "Doe";
	        String specialization = "Fitness";
	        boolean isActive = true;

	        TrainerRequestDTO dto = TrainerRequestDTO.builder()
	            .userName(userName)
	            .firstName(firstName)
	            .lastName(lastName)
	            .specialization(specialization)
	            .isActive(isActive)
	            .build();

	        assertEquals(userName, dto.getUserName());
	        assertEquals(firstName, dto.getFirstName());
	        assertEquals(lastName, dto.getLastName());
	        assertEquals(specialization, dto.getSpecialization());
	        assertEquals(isActive, dto.isActive());
	    }
}
