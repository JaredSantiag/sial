package com.example.demo.services;

import com.example.demo.dto.LicenciaDTO;

public interface LicenciaService {
	
	public LicenciaDTO crearLicencia(LicenciaDTO licenciaDTO);
	
	public LicenciaDTO obtenerLicenciaPorId(Long id);
	
}
