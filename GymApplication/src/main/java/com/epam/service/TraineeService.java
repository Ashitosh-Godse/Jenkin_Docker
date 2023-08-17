package com.epam.service;


import java.util.List;

import com.epam.customexception.GymException;
import com.epam.dto.request.TraineeProfileDTO;
import com.epam.dto.request.TraineeRequestDTO;
import com.epam.dto.request.TraineeRequestTrainingDTO;
import com.epam.dto.response.CredentialsDTO;
import com.epam.dto.response.TraineeResponseDTO;
import com.epam.dto.response.TrainerListResponseDTO;
import com.epam.dto.response.TrainingTraineeResponseDTO;

public interface TraineeService {
	
	public CredentialsDTO registerTrainee(TraineeProfileDTO traineeProfileDTO)throws GymException;
    public TraineeResponseDTO updateProfile(TraineeRequestDTO traineeRequestDTO)throws GymException;
	public void deleteTraineeProfile(String username)throws GymException;
	List<TrainerListResponseDTO> updateTraineeTrainers(String traineeName, List<String> trainerUsernames)
			throws GymException;
	List<TrainingTraineeResponseDTO> getTraineeTrainingDetails(TraineeRequestTrainingDTO training)throws GymException;




	
	


}
