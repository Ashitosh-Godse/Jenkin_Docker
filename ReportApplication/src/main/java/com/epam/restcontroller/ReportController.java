package com.epam.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.customexception.ReportException;
import com.epam.dto.ReportResponseDTO;
import com.epam.dto.request.ReportRequestDTO;
import com.epam.service.ReportServiceImpl;


@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired
	ReportServiceImpl reportSericeImpl;
	
	@PostMapping
	public ResponseEntity<Void> addReport(@RequestBody ReportRequestDTO reportRequestDTO){
		reportSericeImpl.addTrainingReport(reportRequestDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{userName}")
	public ResponseEntity<ReportResponseDTO> getReport(@PathVariable String userName) throws ReportException{
		return new ResponseEntity<>(reportSericeImpl.getReport(userName),HttpStatus.OK);
		
	}
	
	
	
}
