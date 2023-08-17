package com.epam.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.request.MailRequestDTO;
import com.epam.service.NotificationServiceImpl;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/notification")
public class NotificationController {
	
	@Autowired
	private NotificationServiceImpl notificationServiceImpl;
	
	@PostMapping
	public ResponseEntity<Void> sendEmailAfterRegistration(@RequestBody MailRequestDTO emailDTO) throws MessagingException{
		notificationServiceImpl.sendMail(emailDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
