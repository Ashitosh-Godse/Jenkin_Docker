package com.epam.convertor;

import com.epam.dto.request.ReportRequestDTO;
import com.epam.dto.response.TrainingTraineeResponseDTO;
import com.epam.dto.response.TrainingTrainerResponseDTO;
import com.epam.model.Training;

public class TrainingConvertor {

	 public static TrainingTraineeResponseDTO convertTrainingToTrainingTraineeResponseDTO(Training training) {
		  return  TrainingTraineeResponseDTO.builder()
                  .trainingName(training.getName())
                  .date(training.getDate())
                  .trainingType(training.getTrainingType().getTrainingTypeName())
                  .duration(training.getDuration())
                  .trainerName(training.getTrainer().getUser().getFirstName() + " " + training.getTrainer().getUser().getLastName())
                  .build();
			   
		 }
	 public static TrainingTrainerResponseDTO convertTrainingToTrainingTrainerResponseDTO(Training training) {
		  return  TrainingTrainerResponseDTO.builder().trainingName(training.getName())
					.date(training.getDate()).type(training.getTrainingType().getTrainingTypeName())
					.duration(training.getDuration())
					.traineeName(training.getTrainee().getUser().getFirstName() + " "
							     + training.getTrainee().getUser().getLastName())
					.build();
			   
		 }
	 public static ReportRequestDTO convertTrainingModelToReportRequestDTO(Training training) {
		 return  ReportRequestDTO.builder()
				  .startDate(training.getDate())
				  .trainerFirstName(training.getTrainer().getUser().getFirstName())
				  .trainerLastName(training.getTrainer().getUser().getLastName())
				  .trainerUserName(training.getTrainer().getUser().getUserName())
				  .status(training.getTrainer().getUser().isActive())
				  .duration(training.getDuration())
				  .build();
	 }
}
