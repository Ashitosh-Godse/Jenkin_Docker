package com.epam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.customexception.ReportException;
import com.epam.dto.ReportResponseDTO;
import com.epam.dto.request.ReportRequestDTO;
import com.epam.model.Report;
import com.epam.repository.ReportRepo;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {
	
	    @Mock
	    private ReportRepo reportRepo;

	    @InjectMocks
	    private ReportServiceImpl reportService;

	   

	    @Test
	    void testGetReport_ValidUserName() throws ReportException {
	        String userName = "john_doe";
	        Report report = Report.builder()
	                .userName("john_doe")
	                .firstName("John")
	                .lastName("Doe")
	                .status(true)
	                .summary(new HashMap<>())
	                .build();

	        Map<Integer, Map<Integer, Map<Integer, Integer>>> summaryMap = new HashMap<>();
	        Map<Integer, Map<Integer, Integer>> yearMap = new HashMap<>();
	        Map<Integer, Integer> monthMap = new HashMap<>();
	        monthMap.put(1, 60);
	        yearMap.put(2023, monthMap);
	        summaryMap.put(1, yearMap);

	        report.setSummary(summaryMap);

	        when(reportRepo.findById(userName)).thenReturn(java.util.Optional.of(report));

	        ReportResponseDTO responseDTO = reportService.getReport(userName);

	        assertEquals(userName, responseDTO.getUserName());
	        assertEquals(report.getFirstName(), responseDTO.getFirstName());
	        assertEquals(report.getLastName(), responseDTO.getLastName());
	        assertEquals(report.isStatus(), responseDTO.isActive());
	        assertEquals(report.getSummary(), responseDTO.getSummary());

	    }

	    @Test
	   void testGetReport_InvalidUserName() {
	        String invalidUserName = "non_existent_user";

	        when(reportRepo.findById(invalidUserName)).thenReturn(java.util.Optional.empty());

	        assertThrows(ReportException.class, () -> reportService.getReport(invalidUserName));

	    }

	    @Test
	     void testAddTrainingReport() {
	    	ReportRequestDTO reportRequestDTO = ReportRequestDTO.builder()
	                .trainerUserName("john_doe")
	                .trainerFirstName("John")
	                .trainerLastName("Doe")
	                .status(true)
	                .duration(60)
	                .startDate(new Date())
	                .build();
	        
	        Report reportFromRepo = Report.builder()
	                .userName("john_doe")
	                .firstName("John")
	                .lastName("Doe")
	                .status(true)
	                .summary(new HashMap<>())
	                .build();
	        
	        when(reportRepo.findById(reportRequestDTO.getTrainerUserName())).thenReturn(Optional.ofNullable(null));
	        when(reportRepo.save(any(Report.class))).thenReturn(reportFromRepo);

	        reportService.addTrainingReport(reportRequestDTO);

	        // Assertions or verifications based on expected behaviors
	        verify(reportRepo, times(1)).findById(reportRequestDTO.getTrainerUserName());
	        verify(reportRepo, times(1)).save(any(Report.class));
	    }

	  
	}
	




	    


