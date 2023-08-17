package com.epam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.customexception.GymException;
import com.epam.dto.request.TrainerProfileDTO;
import com.epam.dto.request.TrainerRequestDTO;
import com.epam.dto.request.TrainerRequestTrainingDTO;
import com.epam.dto.response.CredentialsDTO;
import com.epam.dto.response.TraineeListResponseDTO;
import com.epam.dto.response.TrainerProfileResponseDTO;
import com.epam.dto.response.TrainerResponseDTO;
import com.epam.dto.response.TrainingTrainerResponseDTO;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.model.TrainingType;
import com.epam.model.User;
import com.epam.repository.TrainerRepo;
import com.epam.repository.TrainingRepo;
import com.epam.repository.TrainingTypeRepo;
import com.epam.repository.UserRepo;

@ExtendWith(MockitoExtension.class)

class TrainerServiceTest {
	@Mock
	private TrainerRepo trainerRepo;

	@Mock
	private UserRepo userRepo;

	@Mock
	private KafkaProducer kafkaProducer;

	@Mock
	private UserServiceImpl userService;

	@Mock
	private TrainingRepo trainingRepo;

	@Mock
	private TrainingTypeRepo trainingTypeRepo;

	@InjectMocks
	private TrainerServiceImpl trainerServiceimpl;

	@Test
	void testRegisterTrainer() throws Exception {
		String email = "trainer@example.com";
		String firstName = "John";
		String lastName = "Doe";
		String trainingType = "Strength";

		TrainerProfileDTO trainerProfileDTO = TrainerProfileDTO.builder().email(email).firstName(firstName)
				.lastName(lastName).trainingtypeName(trainingType).build();

		when(userRepo.existsByUserName(email)).thenReturn(false);

		User user = User.builder().userName("trainer").firstName(firstName).lastName(lastName).isActive(true)
				.email(email).password("generated_password").build();
		when(userService.generatePassword()).thenReturn("generated_password");
		when(userRepo.save(any(User.class))).thenReturn(user);

		TrainingType trainingTypeObj = TrainingType.builder()

				.trainingTypeName(trainingType).build();
		when(trainingTypeRepo.findByTrainingTypeName(trainingType)).thenReturn(Optional.of(trainingTypeObj));

		Trainer trainer = Trainer.builder().user(user).trainingType(trainingTypeObj).build();
		when(trainerRepo.save(any(Trainer.class))).thenReturn(trainer);


		CredentialsDTO credentialsDTO = CredentialsDTO.builder().username(user.getUserName())
				.password(user.getPassword()).build();

		CredentialsDTO result = trainerServiceimpl.registerTrainer(trainerProfileDTO);

		assertNotNull(result);
		assertEquals(credentialsDTO.getUsername(), result.getUsername());
		assertEquals(credentialsDTO.getPassword(), result.getPassword());

	}
	
	@Test
	void testRegisterTrainerWithInvalidTrainingType() throws Exception {
		String email = "trainer@example.com";
		String firstName = "John";
		String lastName = "Doe";
		String trainingType = "Strength";

		TrainerProfileDTO trainerProfileDTO = TrainerProfileDTO.builder().email(email).firstName(firstName)
				.lastName(lastName).trainingtypeName(trainingType).build();

		when(userRepo.existsByUserName(email)).thenReturn(false);

		User user = User.builder().userName("trainer").firstName(firstName).lastName(lastName).isActive(true)
				.email(email).password("generated_password").build();
		when(userService.generatePassword()).thenReturn("generated_password");
		when(userRepo.save(any(User.class))).thenReturn(user);

		TrainingType trainingTypeObj = TrainingType.builder()

		.trainingTypeName(trainingType).build();
		when(trainingTypeRepo.findByTrainingTypeName(trainingType)).thenReturn(Optional.empty());

		


		 assertThrows(GymException.class, ()->trainerServiceimpl.registerTrainer(trainerProfileDTO)); 

	

	}
	 @Test
	  void testRegisterTraineeUsernameExists() throws Exception {


			TrainerProfileDTO trainerProfileDTO = TrainerProfileDTO.builder().email("trainer@example.com").firstName("John")
					.lastName("John").trainingtypeName("Strength").build();

	        when(userRepo.existsByUserName(trainerProfileDTO.getEmail())).thenReturn(true);

	 

	        
	        assertThrowsExactly(GymException.class, ()->trainerServiceimpl.registerTrainer(trainerProfileDTO));
	    }
	@Test
	void testGetTrainerProfile() throws Exception {
		String username = "trainer1";

		User user = User.builder().userName("user").firstName("ashu").lastName("godse").isActive(true).build();
		TrainingType trainingType = TrainingType.builder().trainingTypeName("Fitness").build();
		Trainer trainer = Trainer.builder().user(user).trainingType(trainingType).build();
		when(trainerRepo.findByUserUserName(username)).thenReturn(Optional.of(trainer));

		List<Trainee> trainees = new ArrayList<>(); // Set up trainees if needed
		trainer.setListOfTrainees(trainees);

		TrainerProfileResponseDTO result = trainerServiceimpl.getTrainerProfile(username);

		assertNotNull(result);

	}

