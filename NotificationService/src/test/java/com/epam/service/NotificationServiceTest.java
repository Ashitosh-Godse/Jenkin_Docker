package com.epam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.epam.dto.request.MailRequestDTO;
import com.epam.model.EmailSummary;
import com.epam.repository.NotificationRepo;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
	
	@Mock
	private JavaMailSender javaMailSender;
	
	@Mock
	private NotificationRepo notificationRepo; 
	
	@Value("${spring.mail.username}")
	private String emailSender;
	
	@InjectMocks
	private NotificationServiceImpl notificationServiceImpl;
	
//	@Test
//   void testSendMail() throws MessagingException {
//        MailRequestDTO mailRequestDTO = MailRequestDTO.builder()
//                .toMail("recipient@example.com")
//                .ccMails(Collections.singletonList("cc@example.com"))
//                .bccMails(Collections.singletonList("bcc@example.com"))
//                .details(Collections.singletonMap("subject", "Test Subject"))
//                .build();
//
//        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session)null));
//        when(notificationRepo.save(any(EmailSummary.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
//
//        notificationServiceImpl.sendMail(mailRequestDTO);
//
//        verify(javaMailSender, times(1)).createMimeMessage();
//        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
//        verify(notificationRepo, times(1)).save(any(EmailSummary.class));
//    }
	
//	@Test
//    void testSendMail() throws MessagingException {
//        // Prepare test data
//        MailRequestDTO mailRequestDTO = new MailRequestDTO();
//        mailRequestDTO.setToMail("recipient@example.com");
//        mailRequestDTO.setCcMails(Arrays.asList("cc1@example.com", "cc2@example.com"));
//        mailRequestDTO.setBccMails(Arrays.asList("bcc1@example.com", "bcc2@example.com"));
//        mailRequestDTO.getDetails().put("subject", "Test Subject");
//        mailRequestDTO.getDetails().put("key1", "value1");
//        mailRequestDTO.getDetails().put("key2", "value2");
//
//        MimeMessage mimeMessage = new MimeMessage((Session) null);
//
//        // Mock MimeMessageHelper behavior
//        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
//
//        // Call the method
//        notificationServiceImpl.sendMail(mailRequestDTO);
//
//        // Verify interactions with mocks
//        verify(javaMailSender, times(1)).createMimeMessage();
//        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
//        verify(notificationRepo, times(1)).save(any(EmailSummary.class));
//
////        // You can also add assertions on the created MimeMessageHelper to check the contents if needed
////        ArgumentCaptor<MimeMessage> mimeMessageCaptor = ArgumentCaptor.forClass(MimeMessage.class);
////        verify(javaMailSender).send(mimeMessageCaptor.capture());
////        MimeMessageHelper capturedMimeMessageHelper = new MimeMessageHelper(mimeMessageCaptor.getValue());
////        assertEquals(mailRequestDTO.getToMail(), capturedMimeMessageHelper.getTo()[0].toString());
////        assertEquals(mailRequestDTO.getCcMails().get(0), capturedMimeMessageHelper.getCc()[0].toString());
////        assertEquals(mailRequestDTO.getBccMails().get(0), capturedMimeMessageHelper.getBcc()[0].toString());
////        assertEquals(emailSender, capturedMimeMessageHelper.getFrom());
////        assertEquals(mailRequestDTO.getDetails().get("subject"), capturedMimeMessageHelper.getSubject());
////        // ... add more assertions as needed
//    }
}
