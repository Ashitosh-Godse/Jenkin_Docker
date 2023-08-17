package com.epam.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String firstName;
	
	private String lastName;
	
	private String userName;
	
	private String password;
	
	private String email;
	
	private boolean isActive;
	

	
	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
	private Trainee trainee;
	
	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
	private Trainer trainer;
	
//	  private boolean disabled;
//	  
//	  private boolean accountExpired;
//	  
//	  private boolean accountLocked;
//	  
//	  private boolean credentialsExpired;
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		// TODO Auto-generated method stub
//		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//        authorities.add(new SimpleGrantedAuthority("USER"));
//
//        return authorities;
//	
//	}
//	
//	
//
//	@Override
//	public String getUsername() {
//		return this.userName;
//	}
//	
//	public String getUserName() {
//		return this.userName;
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
//
//
//
//	public User(int id, String firstName, String lastName, String userName, String password, String email,
//			boolean isActive) {
//		super();
//		this.id = id;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.userName = userName;
//		this.password = password;
//		this.email = email;
//		this.isActive = isActive;
//	}
//	
//	
//	

}
