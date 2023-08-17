package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.request.ReportRequestDTO;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

@ExtendWith(MockitoExtension.class)
 class TestReportRequestDTO {
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	@Test
     void testNoArgsConstructor() {
        ReportRequestDTO reportRequestDTO = new ReportRequestDTO();
        assertNotNull(reportRequestDTO);
    }

    @Test
     void testAllArgsConstructor() {
        ReportRequestDTO reportRequestDTO = new ReportRequestDTO(
                "john_doe", "John", "Doe", true, 60, new Date());
        assertNotNull(reportRequestDTO);
        assertEquals("john_doe", reportRequestDTO.getTrainerUserName());
        assertEquals("John", reportRequestDTO.getTrainerFirstName());
        assertEquals("Doe", reportRequestDTO.getTrainerLastName());
        assertTrue(reportRequestDTO.isStatus());
        assertEquals(60, reportRequestDTO.getDuration());
        assertNotNull(reportRequestDTO.getStartDate());
    }

    @Test
     void testBuilder() {
        ReportRequestDTO reportRequestDTO = ReportRequestDTO.builder()
                .trainerUserName("john_doe")
                .trainerFirstName("John")
                .trainerLastName("Doe")
                .status(true)
                .duration(60)
                .startDate(new Date())
                .build();

        assertNotNull(reportRequestDTO);
        assertEquals("john_doe", reportRequestDTO.getTrainerUserName());
        assertEquals("John", reportRequestDTO.getTrainerFirstName());
        assertEquals("Doe", reportRequestDTO.getTrainerLastName());
        assertTrue(reportRequestDTO.isStatus());
        assertEquals(60, reportRequestDTO.getDuration());
        assertNotNull(reportRequestDTO.getStartDate());
    }
}
