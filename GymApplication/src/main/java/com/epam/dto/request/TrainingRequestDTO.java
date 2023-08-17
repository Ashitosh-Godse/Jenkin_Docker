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
@Builder
@AllArgsConstructor
public class TrainingRequestDTO {
	@NotBlank(message="traineeUsername cannot be blank or null")
    private String traineeUsername;
	
	@NotBlank(message="trainerUsername cannot be blank or null")
	private String trainerUsername;
	
	@NotBlank(message="trainingName cannot be blank or null")
	private String trainingName;
	

	private Date date;
	
	@NotBlank(message="trainingType cannot be blank or null")
	private String trainingType;

	private int duration;
}
