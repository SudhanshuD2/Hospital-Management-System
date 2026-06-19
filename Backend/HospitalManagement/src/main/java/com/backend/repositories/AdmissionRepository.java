package com.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Admission;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {

}
