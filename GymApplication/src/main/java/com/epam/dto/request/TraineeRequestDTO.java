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
public class TraineeRequestDTO {
	
	
	@NotBlank(message="username cannot be blank or null")
	private String username;
	
	@NotBlank(message="firstName cannot be blank or null")
	private String firstName;
	
	@NotBlank(message="lastName cannot be blank or null")
	private String lastName;
	
	
	@NotBlank(message="adddress cannot be blank or null")
	private String adddress;
	
	private Date dateOfBirth;	

	
	private boolean isActive;

	

}
