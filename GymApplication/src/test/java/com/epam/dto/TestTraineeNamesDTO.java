package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.request.TrainerNamesDTO;

@ExtendWith(MockitoExtension.class)
class TestTraineeNamesDTO {
	 @Test
	   void testTrainerNamesDTOGetterAndSetter() {
	        TrainerNamesDTO dto = new TrainerNamesDTO();
	        
	        dto.setUserName("john_doe");
	        dto.setTrainersList(Arrays.asList("trainer1", "trainer2"));
	        
	        assertEquals("john_doe", dto.getUserName());
	        assertEquals(Arrays.asList("trainer1", "trainer2"), dto.getTrainersList());
	    }

	    @Test
	    void testTrainerNamesDTONoArgsConstructor() {
	        TrainerNamesDTO dto = new TrainerNamesDTO();
	        
	        assertEquals(null, dto.getUserName());
	        assertEquals(null, dto.getTrainersList());
	    }

	    @Test
	     void testTrainerNamesDTOAllArgsConstructor() {
	        List<String> trainersList = Arrays.asList("trainer1", "trainer2");
	        TrainerNamesDTO dto = new TrainerNamesDTO("john_doe", trainersList);
	        
	        assertEquals("john_doe", dto.getUserName());
	        assertEquals(trainersList, dto.getTrainersList());
	    }
}
