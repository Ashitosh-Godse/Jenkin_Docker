package com.epam.dto;

import java.util.Date;
import java.util.List;

import com.epam.model.Trainer;
import com.epam.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class TraineeDTO {
	private int traineeId;	
	private Date dateOfBirth;	
	private String adddress;
	private User user;
	private String email;
	List<Trainer> listOfTrainers;
	public TraineeDTO(Date dateOfBirth, String adddress, User user, String email, List<Trainer> listOfTrainers) {
		super();
		this.dateOfBirth = dateOfBirth;
		this.adddress = adddress;
		this.user = user;
		this.listOfTrainers = listOfTrainers;
		this.email=email;
	}
//	public TraineeDTO(Date dateOfBirth, String adddress, User user, String email) {
//		super();
//		this.dateOfBirth = dateOfBirth;
//		this.adddress = adddress;
//		this.user = user;
//		this.email=email;
//	}
	
}
