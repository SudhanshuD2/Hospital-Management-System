package com.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entities.Prescription;
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

}
