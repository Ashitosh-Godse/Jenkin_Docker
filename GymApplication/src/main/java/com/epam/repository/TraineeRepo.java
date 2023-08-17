package com.epam.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.model.Trainee;


public interface TraineeRepo extends JpaRepository<Trainee, Integer>{
	
	Optional<Trainee> findByUserUserName(String userName);

	Optional<Trainee> findByUserId(int userId);

	void deleteByUserId(int id);
	
 
	
	

}
