package com.epam.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.epam.customexception.ReportException;
import com.epam.dto.ReportResponseDTO;
import com.epam.dto.request.ReportRequestDTO;
import com.epam.model.Report;
import com.epam.repository.ReportRepo;

@Service
public class ReportServiceImpl {
	
	@Autowired
	private ReportRepo reportRepo;
	
	
	@Value("${kafka.topic.name}")
	private String topic;
	
	@Value("${spring.kafka.consumer.group-id}")
	private String groupID;
	
	@KafkaListener( topics="${kafka.topic.name}",groupId = "{spring.kafka.consumer.group-id}")
	public void addTrainingReport(ReportRequestDTO reportRequestDTO) {
		
		Report report=reportRepo.findById(reportRequestDTO.getTrainerUserName()).
				orElseGet(()->{
					return Report.builder()
					.firstName(reportRequestDTO.getTrainerFirstName())
					.lastName(reportRequestDTO.getTrainerLastName())
					.userName(reportRequestDTO.getTrainerUserName())
					.status(reportRequestDTO.isStatus())
					.summary(new HashMap<>())
					.build();
				});
		
		report.setSummary(new HashMap<>());
		Date date=reportRequestDTO.getStartDate();
		
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
		int month=calendar.get(Calendar.MONTH)+1;
		
		int year=calendar.get(Calendar.YEAR);
		
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		
		report.getSummary().computeIfAbsent(year, k->new HashMap<>())
		.computeIfAbsent(month,k->new HashMap<>()).put(day, reportRequestDTO.getDuration());
		
		reportRepo.save(report);

		
	}
	
	public ReportResponseDTO getReport(String userName) throws ReportException {
		Report report=reportRepo.findById(userName).orElseThrow(()->new ReportException("Invalid userName"));
		return ReportResponseDTO.builder()
				.isActive(report.isStatus())
				.firstName(report.getFirstName())
				.lastName(report.getLastName())
				.summary(report.getSummary())
				.userName(report.getUserName())
				.build();
	}

}
