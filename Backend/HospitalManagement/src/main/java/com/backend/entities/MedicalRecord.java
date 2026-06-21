package com.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name="medical_records")
public class MedicalRecord extends BaseEntity{
	
	private String allergies;
	@Column(name="chronic_diseases")
	private String chronicDiseases;
	
	@Column(length = 100)
	private String notes;
	
	// patient to medical record one to one
	@OneToOne(mappedBy= "medicalRecord")
	private Patient patient;
}
