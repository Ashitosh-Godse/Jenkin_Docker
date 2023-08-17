package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.response.TrainingTraineeResponseDTO;

@ExtendWith(MockitoExtension.class)
 class TrainingTraineeResponseDTOTest {
	@Test
   void testBuilder() {
        TrainingTraineeResponseDTO response = TrainingTraineeResponseDTO.builder()
            .trainingName("Workout")
            .date(new Date())
            .trainingType("Fitness")
            .duration(60)
            .trainerName("Trainer One")
            .build();

        assertEquals("Workout", response.getTrainingName());
        assertNotNull(response.getDate());
        assertEquals("Fitness", response.getTrainingType());
        assertEquals(60, response.getDuration());
        assertEquals("Trainer One", response.getTrainerName());
    }

    @Test
     void testNoArgsConstructor() {
        TrainingTraineeResponseDTO response = new TrainingTraineeResponseDTO();
        assertNotNull(response);
    }

    @Test
    void testAllArgsConstructor() {
        TrainingTraineeResponseDTO response = new TrainingTraineeResponseDTO("Workout", new Date(), "Fitness", 60, "Trainer One");

        assertEquals("Workout", response.getTrainingName());
        assertNotNull(response.getDate());
        assertEquals("Fitness", response.getTrainingType());
        assertEquals(60, response.getDuration());
        assertEquals("Trainer One", response.getTrainerName());
    }

}
