package com.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DoctorResp {
	private String name;
	private String email;
	private String phone;
	private Boolean isActive;
	private String specialization;
	private Integer experienceYear;
	private Double consultationFees;
	
	@Override
	public String toString() {
		return String.format("%-20s%-10s%-13s%-5s%-16s%-5d%-8.2f", name, email, phone, isActive, specialization, experienceYear, consultationFees);
	}
}
