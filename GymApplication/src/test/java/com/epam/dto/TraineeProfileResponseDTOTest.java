package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.response.TraineeProfileResponseDTO;
import com.epam.dto.response.TrainerListResponseDTO;

@ExtendWith(MockitoExtension.class)
class TraineeProfileResponseDTOTest {

    @Test
    public void testBuilder() {
        List<TrainerListResponseDTO> trainers = new ArrayList<>();
        trainers.add(TrainerListResponseDTO.builder()
            .userName("trainer1")
            .firstName("Trainer")
            .lastName("One")
            .build());

        TraineeProfileResponseDTO profile = TraineeProfileResponseDTO.builder()
            .firstName("John")
            .lastName("Doe")
            .dateofBirth(new Date())
            .address("123 Main St")
            .isActive(true)
            .listOfTrainers(trainers)
            .build();

        assertEquals("John", profile.getFirstName());
        assertEquals("Doe", profile.getLastName());
        assertNotNull(profile.getDateofBirth());
        assertEquals("123 Main St", profile.getAddress());
        assertEquals(true, profile.isActive());
        assertEquals(1, profile.getListOfTrainers().size());
    }

    @Test
    public void testNoArgsConstructor() {
        TraineeProfileResponseDTO profile = new TraineeProfileResponseDTO();
        assertNotNull(profile);
    }

    @Test
     void testAllArgsConstructor() {
        Date dob = new Date();
        List<TrainerListResponseDTO> trainers = new ArrayList<>();
        trainers.add(TrainerListResponseDTO.builder()
            .userName("trainer1")
            .firstName("Trainer")
            .lastName("One")
            .build());

        TraineeProfileResponseDTO profile = new TraineeProfileResponseDTO("John", "Doe", dob, "123 Main St", true, trainers);

        assertEquals("John", profile.getFirstName());
        assertEquals("Doe", profile.getLastName());
        assertEquals(dob, profile.getDateofBirth());
        assertEquals("123 Main St", profile.getAddress());
        assertEquals(true, profile.isActive());
        assertEquals(1, profile.getListOfTrainers().size());
    }

}
