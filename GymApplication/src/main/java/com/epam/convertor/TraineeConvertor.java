package com.epam.convertor;

import java.util.List;

import com.epam.dto.response.TraineeListResponseDTO;
import com.epam.dto.response.TraineeProfileResponseDTO;
import com.epam.dto.response.TraineeResponseDTO;
import com.epam.dto.response.TrainerListResponseDTO;
import com.epam.model.Trainee;
import com.epam.model.User;

public class TraineeConvertor {

 public static TraineeProfileResponseDTO convertTraineeModelToTraineeProfileResponseDTO(Trainee trainee,List<TrainerListResponseDTO>listOfTrainers) {

	return 	TraineeProfileResponseDTO.builder()
	   .address(trainee.getAdddress())
	   .dateofBirth(trainee.getDateOfBirth())
	   .firstName(trainee.getUser().getFirstName())
	   .lastName(trainee.getUser().getLastName())
	   .isActive(true)
	   .listOfTrainers(listOfTrainers)
	   .build();
}
 
 public static TraineeResponseDTO convertTraineeModelToTraineeResponseDTO(Trainee trainee,List<TrainerListResponseDTO>listOfTrainers) {

	 return TraineeResponseDTO.builder()
		   .address(trainee.getAdddress())
		   .dateofBirth(trainee.getDateOfBirth())
		   .firstName(trainee.getUser().getFirstName())
		   .lastName(trainee.getUser().getLastName())
		   .isActive(trainee.getUser().isActive())
		   .username(trainee.getUser().getUserName())
		   .listOfTrainers(listOfTrainers)
		   .build();
 }
 
 public static TraineeListResponseDTO convertTraineeToTraineeListResponseDTO(User trainee) {
	  return  TraineeListResponseDTO.builder()
			  .userName(trainee.getUserName())
			  .firstName(trainee.getFirstName())
			  .lastName(trainee.getLastName())
			  .build();

	 }

}
