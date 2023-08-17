package com.epam.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@AllArgsConstructor
@Builder
@Getter
public class TrainerProfileDTO {
	@NotBlank(message = "firstName cannot be blank or null")
	private String firstName;
	
	@NotBlank(message="lastName cannot be blank or null")
	private String lastName;
	
	@NotBlank(message="email cannot be blank or null")
	private String email;
	
	@NotBlank(message="trainingtype cannot be blank or null")
	private String trainingtypeName;

}
