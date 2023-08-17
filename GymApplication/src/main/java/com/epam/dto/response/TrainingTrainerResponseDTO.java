package com.epam.dto.response;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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
public class TrainingTrainerResponseDTO {
	private String  trainingName;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	private String type;
	private int duration;
	private String traineeName;
}
