package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.response.CredentialsDTO;
@ExtendWith(MockitoExtension.class)
 class CredentialsDTOTest {

    @Test
     void testNoArgsConstructor() {
        CredentialsDTO credentialsDTO = new CredentialsDTO();
        assertNotNull(credentialsDTO);
    }

    @Test
     void testAllArgsConstructor() {
        CredentialsDTO credentialsDTO = new CredentialsDTO("username", "password");
        assertNotNull(credentialsDTO);
        assertEquals("username", credentialsDTO.getUsername());
        assertEquals("password", credentialsDTO.getPassword());
    }

    @Test
     void testGetterAndSetterAnnotations() {
        CredentialsDTO credentialsDTO = new CredentialsDTO();
        credentialsDTO.setUsername("username");
        credentialsDTO.setPassword("password");

        assertEquals("username", credentialsDTO.getUsername());
        assertEquals("password", credentialsDTO.getPassword());
    }
}






