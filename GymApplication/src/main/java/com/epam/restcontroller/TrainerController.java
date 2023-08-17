package com.epam.restcontroller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.customexception.GymException;
import com.epam.dto.request.TrainerProfileDTO;
import com.epam.dto.request.TrainerRequestDTO;
import com.epam.dto.request.TrainerRequestTrainingDTO;
import com.epam.dto.response.CredentialsDTO;
import com.epam.dto.response.TrainerProfileResponseDTO;
import com.epam.dto.response.TrainerResponseDTO;
import com.epam.dto.response.TrainingTrainerResponseDTO;
import com.epam.service.TrainerServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gym/trainers")
@Slf4j
public class TrainerController {
	
	@Autowired
	TrainerServiceImpl trainerService; 
	
	@PostMapping
	public ResponseEntity<CredentialsDTO> registerTrainee(@RequestBody @Valid TrainerProfileDTO trainerProfileDTO) throws  GymException{
		log.info("Entered into register trainer Controller with TrainerProfileDTO :"+trainerProfileDTO);		
		return new ResponseEntity<>(trainerService.registerTrainer(trainerProfileDTO),HttpStatus.OK);
		
	}
	
	@GetMapping("/{username}") 
	public ResponseEntity<TrainerProfileResponseDTO> getTrainerByUserName(@PathVariable String username) throws GymException{
		log.info("Entered into getTrainerByUserNamewith Controller with username :"+username);		
		return new ResponseEntity<>(trainerService.getTrainerProfile(username),HttpStatus.OK);
		
	}
	
	@PutMapping
	public ResponseEntity<TrainerResponseDTO> updareTrainerProfile(@RequestBody @Valid TrainerRequestDTO trainerRequestDTO) throws GymException{
		log.info("Entered into updareTrainerProfile Controller with TrainerRequestDTO :"+trainerRequestDTO);		
		return new ResponseEntity<>(trainerService.updateProfile(trainerRequestDTO),HttpStatus.OK);
		
	}
	
	@GetMapping
	public ResponseEntity<List<TrainingTrainerResponseDTO>> getTrainingList(@RequestBody @Valid TrainerRequestTrainingDTO trainerRequestTrainingDTO) throws GymException{
		log.info("Entered into  getTrainingList Controller with TrainerRequestTrainingDTO :"+trainerRequestTrainingDTO);		
		return new ResponseEntity<>(trainerService.getTrainingList(trainerRequestTrainingDTO),HttpStatus.OK);
		
	} 
	

	
	@GetMapping("/trainer/{username}")
    public ResponseEntity<List<TrainingTrainerResponseDTO>> getTrainerTrainingsList(
            @PathVariable String username,
            @RequestParam(required = false) @Valid Date periodFrom,
            @RequestParam(required = false)  @Valid Date periodTo,
            @RequestParam(required = false) @Valid String traineeName) throws GymException{
        log.info("trainer username received to fetch training details");
        TrainerRequestTrainingDTO training=TrainerRequestTrainingDTO.builder()
                .periodFrom(periodFrom).periodTo(periodTo).traineeName(traineeName)
                .userName(username).build();
        List<TrainingTrainerResponseDTO> trainings = trainerService.getTrainerTrainingDetails(training);
        return ResponseEntity.ok(trainings);
}
	
	
}
