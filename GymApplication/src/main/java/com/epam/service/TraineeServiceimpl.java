package com.epam.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.epam.convertor.TraineeConvertor;
import com.epam.convertor.TrainerConvertor;
import com.epam.convertor.TrainingConvertor;
import com.epam.customexception.GymException;
import com.epam.dto.request.MailRequestDTO;
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
import com.epam.model.User;
import com.epam.repository.TraineeRepo;
import com.epam.repository.TrainingRepo;
import com.epam.repository.UserRepo;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TraineeServiceimpl implements TraineeService {

	@Autowired
	private TraineeRepo traineeRepo;

	@Autowired
	private UserRepo userRepo;
	
   
    @Autowired
    private KafkaProducer kafkaProducer;

	@Autowired
	private UserService userService;

	@Autowired
	private TrainingRepo trainingRepo;

	public CredentialsDTO registerTrainee(TraineeProfileDTO traineeProfileDTO) throws GymException {
		log.info("Entered into registerTrainee Service with TraineeProfileDTO :" + traineeProfileDTO);

		if (userRepo.existsByUserName(traineeProfileDTO.getEmail())) {
			log.error("GymException has Occured due to:Trainee with the same username Already Exists ");
			throw new GymException(
					"Trainee with the same username Already Exists...!!please sign up with other username");
		}

		String password = userService.generatePassword();
		String[] userName = traineeProfileDTO.getEmail().split("@");

		User user = User.builder().firstName(traineeProfileDTO.getFirstName()).lastName(traineeProfileDTO.getLastName())
				   .userName(userName[0]).email(traineeProfileDTO.getEmail()).password(password).isActive(true).build();

		userRepo.save(user);

		Trainee trainee = Trainee.builder().user(user).adddress(traineeProfileDTO.getAdddress())
						  .dateOfBirth(traineeProfileDTO.getDateOfBirth()).build();

		traineeRepo.save(trainee);

		log.info("Exiting from registerTrainee Service ");

		CredentialsDTO credentialsDTO = CredentialsDTO.builder().username(user.getUserName()).password(user.getPassword())
									    .build();

		Map<String, String> details = new LinkedHashMap<>();

		details.put("subject", "Registration Successful!!!");
		details.put("userName", user.getUserName());
		details.put("password", user.getPassword());

		MailRequestDTO mailRequestDTO = MailRequestDTO.builder().toMail(user.getEmail())
				                       .bccMails(List.of(user.getEmail())).ccMails(List.of(user.getEmail())).details(details).build();

		Message<MailRequestDTO> message = MessageBuilder
				                          .withPayload(mailRequestDTO)
				                          .setHeader(KafkaHeaders.TOPIC, "topic-notification").build();

		kafkaProducer.sendMail(message);

		return credentialsDTO;
	}

	public TraineeProfileResponseDTO getTraineeProfile(String username) throws GymException {
		log.info("Entered into getTraineeProfile Service with username :" + username);
		Trainee trainee = traineeRepo.findByUserUserName(username)
				.orElseThrow(() -> new GymException("Invalid Trainee Name"));

		List<TrainerListResponseDTO> listOfTrainers = new ArrayList<>();
		if (!trainee.getListOfTrainers().isEmpty()) {
			listOfTrainers = trainee.getListOfTrainers().stream().map(trainer -> {
				User trainerInfo = userRepo.findById(trainer.getUser().getId()).get();
				return TrainerConvertor.convertTrainerToTrainerListResponseDTO(trainerInfo);
			}).toList();
		}
		log.info("Exiting from getTraineeProfile Service ");

		return TraineeConvertor.convertTraineeModelToTraineeProfileResponseDTO(trainee, listOfTrainers);

	}

	@Override
	public TraineeResponseDTO updateProfile(TraineeRequestDTO traineeRequestDTO) throws GymException {
		log.info("Entered into updateProfile Service with TraineeRequestDTO :" + traineeRequestDTO);
		Trainee trainee = traineeRepo.findByUserUserName(traineeRequestDTO.getUsername())
				.orElseThrow(() -> new GymException("Invalid Trainee Name"));

		User user = trainee.getUser();
		user.setActive(traineeRequestDTO.isActive());
		user.setFirstName(traineeRequestDTO.getFirstName());
		user.setLastName(traineeRequestDTO.getLastName());

		userRepo.save(user); 

		trainee.setAdddress(traineeRequestDTO.getAdddress());
		trainee.setDateOfBirth(traineeRequestDTO.getDateOfBirth());

		traineeRepo.save(trainee);
		List<TrainerListResponseDTO> listOfTrainers = new ArrayList<>();
		if (!trainee.getListOfTrainers().isEmpty()) {
			listOfTrainers = trainee.getListOfTrainers().stream().map(trainer -> {
				User trainerInfo = userRepo.findById(trainer.getUser().getId()).get();

				return TrainerConvertor.convertTrainerToTrainerListResponseDTO(trainerInfo);

			}).toList();  
		}

		log.info("Exiting from updateProfile Service ");

		Map<String, String> details = new LinkedHashMap<>();

		details.put("subject", "Registration Successful!!!");
		details.put("User Name:", user.getUserName());
		details.put("First Name:", user.getFirstName());
		details.put("Last Name:", user.getFirstName());
		details.put("Address:", user.getTrainee().getAdddress());
		details.put("Date of Birth:", user.getTrainee().getDateOfBirth().toString());

		MailRequestDTO mailRequestDTO = MailRequestDTO.builder().toMail(user.getEmail())
				                       .bccMails(List.of(user.getEmail())).ccMails(List.of(user.getEmail())).details(details).build();

		Message<MailRequestDTO> message = MessageBuilder.withPayload(mailRequestDTO)
				.setHeader(KafkaHeaders.TOPIC, "topic-notification").build();

		kafkaProducer.sendMail(message);

		return TraineeConvertor.convertTraineeModelToTraineeResponseDTO(trainee, listOfTrainers);
	}

	@Override
	@Transactional
	public void deleteTraineeProfile(String username) throws GymException {
		log.info("Entered into deleteTraineeProfile Service with username :" + username);

		Trainee trainee = traineeRepo.findByUserUserName(username)
				.orElseThrow(() -> new GymException("Invalid Trainee Name"));

		userRepo.deleteById(trainee.getUser().getId());

		log.info("Exiting from deleteTraineeProfile Service ");
	}

	@Override
	@Transactional
	public List<TrainerListResponseDTO> updateTraineeTrainers(String traineeUserName, List<String> trainerUsernames)
			throws GymException {

		log.info("Entered into updateTraineeTrainers Service with username :" + traineeUserName);

		Trainee trainee = traineeRepo.findByUserUserName(traineeUserName)
				.orElseThrow(() -> new GymException("user not found with this username"));

		List<User> users = userRepo.findAllByUserNameIn(trainerUsernames);
		List<Trainer> trainers = new ArrayList<>();
		List<TrainerListResponseDTO> trainersDetails = users.stream().map(user -> {
			Trainer trainer = user.getTrainer();
			trainers.add(trainer);
			return TrainerConvertor.convertTrainerToTrainerListResponseDTO(user);

		}).toList();
		trainee.setListOfTrainers(trainers);
		traineeRepo.save(trainee);
		log.info("Exiting from updateTraineeTrainers Service ");
		return trainersDetails;
	}

	@Override
	@Transactional
	public List<TrainingTraineeResponseDTO> getTraineeTrainingDetails(TraineeRequestTrainingDTO training)
			throws GymException {

		log.info("Entered into getTraineeTrainingDetails Service with TraineeRequestTrainingDTO :" + training);

		Trainee trainee = traineeRepo.findByUserUserName(training.getUserName())
				          .orElseThrow(() -> new GymException("trainee not found with the username"));

		List<Training> trainings = trainingRepo.findTrainingsForTrainee(trainee.getUser().getUserName(),
				                   training.getPeriodFrom(), training.getPeriodTo(), training.getTrainerName(),
				                   training.getTrainingType());

		log.info("Exiting from getTraineeTrainingDetails Service ");

		return trainings.stream()
				.map(TrainingConvertor::convertTrainingToTrainingTraineeResponseDTO)
				.toList();
	}

}
