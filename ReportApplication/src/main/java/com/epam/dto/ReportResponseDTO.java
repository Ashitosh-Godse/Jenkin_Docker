package com.epam.dto;

import java.util.Map;

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
public class ReportResponseDTO {
	
	private String firstName;
	private String lastName;
	private String userName;
	private boolean isActive;
	private Map<Integer,Map<Integer,Map<Integer,Integer>>> summary;

}
