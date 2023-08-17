package com.epam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import com.epam.dto.request.TraineeProfileDTO;
import com.epam.dto.request.TraineeRequestDTO;
import com.epam.dto.request.TraineeRequestTrainingDTO;
import com.epam.dto.response.CredentialsDTO;
import com.epam.dto.response.TraineeProfileResponseDTO;
import com.epam.dto.response.TraineeResponseDTO;
import com.epam.dto.response.TrainerListResponseDTO;
import com.epam.dto.response.TrainingTraineeResponseDTO;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.model.TrainingType;
import com.epam.model.User;
import com.epam.repository.TraineeRepo;
import com.epam.repository.TrainingRepo;
import com.epam.repository.UserRepo;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {
	@Mock
	private TraineeRepo traineeRepo;

	@Mock
	private UserRepo userRepo;
	
   
	@Mock
    private KafkaProducer kafkaProducer;

	@Mock
	private UserService userService;

	@Mock
	private TrainingRepo trainingRepo;
	
	@InjectMocks
	private TraineeServiceimpl traineeServiceimpl;
	
	
	

	
	
	
	@Test
   void testRegisterTrainee() throws Exception {
        TraineeProfileDTO traineeProfileDTO = TraineeProfileDTO.builder()
                .firstName("Alice")
                .lastName("Smith")
                .email("alice@example.com")
                .adddress("pune")
                .dateOfBirth(new Date())
                .build(); 

        when(userRepo.existsByUserName(traineeProfileDTO.getEmail())).thenReturn(false);

        User user = User.builder()
                .firstName(traineeProfileDTO.getFirstName())
                .lastName(traineeProfileDTO.getLastName())
                .userName("alice")
                .email(traineeProfileDTO.getEmail())
                .password("generated_password")
                .isActive(true)
                .build();
        when(userService.generatePassword()).thenReturn("generated_password");
        when(userRepo.save(any(User.class))).thenReturn(user);

        Trainee trainee = Trainee.builder()
                .user(user)
                .adddress(traineeProfileDTO.getAdddress())
                .dateOfBirth(traineeProfileDTO.getDateOfBirth())
                .build();
        when(traineeRepo.save(any(Trainee.class))).thenReturn(trainee);

        CredentialsDTO credentialsDTO = CredentialsDTO.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .build();
        
      

        CredentialsDTO result = traineeServiceimpl.registerTrainee(traineeProfileDTO);
        assertEquals(credentialsDTO.getUsername(), result.getUsername());
        assertEquals(credentialsDTO.getPassword(), result.getPassword());


        
    }
	
	
	
	  @Test
	    void testRegisterTrainee_UsernameExists() throws Exception {
	        TraineeProfileDTO traineeProfileDTO = TraineeProfileDTO.builder()
	                .firstName("John")
	                .lastName("Doe")
	                .email("john.doe@example.com")
	                .build();

	        when(userRepo.existsByUserName(traineeProfileDTO.getEmail())).thenReturn(true);

	 

	        
	        assertThrowsExactly(GymException.class, ()->traineeServiceimpl.registerTrainee(traineeProfileDTO));
	    }
	
	@Test
    void testGetTraineeProfile() throws Exception {
        String username = "alice";
        Date dob = new Date();
        String address = "123 Main St";
        boolean isActive = true;

        User user = User.builder()
                   .userName(username)
                   .firstName("ashu")
                   .lastName("godse")
                   .isActive(isActive)
                   .build();
        
        User user2 = User.builder()
                    .userName("user")
                    .firstName("ashu")
                    .lastName("godse")
                    .isActive(isActive)
                    .build();
        
        Trainee trainee=new Trainee();
        TrainingType trainingType=TrainingType.builder().trainingTypeName("Fitness").build();
        Trainer trainer=Trainer.builder()
        		.user(user2)
        		.trainingType(trainingType)
        		.build();
                 trainee = Trainee.builder()
                .user(user)
                .dateOfBirth(dob)
                .adddress(address)
                .listOfTrainers(List.of(trainer))
                .build();
                 user.setTrainee(trainee);
                 user2.setTrainer(trainer);
        trainer.setListOfTrainees(List.of(trainee));
        when(traineeRepo.findByUserUserName(username)).thenReturn(Optional.of(trainee));
        Mockito.when(userRepo.findById(anyInt())).thenReturn(Optional.of(user2));
        TraineeProfileResponseDTO result = traineeServiceimpl.getTraineeProfile(username);

        assertNotNull(result);
        assertEquals(trainee.getUser().getFirstName(), result.getFirstName());
        assertEquals(trainee.getUser().getLastName(), result.getLastName());
        assertEquals(trainee.getDateOfBirth(), result.getDateofBirth());
        assertEquals(trainee.getAdddress(), result.getAddress());
        assertEquals(trainee.getUser().isActive(), result.isActive());

    }
	
	@Test
	 void testGetTraineeProfileForInValidUserName(){
		Mockito.when(traineeRepo.findByUserUserName(anyString())).thenReturn(Optional.empty());
		assertThrows(GymException.class,()->traineeServiceimpl.getTraineeProfile(anyString()));
		
	}
	@Test
	 void testUpdateTraineeProfileForInValidUserName(){
		  TraineeRequestDTO traineeRequestDTO = TraineeRequestDTO.builder()
	                .username("ashu")
	                .firstName("Alice")
	                .lastName("Smith")
	                .isActive(true)
	                .adddress("123 Main St")
	                .dateOfBirth(new Date()) // Set your desired date here
	                .build();

		Mockito.when(traineeRepo.findByUserUserName(traineeRequestDTO.getUsername())).thenReturn(Optional.empty());
		assertThrows(GymException.class,()->traineeServiceimpl.updateProfile(traineeRequestDTO));
		
	}


	
	   @Test
	   void testUpdateProfile() throws Exception {
	        String username = "alice";

	        TraineeRequestDTO traineeRequestDTO = TraineeRequestDTO.builder()
	                .username(username)
	                .firstName("Alice")
	                .lastName("Smith")
	                .isActive(true)
	                .adddress("123 Main St")
	                .dateOfBirth(new Date()) // Set your desired date here
	                .build();

	        User user = User.builder()
	                .firstName("Alice")
	                .lastName("Smith")
	                .isActive(true)
	                .userName(username)
	                .email("alice@example.com")
	                .build();
	        Trainee trainee = Trainee.builder()
	        		        .adddress("123 Main St")
	                       .user(user)
	                       .dateOfBirth(new Date()) 
	                       .build();
	        
	        user.setTrainee(trainee);

	        List<Trainer> trainers = new ArrayList<>(); // Set up trainers if needed
	        trainee.setListOfTrainers(trainers);

	        when(traineeRepo.findByUserUserName(username)).thenReturn(Optional.of(trainee));
	        when(userRepo.save(any(User.class))).thenReturn(user);
	        when(traineeRepo.save(any(Trainee.class))).thenReturn(trainee);


	        TraineeResponseDTO result = traineeServiceimpl.updateProfile(traineeRequestDTO);

	        assertNotNull(result);

	    }
	   
	   @Test
	   void testUpdateTraineeProfile() throws Exception {
	        String traineeUsername = "traineeA";
	        
	        TraineeRequestDTO traineeRequestDTO = TraineeRequestDTO.builder()
	                .username(traineeUsername)
	                .firstName("John")
	                .lastName("Doe")
	                .isActive(true)
	                .adddress("123 Main St")
	                .dateOfBirth(new Date())
	                .build();

	     User userTrainee=User.builder().id(1)
                      	 .userName(traineeUsername)
      		             .email("ashu@email.com")
                      	 .firstName("John")
                      	 .lastName("Doe")
                      	 .isActive(true)
                      	 .build();
	        Trainee trainee = Trainee.builder()
	                         .user(userTrainee)
	                         .adddress("pune")
	                         .dateOfBirth(new Date())
	                         .build();
	                 
	        userTrainee.setTrainee(trainee);
	        List<Trainer>list=new ArrayList<>();
	        
	        Trainer trainer=Trainer
	        		        .builder().id(2)
	        		        .trainingType(
	        		         TrainingType.builder()
	        		        .trainingTypeName("Fitness").build())
	        		        .build();

	        User trainerUser = User.builder().id(2)
	        		           .userName("traineeA")
	        		           .email("ashu@email.com")
	        		           .firstName("ashu")
	        		           .lastName("godse")
	        		           .trainer(trainer)
	        		           .build();
	        
	        trainer.setUser(trainerUser);
	        list.add(trainer);
	        trainee.setListOfTrainers(list);

	        when(traineeRepo.findByUserUserName(traineeUsername)).thenReturn(Optional.of(trainee));
	        when(userRepo.findById(trainerUser.getId())).thenReturn(Optional.of(trainerUser));

	     
	        
	        TraineeResponseDTO result = traineeServiceimpl.updateProfile(traineeRequestDTO);

	        assertNotNull(result);

	  
	    }
	   
	   @Test
	   void testDeleteTraineeProfile() throws Exception {
	        String username = "alice";
	        Trainee trainee = Trainee.builder()
	                          .user(User.builder().userName(username).build())
	                          .build();

	        when(traineeRepo.findByUserUserName(username)).thenReturn(Optional.of(trainee));

	        doNothing().when(userRepo).deleteById(anyInt());

	        traineeServiceimpl.deleteTraineeProfile(username);

	    } 
	   
	   @Test
		 void testDeleteTraineeProfileForInValidUserName(){
			Mockito.when(traineeRepo.findByUserUserName(anyString())).thenReturn(Optional.empty());
			assertThrows(GymException.class,()->traineeServiceimpl.deleteTraineeProfile(anyString()));
			
		}
	   
	   @Test
	   void testGetTraineeTrainingDetails() throws Exception{
		   
		    String username = "alice";
	        String trainerName = "Dhruv";
	        String trainingTypeName = "Fitness";
	        Date periodFrom = new Date();
	        Date periodTo = new Date();

	        TraineeRequestTrainingDTO trainingRequestDTO = TraineeRequestTrainingDTO.builder()
	                .userName(username)
	                .trainerName(trainerName)
	                .trainingType(trainingTypeName)
	                .periodFrom(periodFrom)
	                .periodTo(periodTo)
	                .build();
		
	        TrainingType trainingType=TrainingType.builder().trainingTypeName("Fitness").build();
		   Trainee trainee = Trainee.builder()
                   .user(User.builder().userName(username).build())
                   .build();
		   Trainer trainer = Trainer.builder()
                   .user(User.builder().firstName("Dhruv").lastName("Joshi").userName(trainerName).build())
                   .trainingType(trainingType)
                   .build();
		   List<Training> trainings=new ArrayList<>();
		   
		   Training training=Training.builder()
				   .duration(60)
				   .date(new Date())
				   .trainee(trainee)
				   .trainer(trainer)
				   .trainingType(trainer.getTrainingType())
				   .build();
		  trainings.add(training);
		  
		  
		  Mockito.when( traineeRepo.findByUserUserName(anyString())).thenReturn(Optional.of(trainee));
     
     Mockito.when(trainingRepo.findTrainingsForTrainee(trainingRequestDTO.getUserName(), trainingRequestDTO.getPeriodFrom(),trainingRequestDTO.getPeriodTo(), trainingRequestDTO.getTrainerName(), trainingRequestDTO.getTrainingType())).thenReturn(trainings);

     
     List<TrainingTraineeResponseDTO>expected=traineeServiceimpl.getTraineeTrainingDetails(trainingRequestDTO);
     
    expected.stream().forEach(r->System.out.print(r.getTrainerName()));
    assertNotNull(expected);
     
	   }
	   
	   @Test
	   void testUpdateTraineeTrainers() throws Exception {
	        String traineeUserName = "traineeA";
	        List<String> trainerUsernames = List.of("trainer1", "trainer2");

	        Trainee trainee = Trainee.builder()
	                .user(User.builder().id(1).userName(traineeUserName).build())
	                .build();
	        TrainingType trainingType=TrainingType.builder().trainingTypeName("Fitness").build();

	        Trainer trainer = Trainer.builder()
	                   .user(User.builder().firstName("Dhruv").lastName("Joshi").userName("traineeA").build())
	                   .trainingType(trainingType)
	                   
	                   .build();
	        User trainerUser1 = User.builder()
	                   .userName("trainer1")
	                   .firstName("ashu")
	                   .lastName("godse")
	                   .trainer(trainer)
	                   .isActive(true)
	                   .build();
	        
	        User trainerUser2 = User.builder()
	                    .userName("trainer2")
	                    .firstName("ashu")
	                    .lastName("godse")
		                 .trainer(trainer)
	                    .isActive(true)
	                    .build();
	
	        List<User> trainers = List.of(trainerUser1, trainerUser2);

	        when(traineeRepo.findByUserUserName(traineeUserName)).thenReturn(Optional.of(trainee));
	        when(userRepo.findAllByUserNameIn(trainerUsernames)).thenReturn(trainers);
	        when(traineeRepo.save(any(Trainee.class))).thenReturn(trainee);

	        
	        List<TrainerListResponseDTO> result = traineeServiceimpl.updateTraineeTrainers(traineeUserName, trainerUsernames);

	        assertNotNull(result);
	        assertEquals(2, result.size());
	      
	    }
	   
	   
	    @Test
		void testUpdateTraineeTrainersForInValidUserName(){
			Mockito.when(traineeRepo.findByUserUserName("ashu")).thenReturn(Optional.empty());
			List<String>trainerUsernames=new ArrayList<>();
			assertThrows(GymException.class,()->traineeServiceimpl.updateTraineeTrainers("ashu",trainerUsernames));
			
		}
	    
	    @Test
		void testGetTraineeTrainingsDetailsForInValidUserName(){

		    String username = "alice";
	        String trainerName = "Dhruv";
	        String trainingTypeName = "Fitness";
	        Date periodFrom = new Date();
	        Date periodTo = new Date();

	        TraineeRequestTrainingDTO trainingRequestDTO = TraineeRequestTrainingDTO.builder()
	                .userName(username)
	                .trainerName(trainerName)
	                .trainingType(trainingTypeName) 
	                .periodFrom(periodFrom)
	                .periodTo(periodTo)
	                .build();
			Mockito.when(traineeRepo.findByUserUserName(trainingRequestDTO.getUserName())).thenReturn(Optional.empty());
			assertThrows(GymException.class,()->traineeServiceimpl.getTraineeTrainingDetails(trainingRequestDTO));
			
		}
	   
}
