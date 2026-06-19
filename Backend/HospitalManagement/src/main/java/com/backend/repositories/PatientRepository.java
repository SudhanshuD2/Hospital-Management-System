package com.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
