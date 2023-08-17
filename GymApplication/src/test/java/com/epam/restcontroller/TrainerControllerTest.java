package com.epam.restcontroller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.epam.dto.request.TrainerProfileDTO;
import com.epam.dto.request.TrainerRequestDTO;
import com.epam.dto.request.TrainerRequestTrainingDTO;
import com.epam.dto.response.CredentialsDTO;
import com.epam.dto.response.TrainerProfileResponseDTO;
import com.epam.dto.response.TrainerResponseDTO;
import com.epam.dto.response.TrainingTrainerResponseDTO;
import com.epam.service.TrainerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TrainerController.class)
public class TrainerControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	TrainerServiceImpl trainerServiceImpl;
	
	
	@Test
	void testGetTrainerByUserName() throws Exception {
		TrainerProfileResponseDTO trainerProfileResponseDTO=new TrainerProfileResponseDTO();
		Mockito.when(trainerServiceImpl.getTrainerProfile(any())).thenReturn(trainerProfileResponseDTO);
		mockMvc.perform(get("/gym/trainers/ashu")).andExpect(status().isOk());
				
	}
	
	@Test
	void testGetTraineeTrainingsList() throws Exception{
		List<TrainingTrainerResponseDTO>list=new ArrayList<>();
		Mockito.when(trainerServiceImpl.getTrainerTrainingDetails(any())).thenReturn(list);
		mockMvc.perform(get("/gym/trainers/trainer/ashu")).andExpect(status().isOk());
	}
	
	@Test
	void testUpdateTraineeProfile() throws Exception {
		TrainerRequestDTO trainerRequestDTO = TrainerRequestDTO.builder().userName("ashu").specialization("Fitness").userName("ashu")
                                              .isActive(true).firstName("ashu").lastName("godse").build();
		TrainerResponseDTO trainerResponseDTO=TrainerResponseDTO.builder()
				                              .userName("ashu")
											  .firstName("ashu")
											  .lastName("godse")
											  .specialization("Fitness")
											  .listOfTrainees(null)
											  .isActive(true)
											  .build();

		Mockito.when(trainerServiceImpl.updateProfile(trainerRequestDTO)).thenReturn(trainerResponseDTO);
		
		mockMvc.perform(put("/gym/trainers").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(trainerResponseDTO))).andExpect(status().isOk());
	}
	

	@Test
	void testRegisterTrainee() throws Exception {
	    CredentialsDTO credentialsDTO = CredentialsDTO.builder()
	            .username("ashitoshgodse007")
	            .password("jkafkjgf")
	            .build();

	    TrainerProfileDTO trainerProfileDTO = TrainerProfileDTO.builder()
	            .firstName("ashu")
	            .lastName("godse")
	            .email("ashitoshgodse007@gmail.com")
	            .trainingtypeName("Fitness")
	            .build();

	    Mockito.when(trainerServiceImpl.registerTrainer(any(TrainerProfileDTO.class))).thenReturn(credentialsDTO);

	    mockMvc.perform(post("/gym/trainers")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(trainerProfileDTO))) // Use trainerProfileDTO instead of credentialsDTO
	            .andExpect(status().isOk());
	}
	
	@Test
	void testGetTrainingList() throws Exception{
		List<TrainingTrainerResponseDTO>list=new ArrayList<>();
		TrainerRequestTrainingDTO trainerRequestTrainingDTO = TrainerRequestTrainingDTO.builder()
		                                                    .userName("john_doe")
		                                                    .periodFrom(new Date()) 
		                                                    .periodTo(new Date())   
		                                                    .traineeName("Alice")
		                                                    .build();

		
		Mockito.when(trainerServiceImpl.getTrainingList(trainerRequestTrainingDTO)).thenReturn(list);
		
		mockMvc.perform(get("/gym/trainers")
				.contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(trainerRequestTrainingDTO)))
		       .andExpect(status().isOk());
	}

}
