package com.epam.dto.request;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
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
public class TraineeRequestTrainingDTO {
	@NotBlank(message="userName cannot be blank or null")
	private String userName;
	private Date periodFrom;
	private Date periodTo;
	private String trainerName;
	private String trainingType; 
}
