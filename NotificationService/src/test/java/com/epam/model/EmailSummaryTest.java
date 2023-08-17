package com.epam.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class)
class EmailSummaryTest {

    @Test
    void testIdAnnotation() {
        EmailSummary emailSummary = new EmailSummary();
        emailSummary.setFromMail("from@example.com");
        assertNull(emailSummary.getBccMail()); // The ID should be initially null
    }

    @Test
    public void testGetterAndSetterAnnotations() {
        EmailSummary emailSummary = new EmailSummary();
        emailSummary.setFromMail("from@example.com");
        emailSummary.setToMail("to@example.com");
        emailSummary.setSubject("Test Subject");
        emailSummary.setCcMails(Arrays.asList("cc1@example.com", "cc2@example.com"));
        emailSummary.setBccMail(Arrays.asList("bcc1@example.com", "bcc2@example.com"));
        emailSummary.setBody("Test body");
        emailSummary.setStatus("Sent");
        emailSummary.setRemark("Test remark");

        assertEquals("from@example.com", emailSummary.getFromMail());
        assertEquals("to@example.com", emailSummary.getToMail());
        assertEquals("Test Subject", emailSummary.getSubject());
        assertEquals(Arrays.asList("cc1@example.com", "cc2@example.com"), emailSummary.getCcMails());
        assertEquals(Arrays.asList("bcc1@example.com", "bcc2@example.com"), emailSummary.getBccMail());
        assertEquals("Test body", emailSummary.getBody());
        assertEquals("Sent", emailSummary.getStatus());
        assertEquals("Test remark", emailSummary.getRemark());
    }

    @Test
    public void testNoArgsConstructorAnnotation() {
        EmailSummary emailSummary = new EmailSummary();
        assertNull(emailSummary.getFromMail());
        assertNull(emailSummary.getToMail());
        assertNull(emailSummary.getSubject());
        assertNull(emailSummary.getCcMails());
        assertNull(emailSummary.getBccMail());
        assertNull(emailSummary.getBody());
        assertNull(emailSummary.getStatus());
        assertNull(emailSummary.getRemark());
    }

    @Test
    public void testAllArgsConstructorAnnotation() {
        List<String> ccMails = Arrays.asList("cc1@example.com", "cc2@example.com");
        List<String> bccMails = Arrays.asList("bcc1@example.com", "bcc2@example.com");
        EmailSummary emailSummary = new EmailSummary("from@example.com", "to@example.com",
                "Test Subject", ccMails, bccMails, "Test body", "Sent", "Test remark");

        assertEquals("from@example.com", emailSummary.getFromMail());
        assertEquals("to@example.com", emailSummary.getToMail());
        assertEquals("Test Subject", emailSummary.getSubject());
        assertEquals(ccMails, emailSummary.getCcMails());
        assertEquals(bccMails, emailSummary.getBccMail());
        assertEquals("Test body", emailSummary.getBody());
        assertEquals("Sent", emailSummary.getStatus());
        assertEquals("Test remark", emailSummary.getRemark());
    }

    @Test
    public void testBuilderAnnotation() {
        List<String> ccMails = Arrays.asList("cc1@example.com", "cc2@example.com");
        List<String> bccMails = Arrays.asList("bcc1@example.com", "bcc2@example.com");
        EmailSummary emailSummary = EmailSummary.builder()
                .fromMail("from@example.com")
                .toMail("to@example.com")
                .subject("Test Subject")
                .ccMails(ccMails)
                .bccMail(bccMails)
                .body("Test body")
                .status("Sent")
                .remark("Test remark")
                .build();

        assertEquals("from@example.com", emailSummary.getFromMail());
        assertEquals("to@example.com", emailSummary.getToMail());
        assertEquals("Test Subject", emailSummary.getSubject());
        assertEquals(ccMails, emailSummary.getCcMails());
        assertEquals(bccMails, emailSummary.getBccMail());
        assertEquals("Test body", emailSummary.getBody());
        assertEquals("Sent", emailSummary.getStatus());
        assertEquals("Test remark", emailSummary.getRemark());
    }
}