	@Test
	void testGetTrainerProfileForInvalidUserName() {

		Mockito.when(trainerRepo.findByUserUserName(anyString())).thenReturn(Optional.empty());
		assertThrows(GymException.class, () -> trainerServiceimpl.getTrainerProfile(anyString()));

	}
	
	 @Test
	 void testGetTrainerProfileWithTrainees() throws Exception {
	        String username = "trainerA";
	        
	        TrainerProfileResponseDTO expectedResponse = TrainerProfileResponseDTO.builder()
	                .firstName("John")
	                .lastName("Doe")
	                .listOfTrainees(List.of(
	                    TraineeListResponseDTO.builder().userName("trainee1").build(),
	                    TraineeListResponseDTO.builder().userName("trainee2").build()
	                ))
	                .build();

	        User trainerUser = User.builder().id(1).userName(username).build();
	        TrainingType trainingType=TrainingType.builder().trainingTypeName("Fitness").build();

	        Trainer trainer = Trainer.builder()
	                         .user(trainerUser)
	                         .trainingType(trainingType)
	                         .listOfTrainees(List.of(
	                          Trainee.builder().user(User.builder().id(2).userName("trainee1").build()).build(),
	                          Trainee.builder().user(User.builder().id(3).userName("trainee2").build()).build()
	                ))
	                .build();

	        when(trainerRepo.findByUserUserName(username)).thenReturn(Optional.of(trainer));
	        when(userRepo.findById(2)).thenReturn(Optional.of(User.builder().userName("trainee1").build()));
	        when(userRepo.findById(3)).thenReturn(Optional.of(User.builder().userName("trainee2").build()));

	
	        
	        TrainerProfileResponseDTO result = trainerServiceimpl.getTrainerProfile(username);

	        assertNotNull(result);
	
	      
	    }
	 
	 @Test
	 void testUpdateProfile() throws Exception {
	        String username = "trainer1";
	        String specialization = "Strength Training";

	        TrainerRequestDTO trainerRequestDTO = TrainerRequestDTO.builder()
	                .userName(username)
	                .firstName("John")
	                .lastName("Doe")
	                .isActive(true)
	                .specialization(specialization)
	                .build();

	        User user = User.builder()
	              
	                .userName(username)
	                .firstName("John")
	                .lastName("Doe")
	                .email("john.doe@example.com")
	                .build();
	        Trainer trainer = Trainer.builder()
	                .user(user)
	                .trainingType(TrainingType.builder().trainingTypeName(specialization).build())
	                .build();
	        user.setTrainer(trainer);
	        List<Trainee> trainees = new ArrayList<>(); // Set up trainers if needed
	         
	        trainer.setListOfTrainees(trainees);

	        when(userRepo.findByUserName(username)).thenReturn(Optional.of(user));
	        when(trainerRepo.findByUserId(user.getId())).thenReturn(Optional.of(trainer));
	        when(trainerRepo.save(any(Trainer.class))).thenReturn(trainer);

	        
	        TrainerResponseDTO result = trainerServiceimpl.updateProfile(trainerRequestDTO);

	        assertNotNull(result);
	        assertEquals(username, result.getUserName());
	        assertEquals("John", result.getFirstName());
	        assertEquals("Doe", result.getLastName());
	        assertTrue(result.isActive());
	        assertEquals(specialization, result.getSpecialization());
	   
	    }
	 
