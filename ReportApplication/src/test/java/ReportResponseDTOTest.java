import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.ReportResponseDTO;

@ExtendWith(MockitoExtension.class)
 class ReportResponseDTOTest {
	 @Test
	     void testNoArgsConstructor() {
	        ReportResponseDTO reportResponseDTO = new ReportResponseDTO();
	        assertNotNull(reportResponseDTO);
	    }

	    @Test
	     void testAllArgsConstructor() {
	        Map<Integer, Map<Integer, Map<Integer, Integer>>> summary = new HashMap<>();
	        ReportResponseDTO reportResponseDTO = new ReportResponseDTO("John", "Doe", "john_doe", true, summary);

	        assertNotNull(reportResponseDTO);
	        assertEquals("John", reportResponseDTO.getFirstName());
	        assertEquals("Doe", reportResponseDTO.getLastName());
	        assertEquals("john_doe", reportResponseDTO.getUserName());
	        assertTrue(reportResponseDTO.isActive());
	        assertEquals(summary, reportResponseDTO.getSummary());
	    }

	    @Test
	     void testBuilder() {
	        Map<Integer, Map<Integer, Map<Integer, Integer>>> summary = new HashMap<>();
	        ReportResponseDTO reportResponseDTO = ReportResponseDTO.builder()
	                .firstName("John")
	                .lastName("Doe")
	                .userName("john_doe")
	                .isActive(true)
	                .summary(summary)
	                .build();

	        assertNotNull(reportResponseDTO);
	        assertEquals("John", reportResponseDTO.getFirstName());
	        assertEquals("Doe", reportResponseDTO.getLastName());
	        assertEquals("john_doe", reportResponseDTO.getUserName());
	        assertTrue(reportResponseDTO.isActive());
	        assertEquals(summary, reportResponseDTO.getSummary());
	    }

	    @Test
	     void testGetterAndSetterAnnotations() {
	        Map<Integer, Map<Integer, Map<Integer, Integer>>> summary = new HashMap<>();
	        ReportResponseDTO reportResponseDTO = new ReportResponseDTO();
	        reportResponseDTO.setFirstName("John");
	        reportResponseDTO.setLastName("Doe");
	        reportResponseDTO.setUserName("john_doe");
	        reportResponseDTO.setActive(true);
	        reportResponseDTO.setSummary(summary);

	        assertEquals("John", reportResponseDTO.getFirstName());
	        assertEquals("Doe", reportResponseDTO.getLastName());
	        assertEquals("john_doe", reportResponseDTO.getUserName());
	        assertTrue(reportResponseDTO.isActive());
	        assertEquals(summary, reportResponseDTO.getSummary());
	    }
}
