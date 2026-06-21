package com.backend.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
	@OneToOne(mappedBy = "prescription")
	private Appointment appointment;
	
	@OneToMany(mappedBy = "prescription")
    private List<PrescriptionMedicine> medicines = new ArrayList<>();
}
