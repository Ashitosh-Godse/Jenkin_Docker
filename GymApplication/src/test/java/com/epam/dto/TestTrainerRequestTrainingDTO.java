package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.request.TrainerRequestTrainingDTO;

@ExtendWith(MockitoExtension.class)
public class TestTrainerRequestTrainingDTO {
	 @Test
	   void testTrainerRequestTrainingDTOBuilder() {
	        String userName = "johndoe";
	        Date periodFrom = new Date();
	        Date periodTo = new Date();
	        String traineeName = "trainee123";

	        TrainerRequestTrainingDTO dto = TrainerRequestTrainingDTO.builder()
	            .userName(userName)
	            .periodFrom(periodFrom)
	            .periodTo(periodTo)
	            .traineeName(traineeName)
	            .build();

	        assertEquals(userName, dto.getUserName());
	        assertEquals(periodFrom, dto.getPeriodFrom());
	        assertEquals(periodTo, dto.getPeriodTo());
	        assertEquals(traineeName, dto.getTraineeName());
	    }
	   
}
