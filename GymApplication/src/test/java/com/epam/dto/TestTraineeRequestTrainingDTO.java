package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.request.TraineeRequestTrainingDTO;

@ExtendWith(MockitoExtension.class)
class TestTraineeRequestTrainingDTO {
	 @Test
	   void testTraineeRequestTrainingDTONoArgsConstructor() {
	        TraineeRequestTrainingDTO requestDTO = new TraineeRequestTrainingDTO();
	        
	        assertEquals(null, requestDTO.getUserName());
	        assertEquals(null, requestDTO.getPeriodFrom());
	        assertEquals(null, requestDTO.getPeriodTo());
	        assertEquals(null, requestDTO.getTrainerName());
	        assertEquals(null, requestDTO.getTrainingType());
	    }

	    @Test
	    void testTraineeRequestTrainingDTOSetter() {
	        TraineeRequestTrainingDTO requestDTO = new TraineeRequestTrainingDTO();
	        
	        requestDTO.setUserName("john_doe");
	        requestDTO.setPeriodFrom(mock(Date.class));
	        requestDTO.setPeriodTo(mock(Date.class));
	        requestDTO.setTrainerName("trainer1");
	        requestDTO.setTrainingType("Gym Training");
	        
	        assertEquals("john_doe", requestDTO.getUserName());
	    }

	    @Test
	    void testTraineeRequestTrainingDTOBuilder() {
	        Date mockDate = mock(Date.class);

	        TraineeRequestTrainingDTO requestDTO = TraineeRequestTrainingDTO.builder()
	                .userName("john_doe")
	                .periodFrom(mockDate)
	                .periodTo(mockDate)
	                .trainerName("trainer1")
	                .trainingType("Gym Training")
	                .build();

	        assertEquals("john_doe", requestDTO.getUserName());
	    }

}
