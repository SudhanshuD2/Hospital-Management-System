package com.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
