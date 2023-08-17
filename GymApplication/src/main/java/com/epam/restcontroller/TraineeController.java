package com.epam.restcontroller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.customexception.GymException;
import com.epam.dto.request.TraineeProfileDTO;
import com.epam.dto.request.TraineeRequestDTO;
import com.epam.dto.request.TraineeRequestTrainingDTO;
import com.epam.dto.response.CredentialsDTO;
import com.epam.dto.response.TraineeProfileResponseDTO;
import com.epam.dto.response.TraineeResponseDTO;
import com.epam.dto.response.TrainerListResponseDTO;
import com.epam.dto.response.TrainingTraineeResponseDTO;
import com.epam.service.TraineeServiceimpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("gym/trainees")
@Slf4j
public class TraineeController {
	
	@Autowired
	TraineeServiceimpl traineeService; 
	
	@PostMapping
	public ResponseEntity<CredentialsDTO> registerTrainee(@RequestBody @Valid TraineeProfileDTO traineeProfileDTO) throws GymException{	
		log.info("Entered into register Trainee Controller with TraineeProfileDTO :"+traineeProfileDTO);		
		return new ResponseEntity<>(traineeService.registerTrainee(traineeProfileDTO),HttpStatus.OK);
		
	}
	
	
	@GetMapping("/{username}")
	public ResponseEntity<TraineeProfileResponseDTO> getTraineeByUserName(@PathVariable String username) throws GymException{
		log.info("Entered into getTraineeByUserName Controller with userName :"+username);
		return new ResponseEntity<>(traineeService.getTraineeProfile(username),HttpStatus.OK);
		
	}
	@PutMapping
	public ResponseEntity<TraineeResponseDTO> updateTraineeProfile(@RequestBody @Valid TraineeRequestDTO traineeRequestDTO) throws GymException{
		log.info("Entered into updateTraineeProfile Controller with TraineeRequestDTO :"+traineeRequestDTO);
		return new ResponseEntity<>(traineeService.updateProfile(traineeRequestDTO),HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{username}")
	public ResponseEntity<Void> deleteTrainee(@PathVariable String username) throws GymException{
		log.info("Entered into deleteTrainee Controller with username :"+username);
		traineeService.deleteTraineeProfile(username);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	@PutMapping("/trainers/{traineeUserName}")
	public ResponseEntity<List<TrainerListResponseDTO>> updateTraineeTrainersList(@PathVariable String traineeUserName,@RequestBody List<String> trainerUsernames) throws GymException{
		log.info("Entered into registerTrainee Controller with traineeUserName and trainerUsernames:"+traineeUserName +":"+trainerUsernames);
		return new ResponseEntity<>(traineeService.updateTraineeTrainers(traineeUserName,trainerUsernames),HttpStatus.OK);
		
	}

	@GetMapping("/trainee/{username}")
    public ResponseEntity<List<TrainingTraineeResponseDTO>> getTraineeTrainingsList(
            @PathVariable String username,
            @RequestParam(required = false) @Valid Date periodFrom,
            @RequestParam(required = false) @Valid Date periodTo,
            @RequestParam(required = false) String trainerName,
            @RequestParam(required = false) String trainingType) throws GymException {
        log.info("trainee username received to fetch training details");
        TraineeRequestTrainingDTO training=TraineeRequestTrainingDTO.builder()
                .periodFrom(periodFrom).periodTo(periodTo).trainerName(trainerName)
                .userName(username).trainingType(trainingType).build();
      
        List<TrainingTraineeResponseDTO> trainings = traineeService.getTraineeTrainingDetails(training);
        return ResponseEntity.ok(trainings);
}
	

}
