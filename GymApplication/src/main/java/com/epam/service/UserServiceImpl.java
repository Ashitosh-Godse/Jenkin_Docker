package com.epam.service;

import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.customexception.GymException;
import com.epam.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public void login(String username, String password) throws GymException {
		
		 if(userRepo.existsByUserNameAndPassword(username, password)) {
			 log.info("logging successful");
		 }
		 else {
			 throw new GymException("Invalid Credentials");
		 }
	}

	@Override
	public void changePassword(String username, String oldPassword, String newPassword) throws GymException {
		
				 userRepo.findByUserName(username).map(user->{
				 user.setPassword(newPassword);
				 log.info("password changed successful");
				 return userRepo.save(user);
			 }).orElseThrow(()->new GymException("Invalid Credentials"));
		 
		
  
    }

	@Override
	public String generatePassword() {
		String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	      String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
	      String specialCharacters = "!@#$";
	      String numbers = "1234567890";
	      String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
	      Random random = new Random();
	      char[] password = new char[8];

	      password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
	      password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
	      password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
	      password[3] = numbers.charAt(random.nextInt(numbers.length()));
	   
	      for(int i = 4; i< 8 ; i++) {
	         password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
	      }
	      return password.toString();
		
	}

	
}
