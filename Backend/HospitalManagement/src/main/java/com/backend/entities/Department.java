package com.backend.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="departments")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Department extends BaseEntity{
	@Column(length = 50)
	private String name;
	@Column(length = 100)
	private String description;
	
	// One department has many doctors
	@OneToMany(mappedBy = "department")
	private List<Doctor> doctors = new ArrayList<>();
}
