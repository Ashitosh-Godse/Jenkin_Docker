package com.epam.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.epam.model.EmailSummary;

public interface NotificationRepo extends MongoRepository<EmailSummary, String> {

}
