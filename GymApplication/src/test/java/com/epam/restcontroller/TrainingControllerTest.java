package com.epam.restcontroller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.dto.request.TrainingRequestDTO;
import com.epam.dto.response.TrainerListResponseDTO;
import com.epam.service.TrainingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TrainingController.class)
public class TrainingControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	TrainingServiceImpl trainingServiceImpl;
	
	
	@Test
	void testAddTraining() throws  Exception {
		TrainingRequestDTO trainingRequestDTO = TrainingRequestDTO.builder()
		        .traineeUsername("alice")
		        .trainerUsername("bob")
		        .trainingName("Fitness Training")
		        .date(new Date()) 
		        .trainingType("Strength")
		        .duration(60) 
		        .build();
		
		Mockito.doNothing().when(trainingServiceImpl).addTraining(trainingRequestDTO);
		
		mockMvc.perform(post("/gym/training")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(trainingRequestDTO)))
		                .andExpect(status().isOk());

		
	}
	
	@Test
    void testGetTrainerByUserName() throws Exception {
        String traineeUserName = "alice";

        List<TrainerListResponseDTO> responseDTOs = new ArrayList<>();

        when(trainingServiceImpl.unassignedTrainers(anyString())).thenReturn(responseDTOs);

        mockMvc.perform(get("/gym/training/{traineeUserName}", traineeUserName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }	

}
