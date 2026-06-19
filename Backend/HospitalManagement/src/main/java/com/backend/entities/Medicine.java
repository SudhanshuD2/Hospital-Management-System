package com.backend.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name="medicines")
@ToString(callSuper = true)
public class Medicine extends BaseEntity{
	
	private String name;
	
	private String manufacturer;
	
	private Double price;
	
	@OneToMany(mappedBy = "medicine")
	private List<PrescriptionMedicine> prescriptions= new ArrayList<>();
}
