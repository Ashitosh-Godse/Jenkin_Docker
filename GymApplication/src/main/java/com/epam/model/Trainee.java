package com.epam.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Trainee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")	
	private Date dateOfBirth;	

	private String adddress;
	

	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	
	@ManyToMany
	@JoinTable(name="Trainee2Trainer",joinColumns = @JoinColumn(name="trainee_id"),
	inverseJoinColumns = @JoinColumn(name="trainer_id"))
	List<Trainer> listOfTrainers;
	
	@OneToMany(mappedBy="trainee",cascade = CascadeType.ALL)
	private List<Training> training;
	

	
}
