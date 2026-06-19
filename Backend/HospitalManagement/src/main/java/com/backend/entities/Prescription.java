package com.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name="prescriptions")
@NoArgsConstructor
public class Prescription extends BaseEntity{
	@Column(length = 100)
	private String diagnosis;
	@Column(name="doctor_notes", length = 100)
	private String doctorNotes;
	
	// One Appointment → One Prescription
	@OneToOne(mappedBy = "perscription")
	private Appointment appointment;
}
