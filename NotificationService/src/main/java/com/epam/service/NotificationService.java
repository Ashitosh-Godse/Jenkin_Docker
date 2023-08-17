package com.epam.service;

import com.epam.dto.request.MailRequestDTO;

import jakarta.mail.MessagingException;

public interface NotificationService {
	public void sendMail(MailRequestDTO emailDTO) throws MessagingException;
}
