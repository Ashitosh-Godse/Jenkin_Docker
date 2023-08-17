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

import com.epam.convertor.TrainerConvertor;
import com.epam.convertor.TrainingConvertor;
import com.epam.customexception.GymException;
import com.epam.dto.request.MailRequestDTO;
import com.epam.dto.request.ReportRequestDTO;
import com.epam.dto.request.TrainingRequestDTO;
import com.epam.dto.response.TrainerListResponseDTO;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.model.User;
import com.epam.repository.TraineeRepo;
import com.epam.repository.TrainerRepo;
import com.epam.repository.TrainingRepo;
import com.epam.repository.UserRepo;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrainingServiceImpl implements TrainingService {

	
	@Autowired
	private TraineeRepo traineeRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private TrainerRepo trainerRepo;
	
	@Autowired
	private TrainingRepo trainingRepo;
	
	@Autowired
	private KafkaProducer kafkaProducer;
	
	
	
	@Override
	@Transactional
	public void addTraining(TrainingRequestDTO trainingRequestDTO) throws GymException {
		
		log.info("Entered into getTrainerTrainingDetails Service with TrainingRequestDTO :"+trainingRequestDTO);		
		
		Trainee trainee=traineeRepo.findByUserUserName(trainingRequestDTO.getTraineeUsername())
				.orElseThrow(()->new GymException("Invalid Trainee Username!!!"));		
		
		Trainer trainer=trainerRepo.findByUserUserName(trainingRequestDTO.getTrainerUsername())
				.orElseThrow(()->new GymException("Invalid Trainer Username!!!"));
		

		Map<String, String> details = new LinkedHashMap<>();
		details.put("subject", "New Training Added!!!");
		details.put("details:", "new Training added");

		MailRequestDTO mailRequestDTO = MailRequestDTO.builder().toMail(trainer.getUser().getEmail())
				.bccMails(List.of(trainee.getUser().getEmail())).ccMails(List.of(trainee.getUser().getEmail())).details(details).build();
		Message<MailRequestDTO> message = MessageBuilder.withPayload(mailRequestDTO)
				.setHeader(KafkaHeaders.TOPIC, "topic-notification-data").build();
      

		kafkaProducer.sendMail(message);
        
		Training training=Training.builder()
						  .trainee(trainee)
						  .trainer(trainer)
						  .date(trainingRequestDTO.getDate())
						  .trainingType(trainer.getTrainingType())
						  .duration(trainingRequestDTO.getDuration())
						  .name(trainingRequestDTO.getTrainingName())
						  .build();
		
		trainingRepo.save(training);
		
		ReportRequestDTO reportRequestDTO=TrainingConvertor.convertTrainingModelToReportRequestDTO(training);
      	
        Message<ReportRequestDTO> report = MessageBuilder
                         				  .withPayload(reportRequestDTO)
                         				  .setHeader(KafkaHeaders.TOPIC, "topic-report-data")
                         				  .build();
        
        kafkaProducer.sendReport(report);


		
		
		log.info("Exiting from addTraining Service");		

		
	}
	
 
	
	@Override
    public List<TrainerListResponseDTO> unassignedTrainers(String username)throws GymException {
		log.info("Entered into unassignedTrainers Service with username :"+username);		
        Trainee trainee = traineeRepo.findByUserUserName(username)
                .orElseThrow(() -> new GymException("Trainee not found with this username"));
        List<TrainerListResponseDTO> trainers=new ArrayList<>();
           
        trainers = trainerRepo.findAll().stream()
                .filter(trainer -> !trainer.getListOfTrainees().contains(trainee))
                .map(trainer -> {
                    User userTrainer = userRepo.findById(trainer.getUser().getId()).get();
                    return TrainerConvertor.convertTrainerToTrainerListResponseDTO(userTrainer);
                }).toList();
            
    		log.info("Exiting from unassignedTrainers Service");		

        return trainers;
    }

}
