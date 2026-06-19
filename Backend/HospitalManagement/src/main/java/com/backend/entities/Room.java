package com.backend.entities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="rooms")
@Getter
@Setter
@ToString()
@NoArgsConstructor
public class Room extends BaseEntity{
	@Column(name="room_number", nullable = false, unique = true)
	@Positive
	private Integer roomNumber;
	
	@Column(nullable = false)
	@PositiveOrZero
	private Integer floor;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RoomStatus status= RoomStatus.AVAILABLE;
	
	@OneToMany(mappedBy = "room")
	private List<Admission> admissions = new ArrayList<>();
}
