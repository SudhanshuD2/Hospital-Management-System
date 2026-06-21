package com.backend.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
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
	
	@Column(name = "is_active")
	private Boolean isActive=true;
	
	@Enumerated(EnumType.STRING)
	@Column(name="user_role")
	private UserRole userRole;
	
	@OneToOne(mappedBy = "user")
	private Doctor doctor;
	
	@OneToOne(mappedBy = "user")
	private Patient patient;
	
	public User(String firstName, String lastName, String email, String password, String phone, UserRole userRole) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.userRole = userRole;
	}
	/*
	 * No connections here
	 * One User can be:
	 * One Doctor Profile
	 * One Patient Profile
	*/
}
