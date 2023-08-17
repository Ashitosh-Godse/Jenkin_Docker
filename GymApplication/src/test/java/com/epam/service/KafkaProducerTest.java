package com.epam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.epam.dto.request.MailRequestDTO;
import com.epam.dto.request.ReportRequestDTO;

@ExtendWith(MockitoExtension.class)
class KafkaProducerTest {
	@Mock
	private KafkaTemplate<String, Message<MailRequestDTO>> kafkaTemplate;
	
	@Mock
	private KafkaTemplate<String, Message<ReportRequestDTO>> kafkaReportTemplate;
	
	@Captor
    private ArgumentCaptor<Message<MailRequestDTO>> mailMessageCaptor;

    @Captor
    private ArgumentCaptor<Message<ReportRequestDTO>> reportMessageCaptor;
	
	@InjectMocks
	private KafkaProducer kafkaProducer;
	

	 @Test
	 void testSendMail() {
	        MailRequestDTO mailRequestDTO = new MailRequestDTO();

	        Message<MailRequestDTO> message = MessageBuilder.withPayload(mailRequestDTO).build();
	        kafkaProducer.sendMail(message);

	        verify(kafkaTemplate, times(1)).send(mailMessageCaptor.capture());

	        Message<MailRequestDTO> capturedMessage = mailMessageCaptor.getValue();
	        assertEquals(mailRequestDTO, capturedMessage.getPayload());
	      
	    }
	 
	 @Test
	 void testSendReport() {
	        ReportRequestDTO reportRequestDTO = new ReportRequestDTO();

	        Message<ReportRequestDTO> message = MessageBuilder.withPayload(reportRequestDTO).build();

	        kafkaProducer.sendReport(message);

	        verify(kafkaReportTemplate, times(1)).send(reportMessageCaptor.capture());

	        Message<ReportRequestDTO> capturedMessage = reportMessageCaptor.getValue();
	        assertEquals(reportRequestDTO, capturedMessage.getPayload());
	    }
}
