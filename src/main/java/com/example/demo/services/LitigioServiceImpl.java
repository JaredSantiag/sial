package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LitigioDTO;
import com.example.demo.dto.LitigioRespuesta;
import com.example.demo.entities.Litigio;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.LitigioRepository;

@Service
public class LitigioServiceImpl implements LitigioService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private LitigioRepository litigioRepository;

	@Override
	public LitigioDTO crearLitigio(LitigioDTO litigioDTO) {
		// Convertir DTO a Entidad
		Litigio litigio = mapearEntidad(litigioDTO);

		Litigio nuevoLitigio = litigioRepository.save(litigio);

		LitigioDTO litigioRespuesta = mapearDTO(nuevoLitigio);
		return litigioRespuesta;
	}

	@Override
	public LitigioRespuesta obtenerLitigios(int numeroPagina, int medidaPagina,String ordenarPor, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();

		Pageable pageable = PageRequest.of(numeroPagina, medidaPagina,sort); 
		Page<Litigio> litigios = litigioRepository.findAll(pageable);
		
		List<Litigio> litigiosLista = litigios.getContent();
		List<LitigioDTO> contenido = litigiosLista.stream().map(litigio-> mapearDTO(litigio)).collect(Collectors.toList());
	
		LitigioRespuesta litigioRespuesta = new LitigioRespuesta();
		litigioRespuesta.setContenido(contenido);
		litigioRespuesta.setNumeroPagina(litigios.getNumber());
		litigioRespuesta.setMedidaPagina(litigios.getSize());
		litigioRespuesta.setTotalElementos(litigios.getTotalElements ());
		litigioRespuesta.setTotalPaginas(litigios.getTotalPages());
		litigioRespuesta.setUltima(litigios.isLast());
		
		return litigioRespuesta;
	
	}

	@Override
	public LitigioDTO obtenerLitigioPorId(Long id) {
		Litigio litigio = litigioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Litigio", "id", id));
		return mapearDTO(litigio);
	}

	@Override
	public LitigioDTO actualizarLitigio(LitigioDTO litigioDTO, long id) {
		Litigio litigio = litigioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Litigio", "id", id));
		
		litigio.setExpediente(litigioDTO.getExpediente());
		litigio.setActor(litigioDTO.getActor());
		litigio.setDemandado(litigioDTO.getDemandado());

		Litigio litigioActualizado = litigioRepository.save(litigio);
		
		return mapearDTO(litigioActualizado);		
	}
	
	@Override
	public void eliminarLitigio(Long id) {
		Litigio litigio = litigioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Litigio", "id", id));
		litigioRepository.delete(litigio);
		
	}

	
	// Convierte entidad a DTO
	private LitigioDTO mapearDTO(Litigio litigio) {
		LitigioDTO litigioDTO = modelMapper.map(litigio, LitigioDTO.class);
		return litigioDTO;
	}

	// Convierte de DTO a Entidad
	private Litigio mapearEntidad(LitigioDTO litigioDTO) {
		Litigio litigio = modelMapper.map(litigioDTO, Litigio.class);
		return litigio;
	}

}
