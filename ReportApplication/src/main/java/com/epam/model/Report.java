package com.epam.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document("report")
public class Report {
	@Id
	private String userName;
	
	private String firstName;
	private String lastName;
	private boolean status;
	private Map<Integer,Map<Integer,Map<Integer,Integer>>> summary;
	

}
