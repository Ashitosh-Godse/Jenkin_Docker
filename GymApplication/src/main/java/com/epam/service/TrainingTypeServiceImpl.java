package com.epam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.model.TrainingType;
import com.epam.repository.TrainingTypeRepo;

import jakarta.transaction.Transactional;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {

	@Autowired
	private TrainingTypeRepo trainingRepo;
	
	@Override
	@Transactional
	public void addTrainingType(String trainingName){
		trainingRepo.save(TrainingType.builder().trainingTypeName(trainingName).build());
	}
}
