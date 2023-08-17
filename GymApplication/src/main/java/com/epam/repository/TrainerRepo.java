package com.epam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.model.Trainer;

public interface TrainerRepo extends JpaRepository<Trainer, Integer> {

	Optional<Trainer> findByUserId(int userId);
    Optional<Trainer> findByUserUserName(String userName);



}
