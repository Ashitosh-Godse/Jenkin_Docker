package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserDTOTest {
	 @Test
	 void testNoArgsConstructor() {
	        UserDTO userDTO = new UserDTO();
	        assertNotNull(userDTO);
	    }

	    @Test
	   void testAllArgsConstructor() {
	        UserDTO userDTO = new UserDTO("username", "John", "Doe", true);
	        assertNotNull(userDTO);
	        assertEquals("username", userDTO.getUserName());
	        assertEquals("John", userDTO.getFirstName());
	        assertEquals("Doe", userDTO.getLastName());
	        assertTrue(userDTO.isActive());
	    }

	    @Test
	    void testBuilder() {
	        UserDTO userDTO = UserDTO.builder()
	                .userName("username")
	                .firstName("John")
	                .lastName("Doe")
	                .isActive(true)
	                .build();

	        assertNotNull(userDTO);
	        assertEquals("username", userDTO.getUserName());
	        assertEquals("John", userDTO.getFirstName());
	        assertEquals("Doe", userDTO.getLastName());
	        assertTrue(userDTO.isActive());
	    }

}
