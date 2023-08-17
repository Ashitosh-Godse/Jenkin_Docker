package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.response.TraineeListResponseDTO;
@ExtendWith(MockitoExtension.class)
class TraineeListResponseDTOTest {

    @Test
     void testBuilder() {
        TraineeListResponseDTO trainee = TraineeListResponseDTO.builder()
            .userName("john_doe")
            .firstName("John")
            .lastName("Doe")
            .build();

        assertEquals("john_doe", trainee.getUserName());
        assertEquals("John", trainee.getFirstName());
        assertEquals("Doe", trainee.getLastName());
    }

    @Test
   void testNoArgsConstructor() {
        TraineeListResponseDTO trainee = new TraineeListResponseDTO();
        assertNotNull(trainee);
    }

    @Test
   void testAllArgsConstructor() {
        TraineeListResponseDTO trainee = new TraineeListResponseDTO("john_doe", "John", "Doe");
        assertEquals("john_doe", trainee.getUserName());
        assertEquals("John", trainee.getFirstName());
        assertEquals("Doe", trainee.getLastName());
    }

}