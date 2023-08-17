package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.response.TrainingTrainerResponseDTO;

@ExtendWith(MockitoExtension.class)
class TrainingTrainerResponseDTOTest {
	@Test
    void testBuilder() {
        TrainingTrainerResponseDTO response = TrainingTrainerResponseDTO.builder()
            .trainingName("Workout")
            .date(new Date())
            .type("Fitness")
            .duration(60)
            .traineeName("Trainee One")
            .build();

        assertEquals("Workout", response.getTrainingName());
        assertNotNull(response.getDate());
        assertEquals("Fitness", response.getType());
        assertEquals(60, response.getDuration());
        assertEquals("Trainee One", response.getTraineeName());
    }

    @Test
    void testNoArgsConstructor() {
        TrainingTrainerResponseDTO response = new TrainingTrainerResponseDTO();
        assertNotNull(response);
    }

    @Test
    void testAllArgsConstructor() {
        TrainingTrainerResponseDTO response = new TrainingTrainerResponseDTO("Workout", new Date(), "Fitness", 60, "Trainee One");

        assertEquals("Workout", response.getTrainingName());
        assertNotNull(response.getDate());
        assertEquals("Fitness", response.getType());
        assertEquals(60, response.getDuration());
        assertEquals("Trainee One", response.getTraineeName());
    }

}
