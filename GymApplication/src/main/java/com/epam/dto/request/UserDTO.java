package com.epam.dto.request;

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
public class UserDTO {
	@NotBlank(message="userName cannot be blank or null")
	private String userName;
	@NotBlank(message="firstName cannot be blank or null")
	private String firstName;
	@NotBlank(message="lastName cannot be blank or null")
	private String lastName;
	private boolean isActive;
	
}
