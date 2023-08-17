package com.epam.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.model.TrainingType;
import com.epam.repository.TrainingTypeRepo;

@ExtendWith(MockitoExtension.class)
class TrainingTypeServiceTest {
	@Mock
	private TrainingTypeRepo trainingTypeRepo;
	
	@InjectMocks
	private TrainingTypeServiceImpl trainingTypeServiceImpl;
	
	@Test
	void AddTrainingType() {
		TrainingType trainingType=TrainingType.builder()
								  .trainingTypeName("fitness")
								  .build();
		Mockito.when(trainingTypeRepo.save(any(TrainingType.class))).thenReturn(trainingType);
		trainingTypeServiceImpl.addTrainingType(anyString());
	}
	
}
