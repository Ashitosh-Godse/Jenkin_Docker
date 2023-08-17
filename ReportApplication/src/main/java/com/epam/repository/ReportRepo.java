package com.epam.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.epam.model.Report;

public interface ReportRepo extends MongoRepository<Report, String> {

//	Optional<Report> findById(String trainerUsername);

}
