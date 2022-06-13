package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Etapa;

public interface EtapaRepository extends JpaRepository<Etapa, Long>{
	public List<Etapa> findByLitigioId(Long litigionId);
}
