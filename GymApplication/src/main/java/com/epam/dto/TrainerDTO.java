package com.epam.dto;

import java.util.List;

import com.epam.model.Trainee;
import com.epam.model.TrainingType;
import com.epam.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrainerDTO {

	private int trainerId;
	private User user;
	private TrainingType trainingType;
	private String email;
	List<Trainee>listOfTrainees;
	
	public TrainerDTO(User user, TrainingType trainingType, String email,List<Trainee> listOfTrainees) {
		super();
		this.user = user;
		this.trainingType = trainingType;
		this.listOfTrainees = listOfTrainees;
		this.email=email;
	}
	
	


}
