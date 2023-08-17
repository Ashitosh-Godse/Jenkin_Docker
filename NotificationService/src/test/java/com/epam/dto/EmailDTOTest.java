package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.request.MailRequestDTO;

@ExtendWith(MockitoExtension.class)

class EmailDTOTest {
	@Test
    public void testNoArgsConstructor() {
        EmailDTO emailDTO = new EmailDTO();
        assertNotNull(emailDTO);
    }

    @Test
    public void testAllArgsConstructor() {
        List<String> ccList = Arrays.asList("cc1@example.com", "cc2@example.com");
        List<String> bccList = Arrays.asList("bcc1@example.com", "bcc2@example.com");

        EmailDTO emailDTO = new EmailDTO("to@example.com", ccList, bccList, "Test Subject", "Test Body");

        assertNotNull(emailDTO);
        assertEquals("to@example.com", emailDTO.getEmailTo());
        assertEquals(ccList, emailDTO.getCc());
        assertEquals(bccList, emailDTO.getBcc());
        assertEquals("Test Subject", emailDTO.getSubject());
        assertEquals("Test Body", emailDTO.getBody());
    }

    @Test
    public void testBuilder() {
        EmailDTO emailDTO = EmailDTO.builder()
                .emailTo("to@example.com")
                .cc(Arrays.asList("cc1@example.com", "cc2@example.com"))
                .bcc(Arrays.asList("bcc1@example.com", "bcc2@example.com"))
                .subject("Test Subject")
                .body("Test Body")
                .build();

        assertNotNull(emailDTO);
        assertEquals("to@example.com", emailDTO.getEmailTo());
        assertEquals(2, emailDTO.getCc().size());
        assertEquals(2, emailDTO.getBcc().size());
        assertEquals("Test Subject", emailDTO.getSubject());
        assertEquals("Test Body", emailDTO.getBody());
    }
}
