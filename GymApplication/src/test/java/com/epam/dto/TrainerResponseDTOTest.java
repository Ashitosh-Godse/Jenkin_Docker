package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.response.TrainerResponseDTO;

@ExtendWith(MockitoExtension.class)
class TrainerResponseDTOTest {
	@Test
     void testBuilder() {
        TrainerResponseDTO response = TrainerResponseDTO.builder()
            .userName("trainer1")
            .firstName("Trainer")
            .lastName("One")
            .specialization("Fitness")
            .isActive(true)
            .build();

        assertEquals("trainer1", response.getUserName());
        assertEquals("Trainer", response.getFirstName());
        assertEquals("One", response.getLastName());
        assertEquals("Fitness", response.getSpecialization());
        assertTrue(response.isActive());
    }

    @Test
     void testNoArgsConstructor() {
        TrainerResponseDTO response = new TrainerResponseDTO();
        assertNotNull(response);
    }

    @Test
     void testAllArgsConstructor() {
        TrainerResponseDTO response = new TrainerResponseDTO("trainer1", "Trainer", "One", "Fitness", true, null);

        assertEquals("trainer1", response.getUserName());
        assertEquals("Trainer", response.getFirstName());
        assertEquals("One", response.getLastName());
        assertEquals("Fitness", response.getSpecialization());
        assertTrue(response.isActive());
    }

}
