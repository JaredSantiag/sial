package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Litigio;

public interface LitigioRepository extends JpaRepository<Litigio, Long> {

}