	 @Test
	 void testUpdateProfileWithTrainees() throws Exception {
	        String username = "trainerA";
	        
	        TrainerResponseDTO expectedResponse = TrainerResponseDTO.builder()
	                .userName("trainerA")
	                .firstName("John")
	                .lastName("Doe")
	                .isActive(true)
	                .specialization("Fitness")
	                .listOfTrainees(List.of(
	                    TraineeListResponseDTO.builder().userName("trainee1").build(),
	                    TraineeListResponseDTO.builder().userName("trainee2").build()
	                ))
	                .build();

	        User trainerUser = User.builder().id(1).userName(username).email("ashu@123").build();
	        List<Trainee>list=new ArrayList<>();
	        User userTrainee1=User.builder().id(2).userName("trainee1").email("ashu@123").build();
	        User userTrainee2=User.builder().id(3).userName("trainee2").email("ashu@123").build();
	        Trainee trainee1=Trainee.builder().user(userTrainee1).build();
	        Trainee trainer2=Trainee.builder().user(userTrainee2).build();
	        userTrainee1.setTrainee(trainee1);
	        userTrainee2.setTrainee(trainer2);

	        list.add(trainee1);
	        list.add(trainer2);
	        
	        Trainer trainer = Trainer.builder()
	                .user(trainerUser)
	                .trainingType(TrainingType.builder().trainingTypeName("Fitness").build())
	                .listOfTrainees(list)
	                .build();
	   
	        trainerUser.setTrainer(trainer);

	        when(userRepo.findByUserName(username)).thenReturn(Optional.of(trainerUser));
	        when(trainerRepo.findByUserId(trainerUser.getId())).thenReturn(Optional.of(trainer));
	        when(userRepo.findById(2)).thenReturn(Optional.of(userTrainee1));
	        when(userRepo.findById(3)).thenReturn(Optional.of(userTrainee2));


	        TrainerResponseDTO result= trainerServiceimpl.updateProfile(
	                                   TrainerRequestDTO.builder()
	                                   .userName(username)
	                                   .firstName("John")
	                                   .lastName("Doe")
	                                   .isActive(true)
	                                   .specialization("Fitness")
	                                   .build()
	        );

	        assertNotNull(result);

	        verify(userRepo, times(1)).findByUserName(username);
	        verify(trainerRepo, times(1)).findByUserId(trainerUser.getId());
	        verify(userRepo, times(2)).findById(anyInt()); 
	    }
	 
	 @Test
	 void testUpdateProfileWithInvalidUserName() throws Exception {
	        String username = "trainer1";
	        String specialization = "Strength Training";

	        TrainerRequestDTO trainerRequestDTO = TrainerRequestDTO.builder()
	                .userName(username)
	                .firstName("John")
	                .lastName("Doe")
	                .isActive(true)
	                .specialization(specialization)
	                .build();

	        User user = User.builder()
	              
	                .userName(username)  
	                .firstName("John")
	                .lastName("Doe")
	                .email("john.doe@example.com")
	                .build();
	        Trainer trainer = Trainer.builder()
	                .user(user)
	                .trainingType(TrainingType.builder().trainingTypeName(specialization).build())
	                .build();
	        user.setTrainer(trainer);
	        List<Trainee> trainees = new ArrayList<>();
	         
	        trainer.setListOfTrainees(trainees);

	        when(userRepo.findByUserName(username)).thenReturn(Optional.empty());
	        
	        
	        assertThrows(GymException.class, ()->trainerServiceimpl.updateProfile(trainerRequestDTO));

	      
	    }
	 
