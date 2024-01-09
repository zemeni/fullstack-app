package com.example.fullstackapp.repository;

import com.example.fullstackapp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface RoomTestRepository extends JpaRepository<Room, Integer> {
}
