package com.epam.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.customexception.GymException;
import com.epam.service.UserService;

@RestController
@RequestMapping("/gym/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/login")
	public ResponseEntity<Void> login(String username,String password) throws GymException{
		userService.login(username, password);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@GetMapping("/change-password")
	public ResponseEntity<Void> login(String username,String oldPassword,String newPassword) throws GymException{
		userService.changePassword(username, oldPassword, newPassword);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}

}
