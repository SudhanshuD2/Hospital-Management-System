package com.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
