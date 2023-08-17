package com.epam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.epam.dto.request.MailRequestDTO;
import com.epam.dto.request.ReportRequestDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducer {
	
	@Autowired
	private KafkaTemplate<String, Message<MailRequestDTO>> kafkaTemplate;
	
	@Autowired
	private KafkaTemplate<String, Message<ReportRequestDTO>> kafkaReportTemplate;
	
	public void sendMail(Message<MailRequestDTO> message) {
		
		log.info("Entered into sendMail with message :"+message);
		kafkaTemplate.send(message);
		log.info("Exiting from into sendMail ");
		
	}
	
    public void sendReport(Message<ReportRequestDTO> message) {
		
		log.info("Entered into sendMail with message :"+message);
		kafkaReportTemplate.send(message);
		log.info("Exiting from into sendMail ");
		
	}
	
	

}
