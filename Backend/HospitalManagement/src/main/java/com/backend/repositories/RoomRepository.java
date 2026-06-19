package com.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
