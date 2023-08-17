package com.epam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.epam.dto.request.MailRequestDTO;
import com.epam.model.EmailSummary;
import com.epam.repository.NotificationRepo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService{
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private NotificationRepo notificationRepo; 
	
	@Value("${spring.mail.username}")
	private String emailSender;

	
	
	@KafkaListener( topics="topic-notification" ,groupId ="myGroup")
    public void sendMail(MailRequestDTO mailRequestDTO) throws MessagingException {
  
		MimeMessage mimeMessage=javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage, true);
		
		mimeMessageHelper.setSubject(mailRequestDTO.getDetails().get("subject"));
		mimeMessageHelper.setFrom(emailSender);
		mimeMessageHelper.setTo(mailRequestDTO.getToMail());
		
		String body=mailRequestDTO.getDetails().entrySet().stream()
	            .map(entry -> entry.getKey() + " " + entry.getValue())
	            .reduce("", (line1, line2) -> line1.isEmpty() ? line2 : line1 + ", " + line2+"\n");
		
		mimeMessageHelper.setText(body, true);
		
		EmailSummary emailSummary = EmailSummary.builder()
					                .ccMails(mailRequestDTO.getCcMails())
									.subject(mailRequestDTO.getDetails().get("subject"))
									.bccMail(mailRequestDTO.getBccMails())
									.body(body)
									.fromMail(emailSender)
									.toMail(mailRequestDTO.getToMail())
									.subject(mailRequestDTO.getDetails().get("subject")).build();

		try {
			
				javaMailSender.send(mimeMessage);
				emailSummary.setStatus("sent");
				emailSummary.setRemark("mail sent successfully");
				log.info("mail sent successfully!!");
		
		}
		
		catch(MailException e) {
			
				emailSummary.setStatus("failed");
				emailSummary.setRemark("failed to sent");		
				log.error("error!!! mail not sent!!!!!!");
			
		}
		
		notificationRepo.save(emailSummary);
	}

}
