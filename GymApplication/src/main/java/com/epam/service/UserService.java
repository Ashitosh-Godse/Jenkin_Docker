package com.epam.service;

import com.epam.customexception.GymException;

public interface UserService {

	public String generatePassword();

	public void login(String username, String password) throws GymException;

	public void changePassword(String username, String oldPassword, String newPassword) throws GymException;	
}
