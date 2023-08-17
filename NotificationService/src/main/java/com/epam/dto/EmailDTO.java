package com.epam.dto;

import java.util.List;

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
public class EmailDTO {
	private String emailTo;
	private List<String> cc;
	private List<String> bcc;
	private String subject;
	private String body;
	
	

}
