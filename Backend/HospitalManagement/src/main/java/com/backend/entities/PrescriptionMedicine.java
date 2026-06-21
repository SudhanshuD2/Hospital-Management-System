package com.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name="prescription_medicine")
public class PrescriptionMedicine extends BaseEntity{
	private String dosage;
	private String frequency;
	
	@Column(name="duration_days")
	private String durationDays;
	
	/*
	 * Many PrescriptionMedicine → One Prescription
	 * Many PrescriptionMedicine → One Medicine
	*/
	
	@ManyToOne
	@JoinColumn(name="prescription_id")
	private Prescription prescription;
	
	@ManyToOne
	@JoinColumn(name="medicine_id")
	private Medicine medicine;

}
