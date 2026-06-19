package com.backend.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name="user_id"))
@ToString(callSuper = true)
public class User extends BaseEntity{
	
	@Column(name="first_name", length = 40, nullable = false)
	private String firstName;
	@Column(name="last_name", length = 40, nullable = false)
	private String lastName;
	
	@Column(nullable = false)
	private String email;
	@Column(length = 25, nullable = false)
	private String password;
	@Column(length= 12, nullable = false)
	private String phone;
	
	@Enumerated(EnumType.STRING)
	@Column(name="user_role")
	private UserRole userRole;
	
	@OneToOne(mappedBy = "user")
	private Doctor doctor;
	
	@OneToOne(mappedBy = "user")
	private Patient patient;
	
	/*
	 * No connections here
	 * One User can be:
	 * One Doctor Profile
	 * One Patient Profile
	*/
}
