package com.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.PrescriptionMedicine;

public interface PrescriptionMedicineRepository extends JpaRepository<PrescriptionMedicine, Long> {

}
