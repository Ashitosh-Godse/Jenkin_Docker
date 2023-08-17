package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.response.TrainerProfileResponseDTO;

@ExtendWith(MockitoExtension.class)

 class TrainerProfileResponseDTOTest {
	@Test
     void testBuilder() {
        TrainerProfileResponseDTO response = TrainerProfileResponseDTO.builder()
            .firstName("Trainer")
            .lastName("One")
            .specialization("Fitness")
            .isActive(true)
            .build();

        assertEquals("Trainer", response.getFirstName());
        assertEquals("One", response.getLastName());
        assertEquals("Fitness", response.getSpecialization());
        assertTrue(response.isActive());
    }

    @Test
     void testNoArgsConstructor() {
        TrainerProfileResponseDTO response = new TrainerProfileResponseDTO();
        assertNotNull(response);
    }

    @Test
     void testAllArgsConstructor() {
        TrainerProfileResponseDTO response = new TrainerProfileResponseDTO("Trainer", "One", "Fitness", true, null);

        assertEquals("Trainer", response.getFirstName());
        assertEquals("One", response.getLastName());
        assertEquals("Fitness", response.getSpecialization());
        assertTrue(response.isActive());
    }
}
