package com.backend.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name= "appointments")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Appointment extends BaseEntity{
	
	@Column(name= "appointment_date_time")
	private LocalDateTime appointmentDateTime;
	
	@Enumerated(EnumType.STRING)
	private AppointmentStatus status = AppointmentStatus.PENDING;
	/*
	 * Many Appointments → One Doctor
	 * Many Appointments → One Patient
	 * One Appointment → One Prescription
	*/
	
	@ManyToOne
	@JoinColumn(name="doctor_id")
	private Doctor doctor;
	
	@ManyToOne
	@JoinColumn(name="patient_id")
	private Patient patient;
	
	@OneToOne
	@JoinColumn(name="prescription_id")
	private Prescription perscription;
	
}
