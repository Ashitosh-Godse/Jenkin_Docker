package com.epam.dto;

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
public class UserDTO {
	
	@NotBlank(message="userName cannot be black or null")
	private String userName;
	@NotBlank(message="firstName cannot be black or null")
	private String firstName;
	@NotBlank(message="lastName cannot be black or null")
	private String lastName;
	@NotBlank(message="isActive cannot be black or null")
	private boolean isActive;
	
}
