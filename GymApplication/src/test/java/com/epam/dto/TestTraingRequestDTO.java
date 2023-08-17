package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.request.TrainingRequestDTO;

@ExtendWith(MockitoExtension.class)
public class TestTraingRequestDTO {
	 
	   @Test
	   void testTrainingRequestDTOBuilder() {
	        String traineeUsername = "trainee123";
	        String trainerUsername = "trainer456";
	        String trainingName = "Training A";
	        Date date = new Date();
	        String trainingType = "Type A";
	        int duration = 60;

	        TrainingRequestDTO dto = TrainingRequestDTO.builder()
	                                .traineeUsername(traineeUsername)
	                                .trainerUsername(trainerUsername)
	                                .trainingName(trainingName)
	                                .date(date)
	                                .trainingType(trainingType)
	                                .duration(duration)
	                                .build();

	        assertEquals(traineeUsername, dto.getTraineeUsername());
	        assertEquals(trainerUsername, dto.getTrainerUsername());
	        assertEquals(trainingName, dto.getTrainingName());
	        assertEquals(date, dto.getDate());
	        assertEquals(trainingType, dto.getTrainingType());
	        assertEquals(duration, dto.getDuration());
	    }
}
