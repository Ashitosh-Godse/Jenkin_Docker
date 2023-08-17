package com.epam.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epam.model.Training;

public interface TrainingRepo extends JpaRepository<Training, Integer>{

	Optional<Training> findByTrainerId(int id);

	void deleteByTraineeId(int id);


	boolean existsByTraineeId(int traineeId);


	List<Training> findTrainingsByTraineeId(int id);

	
	
	@Query("SELECT t FROM Training t " +
            "JOIN t.trainee tr " +
            "JOIN t.trainer tnr " +
            "JOIN t.trainingType tt " +
            "WHERE tr.user.userName = :username " +
            "AND (:periodFrom IS NULL OR t.date >= :periodFrom) " +
            "AND (:periodTo IS NULL OR t.date <= :periodTo) " +
            "AND (:trainerName IS NULL OR tnr.user.firstName LIKE %:trainerName% OR tnr.user.lastName LIKE %:trainerName%) " +
            "AND (:trainingType IS NULL OR tt.trainingTypeName LIKE %:trainingType%)")
     List<Training> findTrainingsForTrainee(String username, Date periodFrom,Date periodTo, String trainerName, String trainingType);
	
	
	 @Query("SELECT t FROM Training t "+
           "JOIN t.trainee tr "+
           "JOIN t.trainer tnr "+
           "JOIN t.trainingType tt "
           + "WHERE tnr.user.userName = :userName "
           + "AND (:periodFrom IS NULL OR t.date >= :periodFrom) "
           + "AND (:periodTo IS NULL OR t.date <= :periodTo) "
           + "AND (:traineeName IS NULL OR tr.user.firstName LIKE %:traineeName% OR tr.user.lastName LIKE %:traineeName%) ")
	List<Training> findTrainingsForTrainer(String userName, Date periodFrom, Date periodTo, String traineeName);

	
	
	


}
