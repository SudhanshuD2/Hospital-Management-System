package com.backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.dtos.DoctorReq;
import com.backend.dtos.DoctorResp;
import com.backend.entities.Department;
import com.backend.entities.Doctor;
import com.backend.entities.User;
import com.backend.entities.UserRole;
import com.backend.repositories.DepartmentRepository;
import com.backend.repositories.DoctorRepository;
import com.backend.repositories.UserRepository;
import com.backend.utils.ApiResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

	private final DoctorRepository doctorRepo;
	private final UserRepository userRepo;
	private final DepartmentRepository deptRepo;
	
	@Override
	public ApiResponse registerDoctor(DoctorReq dto) {
		
		User usr = userRepo.save(new User(
				dto.getFirstName(),
				dto.getLastName(),
				dto.getEmail(),
				dto.getPassword(),
				dto.getPhone(),
				UserRole.DOCTOR
			));
		
		Department dept = deptRepo.findById(dto.getDeptId()).orElseThrow(
				()->new RuntimeException("User not found"));
		
		Doctor doctor = new Doctor();
		doctor.setUser(usr);
		doctor.setDepartment(dept);
		doctor.setConsultationFee(dto.getConsultationFee());
		doctor.setExperienceYears(dto.getExperienceYears());
		doctor.setSpecialization(dto.getSpecialization());
		
		doctorRepo.save(doctor);
		
		return new ApiResponse("Success", "Registered new doctor");
	}

	@Override
	public ApiResponse getDoctorById(Long id) {
		
		Doctor doc = doctorRepo.findById(id).orElseThrow(()->new RuntimeException("Doctor not Found"));
		
		DoctorResp response = new DoctorResp(
				String.format("%s %s", doc.getUser().getFirstName(), doc.getUser().getLastName()), 
				doc.getUser().getEmail(),
				doc.getUser().getPhone(),
				doc.getUser().getIsActive(),
				doc.getSpecialization(),
				doc.getExperienceYears(),
				doc.getConsultationFee());
		
		return new ApiResponse("Success", response.toString());
	}

	@Override
	public ApiResponse getAllDoctors() {
		List<DoctorResp> allDoctors = doctorRepo.
				findAll().
				stream().
				map(this::convertToDoctorResp).
				toList();
		
		return new ApiResponse("Success", allDoctors.toString());
	}

	@Override
	public ApiResponse updateConsultationFee(Long id, Double newFees) {
		Doctor doc = doctorRepo.findById(id).orElseThrow(()->new RuntimeException("Doctor not found"));
		doc.setConsultationFee(newFees);
		doctorRepo.save(doc);
		return new ApiResponse("Success", "Updated Fees of doctor");
	}

	@Override
	public ApiResponse deleteDoctorById(Long id) {
		Doctor doc = doctorRepo.findById(id).orElseThrow(()->new RuntimeException("Doctor not found"));
		doc.getUser().setIsActive(false);
		doctorRepo.save(doc);
		return new ApiResponse("Success", "Deleted Doctor");
	}
	
	private DoctorResp convertToDoctorResp(Doctor doctor) {
		DoctorResp docResp = new DoctorResp(
				String.format("%s %s", doctor.getUser().getFirstName(), doctor.getUser().getLastName()), 
				doctor.getUser().getEmail(),
				doctor.getUser().getPhone(),
				doctor.getUser().getIsActive(),
				doctor.getSpecialization(),
				doctor.getExperienceYears(),
				doctor.getConsultationFee());

		return docResp;
	}
}
