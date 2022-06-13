package com.example.demo.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EtapaDTO;
import com.example.demo.entities.Etapa;
import com.example.demo.entities.Litigio;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.SialAppException;
import com.example.demo.repositories.EtapaRepository;
import com.example.demo.repositories.LitigioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EtapaServiceImpl implements EtapaService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EtapaRepository etapaRepository;
	
	@Autowired
	private LitigioRepository litigioRepository;
	
	@Override
	public EtapaDTO crearEtapa(long litigioId, EtapaDTO etapaDTO) {
		Etapa etapa =  mapearEntidad(etapaDTO);
		Litigio litigio = litigioRepository.findById(litigioId)
				.orElseThrow(() -> new ResourceNotFoundException("Litigio", "id", litigioId));
		
		etapa.setLitigio(litigio);
		etapa.setFecha(etapaDTO.getFecha());
		Etapa nuevaEtapa = etapaRepository.save(etapa);
		return mapearDTO(nuevaEtapa);
	}
	
	@Override
	public List<EtapaDTO> obtenerEtapasPorLitigioId(long litigioId){
		List<Etapa> etapas = etapaRepository.findByLitigioId(litigioId);
		return etapas.stream().map(etapa ->mapearDTO(etapa)).collect(Collectors.toList());
	}
	
	@Override
	public EtapaDTO obtenerEtapaPorId(Long litigioId, Long etapaId) {
		Litigio litigio = litigioRepository.findById(litigioId)
				.orElseThrow(() -> new ResourceNotFoundException("Litigio", "id", litigioId));
		Etapa etapa = etapaRepository.findById(etapaId)
				.orElseThrow(() -> new ResourceNotFoundException("Etapa", "id", etapaId));
		
		if(!etapa.getLitigio().getId().equals(litigio.getId())) {
			throw new SialAppException(HttpStatus.BAD_REQUEST, "Esta etapa no es del litigio");
		}
		
		return mapearDTO(etapa);
	}
	
	@Override
	public EtapaDTO actualizarEtapa(Long litigioId, Long etapaId,EtapaDTO etapaDTO) {
		Litigio litigio = litigioRepository.findById(litigioId)
				.orElseThrow(() -> new ResourceNotFoundException("Litigio", "id", litigioId));
		
		Etapa etapa = etapaRepository.findById(etapaId)
				.orElseThrow(() -> new ResourceNotFoundException("Etapa", "id", etapaId));
		
		if(!etapa.getLitigio().getId().equals(litigio.getId())) {
			throw new SialAppException(HttpStatus.BAD_REQUEST, "Esta etapa no es del litigio");
		}
		
		etapa.setDescripcion(etapaDTO.getDescripcion());
		etapa.setFecha(etapaDTO.getFecha());
		
		Etapa eatapaActualizada = etapaRepository.save(etapa);
		return mapearDTO(eatapaActualizada);
	}
	

	@Override
	public void eliminarEtapa(Long litigioId, Long etapaId) {
		Litigio litigio = litigioRepository.findById(litigioId)
				.orElseThrow(() -> new ResourceNotFoundException("Litigio", "id", litigioId));
		
		Etapa etapa = etapaRepository.findById(etapaId)
				.orElseThrow(() -> new ResourceNotFoundException("Etapa", "id", etapaId));
		
		if(!etapa.getLitigio().getId().equals(litigio.getId())) {
			throw new SialAppException(HttpStatus.BAD_REQUEST, "Esta etapa no es del litigio");
		}
		
		etapaRepository.delete(etapa);
		
	}
	
	private EtapaDTO mapearDTO(Etapa etapa){
	    EtapaDTO etapaDTO = modelMapper.map(etapa, EtapaDTO.class);
	    return etapaDTO;
	}
	
	private Etapa mapearEntidad(EtapaDTO etapaDTO){
	    Etapa etapa = modelMapper.map(etapaDTO, Etapa.class);
	    return etapa;
	}
	
}
