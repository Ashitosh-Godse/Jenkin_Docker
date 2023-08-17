package com.epam.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.customexception.GymException;
import com.epam.service.TrainingTypeServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@RestController
@RequestMapping("/gym/training-type")
@Slf4j
public class TrainingTypeController {
	@Autowired
	TrainingTypeServiceImpl trainingTypeServiceImpl; 
	
	@PostMapping("/{trainingTypeName}")
	public ResponseEntity<Void> addNewTrainingType(@PathVariable @Valid String trainingTypeName) throws  GymException{
		log.info("Entered into addNewTrainingType  Controller with trainingTypeName :"+trainingTypeName);
		trainingTypeServiceImpl.addTrainingType(trainingTypeName);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
}
