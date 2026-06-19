package com.backend.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;

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
@Table
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Admission extends BaseEntity{
	
	@CreatedDate
	@Column(name="admission_date")
	private LocalDate admissionDate;
	
	@Column(name="discharge_date")
	private LocalDate dischargeDate;
	
	@ManyToOne
	@JoinColumn(name="patient_id")
	private Patient patient;
	
	@ManyToOne
	@JoinColumn(name="room_id")
	private Room room;
}
