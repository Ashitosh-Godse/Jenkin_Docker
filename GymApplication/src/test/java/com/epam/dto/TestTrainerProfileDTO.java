package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.request.TrainerProfileDTO;

@ExtendWith(MockitoExtension.class)
public class TestTrainerProfileDTO {
	   @Test
	   void testTrainerProfileDTOBuilder() {
	        String firstName = "John";
	        String lastName = "Doe";
	        String email = "john.doe@example.com";
	        String trainingType = "Fitness";

	        TrainerProfileDTO dto = TrainerProfileDTO.builder()
	            .firstName(firstName)
	            .lastName(lastName)
	            .email(email)
	            .trainingtypeName(trainingType)
	            .build();
	        
	        assertEquals(firstName, dto.getFirstName());
	        assertEquals(lastName, dto.getLastName());
	        assertEquals(email, dto.getEmail());
	        assertEquals(trainingType, dto.getTrainingtypeName());
	    }
}
