package com.epam.convertor;

import java.util.List;

import com.epam.dto.response.TraineeListResponseDTO;
import com.epam.dto.response.TrainerListResponseDTO;
import com.epam.dto.response.TrainerProfileResponseDTO;
import com.epam.model.Trainer;
import com.epam.model.User;

public class TrainerConvertor {
	
	 public static TrainerListResponseDTO convertTrainerToTrainerListResponseDTO(User trainer) {
	  return  TrainerListResponseDTO.builder()
				 .userName(trainer.getUserName())
				 .firstName(trainer.getFirstName())
				 .lastName(trainer.getLastName())
				 .specialization(trainer.getTrainer().getTrainingType().getTrainingTypeName()).build();
		   
	 }
	 public static TrainerProfileResponseDTO convertTrainerModelToTrainerProfileResponseDTO(Trainer trainer,List<TraineeListResponseDTO>listOfTrainees) {

			return 	TrainerProfileResponseDTO.builder().specialization(trainer.getTrainingType().getTrainingTypeName())
					.firstName(trainer.getUser().getFirstName()).lastName(trainer.getUser().getLastName()).isActive(true)
					.listOfTrainees(listOfTrainees).build();
		}
}
