package com.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DoctorReq {
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phone;
	private Long deptId;
	private String specialization;
	private Integer experienceYears;
	private Double consultationFee;
	
}
