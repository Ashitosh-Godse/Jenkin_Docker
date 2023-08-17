package com.epam.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReportTest {
	@Test
     void testAllArgsConstructor() {
        Map<Integer, Map<Integer, Map<Integer, Integer>>> summary = new HashMap<>();
        Report report = new Report("john_doe", "John", "Doe", true, summary);

        assertNotNull(report);
        assertEquals("john_doe", report.getUserName());
        assertEquals("John", report.getFirstName());
        assertEquals("Doe", report.getLastName());
        assertTrue(report.isStatus());
        assertEquals(summary, report.getSummary());
    }

    @Test
    void testNoArgsConstructor() {
        Report report = new Report();
        assertNotNull(report);
    }

    @Test
     void testGetterAndSetterAnnotations() {
        Report report = new Report();
        report.setUserName("john_doe");
        report.setFirstName("John");
        report.setLastName("Doe");
        report.setStatus(true);
        Map<Integer, Map<Integer, Map<Integer, Integer>>> summary = new HashMap<>();
        report.setSummary(summary);

        assertEquals("john_doe", report.getUserName());
        assertEquals("John", report.getFirstName());
        assertEquals("Doe", report.getLastName());
        assertTrue(report.isStatus());
        assertEquals(summary, report.getSummary());
    }

    @Test
    void testBuilderAnnotation() {
        Map<Integer, Map<Integer, Map<Integer, Integer>>> summary = new HashMap<>();
        Report report = Report.builder()
                .userName("john_doe")
                .firstName("John")
                .lastName("Doe")
                .status(true)
                .summary(summary)
                .build();

        assertNotNull(report);
        assertEquals("john_doe", report.getUserName());
        assertEquals("John", report.getFirstName());
        assertEquals("Doe", report.getLastName());
        assertTrue(report.isStatus());
        assertEquals(summary, report.getSummary());
    }
}
