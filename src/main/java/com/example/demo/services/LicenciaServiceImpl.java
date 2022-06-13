package com.example.demo.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LicenciaDTO;
import com.example.demo.entities.Licencia;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.LicenciaRepository;

@Service
public class LicenciaServiceImpl implements LicenciaService{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private LicenciaRepository licenciaRepository;

	@Override
	public LicenciaDTO crearLicencia(LicenciaDTO licenciaDTO) {
		Licencia licencia = mapearEntidad(licenciaDTO);
		Licencia nuevaLicencia = licenciaRepository.save(licencia);
		
		LicenciaDTO licenciaRespuesta = mapearDTO(nuevaLicencia);
		return licenciaRespuesta;
	}


	@Override
	public LicenciaDTO obtenerLicenciaPorId(Long id) {
		Licencia licencia = licenciaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Licencia", "id", id));
		return mapearDTO(licencia);
	}

	
	// Convierte entidad a DTO
	private LicenciaDTO mapearDTO(Licencia licencia) {
		LicenciaDTO licenciaDTO = modelMapper.map(licencia, LicenciaDTO.class);
		return licenciaDTO;
	}

	// Convierte de DTO a Entidad
	private Licencia mapearEntidad(LicenciaDTO licenciaDTO) {
		Licencia licencia = modelMapper.map(licenciaDTO, Licencia.class);
		return licencia;
	}
}
