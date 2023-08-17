package com.epam.dto.response;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingTraineeResponseDTO {
	private String  trainingName;
	private Date date;
	private String trainingType;
	private int duration;
	private String trainerName;
}
