package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.response.TrainerListResponseDTO;

@ExtendWith(MockitoExtension.class)
 class TrainerListResponseDTOTest {
	
	 @Test
	  void testBuilder() {
	        TrainerListResponseDTO response = TrainerListResponseDTO.builder()
	            .userName("trainer1")
	            .firstName("Trainer")
	            .lastName("One")
	            .specialization("Fitness")
	            .build();

	        assertEquals("trainer1", response.getUserName());
	        assertEquals("Trainer", response.getFirstName());
	        assertEquals("One", response.getLastName());
	        assertEquals("Fitness", response.getSpecialization());
	    }

	    @Test
	    void testNoArgsConstructor() {
	        TrainerListResponseDTO response = new TrainerListResponseDTO();
	        assertNotNull(response);
	    }

	    @Test
	    void testAllArgsConstructor() {
	        TrainerListResponseDTO response = new TrainerListResponseDTO("trainer1", "Trainer", "One", "Fitness");

	        assertEquals("trainer1", response.getUserName());
	        assertEquals("Trainer", response.getFirstName());
	        assertEquals("One", response.getLastName());
	        assertEquals("Fitness", response.getSpecialization());
	    }

}
