package com.epam.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.epam.dto.request.MailRequestDTO;
import com.epam.restcontroller.NotificationController;
import com.epam.service.NotificationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest{
	   @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private NotificationServiceImpl notificationService;



	    @Test
	     void testSendEmailAfterRegistration() throws Exception {
	        MailRequestDTO emailDTO = MailRequestDTO.builder()
	                .toMail("to@example.com")
	                .ccMails(Arrays.asList("cc1@example.com", "cc2@example.com"))
	                .bccMails(Arrays.asList("bcc1@example.com", "bcc2@example.com"))
	                .details(Collections.singletonMap("key", "value"))
	                .build();

	        doNothing().when(notificationService).sendMail(emailDTO);

	        mockMvc.perform(post("/notification")
	                .contentType("application/json")
	                .content(new ObjectMapper().writeValueAsString(emailDTO)))
	                .andExpect(MockMvcResultMatchers.status().isOk());

	        
	    }

}
