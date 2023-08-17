package com.epam.restcontroller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.service.TrainingTypeServiceImpl;

@WebMvcTest( TrainingTypeController.class)
class TrainingTypeControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	TrainingTypeServiceImpl trainingTypeServiceImpl;
	
	@Test
    void testAddNewTrainingType() throws Exception {
        String trainingTypeName = "Strength Training";

        doNothing().when(trainingTypeServiceImpl).addTrainingType(anyString());
        mockMvc.perform(post("/gym/training-type/{trainingTypeName}", trainingTypeName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}
