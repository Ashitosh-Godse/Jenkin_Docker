package com.epam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.model.TrainingType;

public interface TrainingTypeRepo extends JpaRepository<TrainingType, Integer>{
	
	Optional<TrainingType> findByTrainingTypeName(String trainingtypeName);
}
