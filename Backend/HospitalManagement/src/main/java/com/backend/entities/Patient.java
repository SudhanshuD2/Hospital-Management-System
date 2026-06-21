package com.backend.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="patients")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Patient {
	@Id
	@Column(name="patient_id")
	private Long id;
	
	@MapsId
	@JoinColumn(name = "patient_id")
	@OneToOne
	private User user;
	
	@Column(name="blood_group")
	@Enumerated(EnumType.STRING)
	private BloodGroup bloodGroup;
	
	@Column(name="date_of_birth", nullable = false)
	private LocalDate dateOfBirth;
	
	@Column(name="emergency_contact", nullable = false)
	private String emergencyContact;
	
	@OneToOne
	@JoinColumn(name="medical_record_id")
	private MedicalRecord medicalRecord;
	
	@OneToMany(mappedBy = "patient")
	private List<Appointment> appointments= new ArrayList<>();
	
	@OneToMany(mappedBy = "patient")
	private List<Admission> admissions = new ArrayList<>();
	
}
