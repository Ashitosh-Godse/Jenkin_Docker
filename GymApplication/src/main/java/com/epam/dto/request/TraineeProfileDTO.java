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
public class TraineeProfileDTO {
	
	@NotBlank(message="firstName cannot be blank or null")
	private String firstName;
	
	@NotBlank(message="lastName cannot be blank or null")
	private String lastName;
	
	private Date dateOfBirth;	
	
	@NotBlank(message="adddress cannot be blank or null")
	private String adddress;
	
	@NotBlank(message="email cannot be blank or null")
	private String email;
	
	

}
