package com.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dtos.DoctorReq;
import com.backend.services.DoctorService;
import com.backend.utils.ApiResponse;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/Doctors")
@RequiredArgsConstructor
public class DoctorController {
	private final DoctorService docService;
	
	@GetMapping("/get-all")
	public ResponseEntity<?> getAllDoctors(){
		try {
			return ResponseEntity.ok(docService.getAllDoctors());
		}catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Failed", e.getMessage()));
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerDoctor(@NotBlank @RequestBody DoctorReq doc){
		try {
			return ResponseEntity.ok(docService.registerDoctor(doc));
		}catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Failed", e.getMessage()));
		}
	}
	
	@GetMapping("/get")
	public ResponseEntity<?> getDoctorById(@Positive @Min(1) @RequestBody Long id){
		try {
			return ResponseEntity.ok(docService.getDoctorById(id));
		}catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Failed", e.getMessage()));
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteDoctorById(@NotBlank @Min(1) @PathVariable Long id){
		try {
			return ResponseEntity.ok(docService.deleteDoctorById(id));
		}catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ApiResponse("Failed", e.getMessage()));
		}
	}
}
