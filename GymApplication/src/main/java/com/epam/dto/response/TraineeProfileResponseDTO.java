package com.epam.dto.response;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeProfileResponseDTO {
	private String firstName;
	private String lastName;
	private Date dateofBirth;
	private String address;
	private boolean isActive;
	
	private List<TrainerListResponseDTO> listOfTrainers;
}
