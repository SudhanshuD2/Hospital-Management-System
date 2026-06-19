package com.backend.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="doctors")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Doctor {

	@Id
	@Column(name="doctor_id")
	private Long id;
	
	@MapsId
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "doctor_id")
	private User user;
	
	private String specialization;
	
	@Column(name = "experience_years")
	@PositiveOrZero
	private Integer experienceYears;
	
	@Column(name = "consultation_fee")
	@Positive
	private Double consultationFee;
	
	@OneToMany(mappedBy = "doctor")
	private List<Appointment> appointments = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name="department_id")
	private Department department;
}
