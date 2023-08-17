package com.epam.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.epam.dto.request.TrainerProfileDTO;
import com.epam.dto.request.TrainerRequestDTO;
import com.epam.dto.request.TrainerRequestTrainingDTO;
import com.epam.dto.response.CredentialsDTO;
import com.epam.dto.response.TraineeListResponseDTO;
import com.epam.dto.response.TrainerProfileResponseDTO;
import com.epam.dto.response.TrainerResponseDTO;
import com.epam.dto.response.TrainingTrainerResponseDTO;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.model.TrainingType;
import com.epam.model.User;
import com.epam.repository.TrainerRepo;
import com.epam.repository.TrainingRepo;
import com.epam.repository.TrainingTypeRepo;
import com.epam.repository.UserRepo;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrainerServiceImpl implements TrainerService { 

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private TrainerRepo trainerRepo;

	@Autowired
	private TrainingRepo trainingRepo;

	@Autowired
	private TrainingTypeRepo trainingTypeRepo;


	@Autowired
	private KafkaProducer kafkaProducer;
	

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Override
	@Transactional
	public CredentialsDTO registerTrainer(TrainerProfileDTO trainerProfileDTO) throws GymException {
		log.info("Entered into registerTrainer Service with TrainerProfileDTO :" + trainerProfileDTO);
		
		if (userRepo.existsByUserName(trainerProfileDTO.getEmail())) {
			log.error("GymException has occured");
			
			throw new GymException("Trainer with the same username Already Exists...!!please sign up with other username");
		}
		String password = userServiceImpl.generatePassword();
		String[] userName = trainerProfileDTO.getEmail().split("@");
		User user= User.builder().firstName(trainerProfileDTO.getFirstName()).lastName(trainerProfileDTO.getLastName())
				  .isActive(true).userName(userName[0]).email(trainerProfileDTO.getEmail()).password(password).build();

		userRepo.save(user);

		
		Trainer trainer = new Trainer();
		trainer.setUser(user);
        TrainingType trainingTypeInfo = trainingTypeRepo.findByTrainingTypeName(trainerProfileDTO.getTrainingtypeName()).orElseThrow(()->new GymException("The Training Type is not present!!"));

        trainer.setTrainingType(trainingTypeInfo);

		
		trainerRepo.save(trainer);
		
		log.info("Exiting from registerTrainer Service");
		
		CredentialsDTO credentialsDTO = CredentialsDTO.builder().username(user.getUserName()).password(password)
				                        .build();

		Map<String, String> details = new LinkedHashMap<>();
		details.put("subject", "Registration Successful!!!");
		details.put("userName", user.getUserName());
		details.put("passord", user.getPassword());

		MailRequestDTO mailRequestDTO = MailRequestDTO.builder().toMail(user.getEmail())
				.bccMails(List.of(user.getEmail())).ccMails(List.of(user.getEmail())).details(details).build();

		Message<MailRequestDTO> message = MessageBuilder.withPayload(mailRequestDTO)
				.setHeader(KafkaHeaders.TOPIC, "topic-notification").build();

		kafkaProducer.sendMail(message);
		return credentialsDTO;
	}

	@Override
	public TrainerProfileResponseDTO getTrainerProfile(String username) throws GymException {
		log.info("Entered into getTrainerProfile Service with username :" + username);
		Trainer trainer = trainerRepo.findByUserUserName(username)
				.orElseThrow(() -> new GymException("Invalid Trainee Name"));

		List<TraineeListResponseDTO> listOfTrainees = new ArrayList<>();

		if (!trainer.getListOfTrainees().isEmpty()) {
			listOfTrainees = trainer.getListOfTrainees().stream().map(trainee -> {
				User traineeInfo = userRepo.findById(trainee.getUser().getId()).get();

				return TraineeConvertor.convertTraineeToTraineeListResponseDTO(traineeInfo);
			}).toList();
		}
		log.info("Exiting from getTrainerProfile Service");
		return TrainerConvertor.convertTrainerModelToTrainerProfileResponseDTO(trainer, listOfTrainees);

	}

	@Transactional
	public TrainerResponseDTO updateProfile(TrainerRequestDTO trainerRequestDTO) throws GymException {
		log.info("Entered into updateProfile Service with TrainerRequestDTO :" + trainerRequestDTO);

		User user = userRepo.findByUserName(trainerRequestDTO.getUserName())
				.orElseThrow(() -> new GymException("Inalid username"));
		user.setActive(trainerRequestDTO.isActive());
		user.setFirstName(trainerRequestDTO.getFirstName());
		user.setLastName(trainerRequestDTO.getLastName());

		userRepo.save(user);

		Trainer trainer = trainerRepo.findByUserId(user.getId()).get();
		
		trainer.getTrainingType().setTrainingTypeName(trainerRequestDTO.getSpecialization());

		trainerRepo.save(trainer);

		List<TraineeListResponseDTO> listOfTrainees = new ArrayList<>();
		if (!trainer.getListOfTrainees().isEmpty()) {
			listOfTrainees = trainer.getListOfTrainees().stream().map(trainee -> {
				User traineeInfo = userRepo.findById(trainee.getUser().getId()).get();
				return TraineeListResponseDTO.builder().userName(traineeInfo.getUserName())
						.firstName(traineeInfo.getFirstName()).lastName(traineeInfo.getLastName()).build();

			}).toList();
 
		}
		log.info("Exiting from updateProfile Service");

		Map<String, String> details = new HashMap<>();

		details.put("subject", "Registration Successful!!!");
		details.put("User Name:", user.getUserName());
		details.put("First Name:", user.getFirstName());
		details.put("Last Name:", user.getFirstName());
		details.put("specialization:", user.getTrainer().getTrainingType().getTrainingTypeName());

		MailRequestDTO mailRequestDTO = MailRequestDTO.builder().toMail(user.getEmail())
				.bccMails(List.of(user.getEmail())).ccMails(List.of(user.getEmail())).details(details).build();

		Message<MailRequestDTO> message = MessageBuilder.withPayload(mailRequestDTO)
				.setHeader(KafkaHeaders.TOPIC, "topic-notification-data").build();
		kafkaProducer.sendMail(message);
		return TrainerResponseDTO.builder().userName(user.getUserName()).firstName(user.getFirstName())
				.lastName(user.getLastName()).isActive(user.isActive())
				.specialization(trainer.getTrainingType().getTrainingTypeName()).listOfTrainees(listOfTrainees).build();
	}

	@Override
	@Transactional
	public List<TrainingTrainerResponseDTO> getTrainingList(TrainerRequestTrainingDTO trainerRequestTrainingDTO)
			throws GymException {

		log.info("Entered into getTrainingList Service with TrainerRequestTrainingDTO :" + trainerRequestTrainingDTO);

		Trainer trainer = trainerRepo.findByUserUserName(trainerRequestTrainingDTO.getUserName())
				.orElseThrow(() -> new GymException("Invalid Trainer Username!!"));

		log.info("Exiting from getTrainerProfile Service");

		return trainingRepo.findTrainingsByTraineeId(trainer.getId()).stream().map(training -> {

			return TrainingTrainerResponseDTO.builder().date(training.getDate()).duration(training.getDuration())
					.trainingName(training.getName())
//					.traineeName(userRepo.findById(training.getTrainee().getUser().getId()).get().getUserName())
					.traineeName(training.getTrainee().getUser().getUserName())
					.build();

		}).toList();
	}

	@Override
	public List<TrainingTrainerResponseDTO> getTrainerTrainingDetails(TrainerRequestTrainingDTO training)
			throws GymException {
		
		log.info("Entered into getTrainerTrainingDetails Service with TrainerRequestTrainingDTO :" + training);

		Trainer trainer = trainerRepo.findByUserUserName(training.getUserName())
				         .orElseThrow(() -> new GymException("trainee not found with the username"));

		List<Training> trainings = trainingRepo.findTrainingsForTrainer(trainer.getUser().getUserName(),
				                   training.getPeriodFrom(), training.getPeriodTo(), training.getTraineeName());

		log.info("Exiting from getTrainerTrainingDetails Service");

		return trainings.stream()
				.map(TrainingConvertor::convertTrainingToTrainingTrainerResponseDTO)
				.toList();
	}

} 
