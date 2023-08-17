package com.epam.dto.request;

import java.util.List;
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
public class MailRequestDTO {
	
    private String toMail;
	private List<String> ccMails;
	private List<String> bccMails;
	private Map<String,String> details;

	
}
