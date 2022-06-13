package com.example.demo.services;

import java.util.List;

import com.example.demo.dto.EtapaDTO;

public interface EtapaService {
	public EtapaDTO crearEtapa(long litigioId, EtapaDTO etapaDTO);
	
	public List<EtapaDTO> obtenerEtapasPorLitigioId(long litigioId);
	
	public EtapaDTO obtenerEtapaPorId(Long litigioId, Long etapaId);
	
	public EtapaDTO actualizarEtapa(Long litigioId, Long etapaId, EtapaDTO etapaDTO);
	
	public void eliminarEtapa(Long litigioId, Long etapaId);
}
