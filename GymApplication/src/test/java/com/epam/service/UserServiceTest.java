package com.epam.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.customexception.GymException;
import com.epam.model.User;
import com.epam.repository.UserRepo;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	    @Mock
	     UserRepo userRepo;

	    @InjectMocks
	     UserServiceImpl userServiceImpl;

	    @Test
	     void testLogin_ValidCredentials() throws GymException {
	        String username = "john_doe";
	        String password = "secretpassword";

	        when(userRepo.existsByUserNameAndPassword(username, password)).thenReturn(true);

	        assertDoesNotThrow(() -> userServiceImpl.login(username, password));
	        verify(userRepo, times(1)).existsByUserNameAndPassword(username, password);
	    }

	    @Test
	     void testLoginInvalidCredentials() {
	        String username = "john_doe";
	        String password = "wrongpassword";

	        when(userRepo.existsByUserNameAndPassword(username, password)).thenReturn(false);

	        GymException exception = assertThrows(GymException.class, () -> userServiceImpl.login(username, password));
	        assertEquals("Invalid Credentials", exception.getMessage());
	        verify(userRepo, times(1)).existsByUserNameAndPassword(username, password);
	    }

	    @Test
	     void testChangePasswordValidCredentials() throws GymException {
	        String username = "john_doe";
	        String oldPassword = "oldpassword";
	        String newPassword = "newpassword";
	        User user = new User(); // Create a User instance as needed

	        when(userRepo.findByUserName(username)).thenReturn(Optional.of(user));
	        when(userRepo.save(any(User.class))).thenReturn(user);

	        assertDoesNotThrow(() -> userServiceImpl.changePassword(username, oldPassword, newPassword));
	        verify(userRepo, times(1)).findByUserName(username);
	        verify(userRepo, times(1)).save(any(User.class));
	    }

	    @Test
	     void testChangePasswordInvalidCredentials() {
	        String username = "john_doe";
	        String oldPassword = "wrongoldpassword";
	        String newPassword = "newpassword";

	        when(userRepo.findByUserName(username)).thenReturn(Optional.empty());

	        GymException exception = assertThrows(GymException.class, () -> userServiceImpl.changePassword(username, oldPassword, newPassword));
	        assertEquals("Invalid Credentials", exception.getMessage());
	        verify(userRepo, times(1)).findByUserName(username);
	        verify(userRepo, never()).save(any(User.class));
	    }

	    @Test
	     void testGeneratePassword() {
	        String generatedPassword = userServiceImpl.generatePassword();
	        assertNotNull(generatedPassword);
//	        assertEquals(11, generatedPassword.length());
	    }
}
