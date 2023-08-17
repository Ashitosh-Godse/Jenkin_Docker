package com.epam.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.customexception.GymException;
import com.epam.dto.request.TrainingRequestDTO;
import com.epam.dto.response.TrainerListResponseDTO;
import com.epam.service.TrainingServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gym/training")
@Slf4j
public class TrainingController {
	@Autowired
	TrainingServiceImpl trainingService;
	
	@PostMapping
	public ResponseEntity<Void> addTraining(@RequestBody @Valid TrainingRequestDTO trainingRequestDTO) throws GymException{
		log.info("Entered into registerTrainee Controller with TrainingRequestDTO :"+trainingRequestDTO);		
		trainingService.addTraining(trainingRequestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@GetMapping("/{traineeUserName}")
	public ResponseEntity<List<TrainerListResponseDTO>> getTrainerByUserName(@PathVariable String traineeUserName) throws GymException{
		log.info("Entered into getTrainerByUserName Controller with traineeUserName :"+traineeUserName);		
		return new ResponseEntity<>(trainingService.unassignedTrainers(traineeUserName),HttpStatus.OK);
		
	}
}
