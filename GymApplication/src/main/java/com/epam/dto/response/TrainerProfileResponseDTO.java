package com.epam.dto.response;

import java.util.List;

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
public class TrainerProfileResponseDTO {
	private String firstName;
	private String lastName;
	private String specialization;
	private boolean isActive;
	
	private List<TraineeListResponseDTO> listOfTrainees;
	
}
