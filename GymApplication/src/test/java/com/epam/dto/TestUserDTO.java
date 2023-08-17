package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.request.UserDTO;

@ExtendWith(MockitoExtension.class)
public class TestUserDTO {
	 @Test
	    void testUserDTONoArgsConstructor() {
	        UserDTO user = new UserDTO();
	        assertNull(user.getUserName());
	        assertNull(user.getFirstName());
	        assertNull(user.getLastName());
	        assertFalse(user.isActive());
	    }

	    @Test
	     void testUserDTOAllArgsConstructor() {
	        String userName = "john_doe";
	        String firstName = "John";
	        String lastName = "Doe";
	        boolean isActive = true;

	        UserDTO user = new UserDTO(userName, firstName, lastName, isActive);
	        assertEquals(userName, user.getUserName());
	        assertEquals(firstName, user.getFirstName());
	        assertEquals(lastName, user.getLastName());
	        assertEquals(isActive, user.isActive());
	    }

	    @Test
	    void testUserDTOGetterSetter() {
	        UserDTO user = new UserDTO();
	        user.setUserName("alice");
	        user.setFirstName("Alice");
	        user.setLastName("Johnson");
	        user.setActive(true);

	        assertEquals("alice", user.getUserName());
	        assertEquals("Alice", user.getFirstName());
	        assertEquals("Johnson", user.getLastName());
	        assertTrue(user.isActive());
	    }

	    @Test
	   public void testUserDTOBuilder() {
	        String userName = "jane_smith";
	        String firstName = "Jane";
	        String lastName = "Smith";
	        boolean isActive = false;

	        UserDTO user = UserDTO.builder()
	            .userName(userName)
	            .firstName(firstName)
	            .lastName(lastName)
	            .isActive(isActive)
	            .build();

	        assertEquals(userName, user.getUserName());
	        assertEquals(firstName, user.getFirstName());
	        assertEquals(lastName, user.getLastName());
	        assertEquals(isActive, user.isActive());
	    }


}
