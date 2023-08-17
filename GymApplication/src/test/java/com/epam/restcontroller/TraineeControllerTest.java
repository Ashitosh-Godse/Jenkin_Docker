package com.epam.restcontroller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.dto.request.TraineeProfileDTO;
import com.epam.dto.request.TraineeRequestDTO;
import com.epam.dto.response.CredentialsDTO;
import com.epam.dto.response.TraineeProfileResponseDTO;
import com.epam.dto.response.TraineeResponseDTO;
import com.epam.dto.response.TrainerListResponseDTO;
import com.epam.dto.response.TrainingTraineeResponseDTO;
import com.epam.service.TraineeServiceimpl;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(TraineeController.class)

public class TraineeControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	TraineeServiceimpl traineeServiceimpl;
	
	@Test
	void testGetTraineeByUserName() throws Exception {
		TraineeProfileResponseDTO traineeProfileResponseDTO=new TraineeProfileResponseDTO();
		Mockito.when(traineeServiceimpl.getTraineeProfile(any())).thenReturn(traineeProfileResponseDTO);
		mockMvc.perform(get("/gym/trainees/ashu")).andExpect(status().isOk());
				
	}
	
	@Test
	void testRegisterTrainee() throws Exception{
		CredentialsDTO credentialsDTO=CredentialsDTO.builder().username("ashitoshgodse007").password("jkafkjgf").build();
	
		
		TraineeProfileDTO traineeProfileDTO=TraineeProfileDTO.builder()
											.firstName("ashu")
											.lastName("godse")
											.adddress("pune")
											.dateOfBirth(new Date())
											.email("ashitoshgodse007@gmail.com")
											.build();
		
		 Mockito.when(traineeServiceimpl.registerTrainee(any(TraineeProfileDTO.class))).thenReturn(credentialsDTO);
		 mockMvc.perform(post("/gym/trainees")
		.contentType(MediaType.APPLICATION_JSON)
		.content(new ObjectMapper().writeValueAsString(traineeProfileDTO)))
		.andExpect(status().isOk());
		
	}
	
	
	
	@Test
	void testUpdateTraineeProfile() throws Exception {
		
		TraineeRequestDTO traineeRequestDTO = TraineeRequestDTO.builder().firstName("ashu").dateOfBirth(new Date()).adddress("ashu").username("ashu")
                .isActive(true).lastName("godse").build();

		TraineeResponseDTO traineeResponseDTO=TraineeResponseDTO.builder()
											  .username("ashu")
											  .firstName("ashu")
											  .lastName("godse")
											  .address("pune")
											  .isActive(true)
											  .dateofBirth(new Date())
											  .listOfTrainers(null)
											  .build();

		Mockito.when(traineeServiceimpl.updateProfile(any(TraineeRequestDTO.class))).thenReturn(traineeResponseDTO);
		
		mockMvc.perform(put("/gym/trainees").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(traineeRequestDTO))).andExpect(status().isOk());
	}

	@Test
	void testDeleteTrainee() throws Exception {
		traineeServiceimpl.deleteTraineeProfile("ashu");
		mockMvc.perform(delete("/gym/trainees/ashu")).andExpect(status().isOk());
	}
	
	@Test
	void testGetTraineeTrainingsList() throws Exception{
		List<TrainingTraineeResponseDTO>list=new ArrayList<>();
		Mockito.when(traineeServiceimpl.getTraineeTrainingDetails(any())).thenReturn(list);
		mockMvc.perform(get("/gym/trainees/trainee/ashu")).andExpect(status().isOk());
	}
	
	@Test
    void testUpdateTraineeTrainers() throws Exception {
        String traineeUserName = "trainee123";
        List<String> trainerUsernames = Arrays.asList("trainer1", "trainer2");
        List<TrainerListResponseDTO> responseDTOs = new ArrayList<>();
        when(traineeServiceimpl.updateTraineeTrainers(anyString(), any(List.class))).thenReturn(responseDTOs);

        mockMvc.perform(put("/gym/trainees/trainers/{traineeUserName}", traineeUserName)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(trainerUsernames)))
                .andExpect(status().isOk());

    }
}
 





	
	


