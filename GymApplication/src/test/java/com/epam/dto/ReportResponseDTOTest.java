package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.response.ReportResponseDTO;

@ExtendWith(MockitoExtension.class)
class ReportResponseDTOTest {

    @Test
   void testBuilder() {
    	Map<Integer,Map<Integer,Map<Integer,Integer>>> details=new HashMap<>();
        ReportResponseDTO report = ReportResponseDTO.builder()
            .firstName("John")
            .lastName("Doe")
            .userName("john_doe")
            .isActive(true)
            .summary(details)
            .build();

        assertEquals("John", report.getFirstName());
        assertEquals("Doe", report.getLastName());
        assertEquals("john_doe", report.getUserName());
        assertTrue(report.isActive());
        assertNotNull(report.getSummary());
    }

    @Test
   void testNoArgsConstructor() {
        ReportResponseDTO report = new ReportResponseDTO();
        assertNotNull(report);
    }

    @Test
   void testAllArgsConstructor() {
    	Map<Integer,Map<Integer,Map<Integer,Integer>>> details=new HashMap<>();
        ReportResponseDTO report = new ReportResponseDTO("John", "Doe", "john_doe", true,details);
        assertEquals("John", report.getFirstName());
        assertEquals("Doe", report.getLastName());
        assertEquals("john_doe", report.getUserName());
        assertTrue(report.isActive());
        assertNotNull(report.getSummary());
    }

}