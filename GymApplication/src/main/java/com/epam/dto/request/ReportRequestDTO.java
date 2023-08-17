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
public class ReportRequestDTO {
	@NotBlank(message="trainerUserName cannot be blank or null")
	private String trainerUserName;
	
	@NotBlank(message="trainerFirstName cannot be blank or null")
	private String trainerFirstName;
	
	@NotBlank(message="trainerLastName cannot be blank or null")
	private String trainerLastName;
	
	private boolean status;
	
	private int duration;
	
	private Date startDate;
}
