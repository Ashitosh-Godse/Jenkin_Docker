package com.epam.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.dto.ReportResponseDTO;
import com.epam.dto.request.ReportRequestDTO;
import com.epam.restcontroller.ReportController;
import com.epam.service.ReportServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReportController.class)
class ReportControllerTest{
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
    private ReportServiceImpl reportService;



    @Test
    void testGetReportForValidUserName() throws Exception {
        Map<Integer, Map<Integer, Map<Integer, Integer>>> summary = new HashMap<>();
        String userName = "john_doe";
        ReportResponseDTO reportResponseDTO = ReportResponseDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .userName("john_doe")
                .isActive(true)
                .summary(summary)
                .build();

        when(reportService.getReport(userName)).thenReturn(reportResponseDTO);

        mockMvc.perform(get("/report/{userName}", userName))
        .andExpect(status().isOk());
    
    }
    
    @Test
    void testAddReport() throws Exception {
    	ReportRequestDTO reportRequestDTO = ReportRequestDTO.builder()
                .trainerUserName("john_doe")
                .trainerFirstName("John")
                .trainerLastName("Doe")
                .status(true)
                .duration(60)
                .startDate(new Date())
                .build();

       Mockito.doNothing()
       .when(reportService).addTrainingReport(reportRequestDTO);

        mockMvc.perform(post("/report")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(new ObjectMapper().writeValueAsString(reportRequestDTO)))
        .andExpect(status().isCreated());
    
    }
    
    
    

    

}
