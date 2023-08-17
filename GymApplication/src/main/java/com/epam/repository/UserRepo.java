package com.epam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	public boolean existsByUserName(String username);

	public Optional<User> findByUserName(String userName);

	public boolean existsByUserNameAndPassword(String username, String password);

	public List<User> findAllByUserNameIn(List<String> trainerUsernames);
}
