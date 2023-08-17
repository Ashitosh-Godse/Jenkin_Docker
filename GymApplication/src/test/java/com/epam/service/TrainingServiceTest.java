package com.epam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;

import com.epam.customexception.GymException;
import com.epam.dto.request.TrainingRequestDTO;
import com.epam.dto.response.TrainerListResponseDTO;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.model.TrainingType;
import com.epam.model.User;
import com.epam.repository.TraineeRepo;
import com.epam.repository.TrainerRepo;
import com.epam.repository.TrainingRepo;
import com.epam.repository.UserRepo;

@ExtendWith(MockitoExtension.class)
class TrainingServiceTest {
	
	@Mock
	private TraineeRepo traineeRepo;
	
	@Mock
	private UserRepo userRepo;
	
	@Mock
	private TrainerRepo trainerRepo;
	
	@Mock
	private TrainingRepo trainingRepo;
	
	@Mock
	private KafkaProducer kafkaProducer;
	
	@InjectMocks
	private TrainingServiceImpl trainingServiceImpl;
	
	TrainingRequestDTO trainingRequestDTO;
	
	Trainee trainee;
	
	Trainer trainer;
	
	private String traineeUsername = "traineeA";
    private String trainerUsername = "trainerB";
	
	@BeforeEach
	void initializeData() {
		             trainingRequestDTO = TrainingRequestDTO.builder()
	                .traineeUsername(traineeUsername)
	                .trainerUsername(trainerUsername)
	                .date(new Date())
	                .duration(60)
	                .trainingName("Strength Training")
	                .trainingType("Fitness")
	                .build();
		             
		             List<Trainer>listOfTrainers=new ArrayList<>();
		             List<Trainee>listOfTrainees=new ArrayList<>();
		             
		             User userTrainee=User.builder()
		             		        .userName(traineeUsername)
		             		        .firstName("ashu")
		             		        .lastName("godse")
		             		        .email("ashu@gmail.com")
		             		        .isActive(true)
		             				.build();
		              trainee =  Trainee.builder()
		             		           .user(userTrainee)
		             		           .adddress("pune")
		             		           .dateOfBirth(new Date())
		             		           .build();
		             userTrainee.setTrainee(trainee);
		             User userTrainer=User.builder()
		     		        .userName(trainerUsername)
		     		        .firstName("ashu")
		     		        .lastName("godse")
		     		        .email("trainer@example.com")
		     		        .isActive(true)
		     				.build();
		             trainer = Trainer.builder().user(userTrainer).build();
		             userTrainer.setTrainer(trainer);
		             
		             listOfTrainees.add(trainee);
		             listOfTrainers.add(trainer);
		             trainee.setListOfTrainers(listOfTrainers);
		             trainer.setListOfTrainees(listOfTrainees);
	}
	
	@Test
    void testAddTraining() throws Exception {
       

        when(traineeRepo.findByUserUserName(traineeUsername)).thenReturn(Optional.of(trainee));
        when(trainerRepo.findByUserUserName(trainerUsername)).thenReturn(Optional.of(trainer));

   
        trainingServiceImpl.addTraining(trainingRequestDTO);

        verify(traineeRepo, times(1)).findByUserUserName(traineeUsername);
        verify(trainerRepo, times(1)).findByUserUserName(trainerUsername);
        verify(kafkaProducer, times(1)).sendMail(any(Message.class));
        verify(trainingRepo, times(1)).save(any(Training.class));
        verify(kafkaProducer, times(1)).sendReport(any(Message.class));
    }
	
	@Test
	void testAddTrainingWithInvalidTraineeName() {
        when(traineeRepo.findByUserUserName(traineeUsername)).thenReturn(Optional.empty());
        assertThrows(GymException.class, ()->trainingServiceImpl.addTraining(trainingRequestDTO));

	}
	@Test
	void testAddTrainingWithInvalidTrainerName() {
		String traineeUsername = "traineeA";
        String trainerUsername = "trainerB";

        when(traineeRepo.findByUserUserName(traineeUsername)).thenReturn(Optional.of(trainee));
        when(trainerRepo.findByUserUserName(trainerUsername)).thenReturn(Optional.empty());
        
        assertThrows(GymException.class, ()->trainingServiceImpl.addTraining(trainingRequestDTO));

	}
	
	 @Test
	 void testUnassignedTrainers() throws Exception {
	        Trainee trainee = Trainee.builder().user(new User()).build();
	        User userTrainer = User.builder().id(1)
	        		.firstName("ashu")
	        		.lastName("godse")
	        		.userName("trainer1").build();
			TrainingType trainingType = TrainingType.builder().trainingTypeName("Fitness").build();
			
	        Trainer trainer1 = Trainer.builder().trainingType(trainingType).user(userTrainer).listOfTrainees(new ArrayList<>()).build();
	        
	        userTrainer.setTrainer(trainer1);
	        
	        Trainer trainer2 = Trainer.builder().trainingType(trainingType).user(new User()).listOfTrainees(Collections.singletonList(trainee)).build();
	        
	        List<Trainer> allTrainers = List.of(trainer1, trainer2);

	        when(traineeRepo.findByUserUserName(anyString())).thenReturn(Optional.of(trainee));
	        when(trainerRepo.findAll()).thenReturn(allTrainers);
	        when(userRepo.findById(1)).thenReturn(Optional.of(userTrainer));

	        List<TrainerListResponseDTO> result = trainingServiceImpl.unassignedTrainers("traineeUsername");

	        assertEquals(1, result.size());
	        assertEquals("trainer1", result.get(0).getUserName());

	        verify(traineeRepo, times(1)).findByUserUserName(eq("traineeUsername"));
	        verify(trainerRepo, times(1)).findAll();
	        verify(userRepo, times(1)).findById(eq(1));
	    }
	 
	 @Test
	 void testUnassignedTrainersWithInvalidUserName() throws Exception {
	        when(traineeRepo.findByUserUserName(anyString())).thenReturn(Optional.empty());
	        assertThrows(GymException.class,()-> trainingServiceImpl.unassignedTrainers(anyString()));
	    }

}
