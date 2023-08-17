package com.epam.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.response.TraineeResponseDTO;
import com.epam.dto.response.TrainerListResponseDTO;

@ExtendWith(MockitoExtension.class)
class TraineeResponseDTOTest {
	@Test
	void testBuilder() {
		List<TrainerListResponseDTO> trainers = new ArrayList<>();
		trainers.add(
				TrainerListResponseDTO.builder().userName("trainer1").firstName("Trainer").lastName("One").build());

		TraineeResponseDTO response = TraineeResponseDTO.builder().username("john_doe").firstName("John")
				.lastName("Doe").dateofBirth(new Date()).address("123 Main St").isActive(true).listOfTrainers(trainers)
				.build();

		assertEquals("john_doe", response.getUsername());
		assertEquals("John", response.getFirstName());
		assertEquals("Doe", response.getLastName());
		assertNotNull(response.getDateofBirth());
		assertEquals("123 Main St", response.getAddress());
		assertEquals(true, response.isActive());
		assertEquals(1, response.getListOfTrainers().size());
	}

	@Test
	void testNoArgsConstructor() {
		TraineeResponseDTO response = new TraineeResponseDTO();
		assertNotNull(response);
	}

	@Test
	void testAllArgsConstructor() {
		Date dob = new Date();
		List<TrainerListResponseDTO> trainers = new ArrayList<>();
		trainers.add(
				TrainerListResponseDTO.builder().userName("trainer1").firstName("Trainer").lastName("One").build());

		TraineeResponseDTO response = new TraineeResponseDTO("john_doe", "John", "Doe", dob, "123 Main St", true,
				trainers);

		assertEquals("john_doe", response.getUsername());
		assertEquals("John", response.getFirstName());
		assertEquals("Doe", response.getLastName());
		assertEquals(dob, response.getDateofBirth());
		assertEquals("123 Main St", response.getAddress());
		assertEquals(true, response.isActive());
		assertEquals(1, response.getListOfTrainers().size());
	}

}
