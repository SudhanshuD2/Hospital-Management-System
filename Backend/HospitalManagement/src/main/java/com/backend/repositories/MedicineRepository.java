package com.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

}
