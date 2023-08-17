package com.epam.dto.response;

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
public class TrainerListResponseDTO {
	private String userName;
	private String firstName;
	private String lastName;
	private String specialization;
	
}