	 @Test
	 void testGetTrainingList() throws Exception {
	        String username = "trainer1";

	      
	        TrainerRequestTrainingDTO trainerRequestTrainingDTO = TrainerRequestTrainingDTO.builder()
	                .userName("trainer1")
	                .periodFrom(new Date())
	                .periodTo(new Date())
	                .traineeName("traineeA")
	                .build();

            List<Training>list=new ArrayList<>();
            List<Trainee>listOfTrainees=new ArrayList<>();
	        Trainer trainer = Trainer.builder()
	        		         .trainingType(TrainingType.builder().trainingTypeName("Fitness").build())
	                         .user(User.builder().userName(username).build())
	                         .listOfTrainees(listOfTrainees)
	                         
	                         .build();

	        Training training = Training.builder()       
	                .name("Training 1")
	                .date(new Date())
	                .duration(60)
	                .trainer(trainer)
	                .trainee(Trainee.builder().user(User.builder().userName("trainee1").build()).build())
	                .build();
	        list.add(training);
	        trainer.setTraining(list);
	        List<Training> trainings = Arrays.asList(training);

	        when(trainerRepo.findByUserUserName(username)).thenReturn(Optional.of(trainer));
	        when(trainingRepo.findTrainingsByTraineeId(trainer.getId())).thenReturn(trainings);


	        
	        List<TrainingTrainerResponseDTO> result = trainerServiceimpl.getTrainingList(trainerRequestTrainingDTO);

	        assertNotNull(result);
	        assertEquals(1, result.size());

	    }
	 
	   @Test
	    void testGetTrainingListForInValidUserName(){
		   TrainerRequestTrainingDTO trainerRequestTrainingDTO = TrainerRequestTrainingDTO.builder()
	                .userName("trainer1")
	                .periodFrom(new Date())
	                .periodTo(new Date())
	                .traineeName("traineeA")
	                .build();
				Mockito.when(trainerRepo.findByUserUserName(trainerRequestTrainingDTO.getUserName())).thenReturn(Optional.empty());
				assertThrows(GymException.class,()->trainerServiceimpl.getTrainingList(trainerRequestTrainingDTO));
				
			}
	 
	  @Test
	  void testGetTrainerTrainingDetails() throws Exception {
	        String username = "trainer1";

	        TrainerRequestTrainingDTO trainerRequestTrainingDTO = TrainerRequestTrainingDTO.builder()
	                .userName(username)
	                .periodFrom(new Date()) 
	                .periodTo(new Date())
	                .traineeName("traineeA")
	                .build();


            List<Training>list=new ArrayList<>();
            List<Trainee>listOfTrainees=new ArrayList<>();
	        Trainer trainer = Trainer.builder()
   		                     .trainingType(TrainingType.builder().trainingTypeName("Fitness").build())
                             .user(User.builder().userName(username).build())
                             .listOfTrainees(listOfTrainees)
                              .build();

	        Training training = Training.builder()
	                .name("Training 1")
	                .date(new Date())
	                .duration(60)
	                .trainer(trainer)
	                .trainingType(trainer.getTrainingType())
	                .trainee(Trainee.builder().user(User.builder().userName("traineeA").build()).build())
	                .build();
	        list.add(training);
	        trainer.setTraining(list);
	        List<Training> trainings = Arrays.asList(training);

	        when(trainerRepo.findByUserUserName(username)).thenReturn(Optional.of(trainer));
	        when(trainingRepo.findTrainingsForTrainer(username, trainerRequestTrainingDTO.getPeriodFrom(), 
	                                                  trainerRequestTrainingDTO.getPeriodTo(), 
	                                                  trainerRequestTrainingDTO.getTraineeName())).thenReturn(trainings);

	       
	        
	        List<TrainingTrainerResponseDTO> result = trainerServiceimpl.getTrainerTrainingDetails(trainerRequestTrainingDTO);

	        assertNotNull(result);
	        assertEquals(1, result.size());

	        verify(trainerRepo, times(1)).findByUserUserName(username);
	        verify(trainingRepo, times(1)).findTrainingsForTrainer(username, 
	            trainerRequestTrainingDTO.getPeriodFrom(), trainerRequestTrainingDTO.getPeriodTo(), 
	            trainerRequestTrainingDTO.getTraineeName());
	    }
	  
	  @Test
	  void testGetTrainerTrainingDetailsForInValidUserName(){
		   TrainerRequestTrainingDTO trainerRequestTrainingDTO = TrainerRequestTrainingDTO.builder()
	                .userName("trainer1")
	                .periodFrom(new Date())
	                .periodTo(new Date())
	                .traineeName("traineeA")
	                .build();
				Mockito.when(trainerRepo.findByUserUserName(trainerRequestTrainingDTO.getUserName())).thenReturn(Optional.empty());
				assertThrows(GymException.class,()->trainerServiceimpl.getTrainerTrainingDetails(trainerRequestTrainingDTO));
				
			}
	  
	


}
