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
class MailRequestDTOTest {
	@Test
     void testNoArgsConstructor() {
        MailRequestDTO mailRequestDTO = new MailRequestDTO();
        assertNotNull(mailRequestDTO);
    }

    @Test
    void testAllArgsConstructor() {
        List<String> ccMails = Arrays.asList("cc1@example.com", "cc2@example.com");
        List<String> bccMails = Arrays.asList("bcc1@example.com", "bcc2@example.com");
        Map<String, String> details = new HashMap<>();
        details.put("key1", "value1");
        details.put("key2", "value2");

        MailRequestDTO mailRequestDTO = new MailRequestDTO("to@example.com", ccMails, bccMails, details);

        assertNotNull(mailRequestDTO);
        assertEquals("to@example.com", mailRequestDTO.getToMail());
        assertIterableEquals(ccMails, mailRequestDTO.getCcMails());
        assertIterableEquals(bccMails, mailRequestDTO.getBccMails());
        assertEquals(details, mailRequestDTO.getDetails());
    }

    @Test
    void testBuilder() {
        MailRequestDTO mailRequestDTO = MailRequestDTO.builder()
                .toMail("to@example.com")
                .ccMails(Arrays.asList("cc1@example.com", "cc2@example.com"))
                .bccMails(Arrays.asList("bcc1@example.com", "bcc2@example.com"))
                .details(Collections.singletonMap("key", "value"))
                .build();

        assertNotNull(mailRequestDTO);
        assertEquals("to@example.com", mailRequestDTO.getToMail());
        assertEquals(2, mailRequestDTO.getCcMails().size());
        assertEquals(2, mailRequestDTO.getBccMails().size());
        assertEquals("value", mailRequestDTO.getDetails().get("key"));
    }
}
