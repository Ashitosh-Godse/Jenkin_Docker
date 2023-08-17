package com.epam.service;

import java.util.List;

import com.epam.customexception.GymException;
import com.epam.dto.request.TrainerProfileDTO;
import com.epam.dto.request.TrainerRequestDTO;
import com.epam.dto.request.TrainerRequestTrainingDTO;
import com.epam.dto.response.CredentialsDTO;
import com.epam.dto.response.TrainerProfileResponseDTO;
import com.epam.dto.response.TrainerResponseDTO;
import com.epam.dto.response.TrainingTrainerResponseDTO;

public interface TrainerService {
	public CredentialsDTO registerTrainer(TrainerProfileDTO trainerDTO)throws GymException;
	public TrainerProfileResponseDTO getTrainerProfile(String username) throws GymException;
	public TrainerResponseDTO updateProfile(TrainerRequestDTO trainerRequestDTO) throws GymException;
	List<TrainingTrainerResponseDTO> getTrainingList(TrainerRequestTrainingDTO trainerRequestTrainingDTO)
    throws GymException;
	List<TrainingTrainerResponseDTO> getTrainerTrainingDetails(TrainerRequestTrainingDTO training)
			throws GymException;


}
