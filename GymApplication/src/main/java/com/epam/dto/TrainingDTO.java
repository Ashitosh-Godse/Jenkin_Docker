package com.epam.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.TrainingType;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrainingDTO {
	private int trainingId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;

	private String trainingType;

	private String duration;

	public TrainingDTO(Date date, String trainingType, String duration, String name, Trainee trainee, Trainer trainer) {
		super();
		this.date = date;
		this.trainingType = trainingType;
		this.duration = duration;
		this.name = name;
		this.trainee = trainee;
		this.trainer = trainer;
	}

	private String name;

	private Trainee trainee;

	private Trainer trainer;

}
