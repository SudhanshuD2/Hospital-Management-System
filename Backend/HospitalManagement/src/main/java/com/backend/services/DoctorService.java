package com.backend.services;

import com.backend.dtos.DoctorReq;
import com.backend.utils.ApiResponse;

public interface DoctorService {
	ApiResponse registerDoctor(DoctorReq doctor);
	
	ApiResponse getDoctorById(Long id);
	
	ApiResponse getAllDoctors();
	
	ApiResponse updateConsultationFee(Long id, Double newFees);
	
	ApiResponse deleteDoctorById(Long id);
}
