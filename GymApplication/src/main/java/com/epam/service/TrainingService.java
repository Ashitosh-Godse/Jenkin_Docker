package com.epam.service;

import java.util.List;

import com.epam.customexception.GymException;
import com.epam.dto.request.TrainingRequestDTO;
import com.epam.dto.response.TrainerListResponseDTO;

public interface TrainingService {
public void addTraining(TrainingRequestDTO trainingRequestDTO) throws GymException;

List<TrainerListResponseDTO> unassignedTrainers(String username) throws GymException;
}
