package com.example.demo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EtapaDTO;
import com.example.demo.services.EtapaService;

@RestController
@RequestMapping("/sial/")
public class EtapaController {

	@Autowired
	EtapaService etapaService;
	
	
	@GetMapping("/litigios/{litigioId}/etapas")
	public List<EtapaDTO> listarEtapasPorLitigios(@PathVariable(value="litigioId") Long litigioId){
		return etapaService.obtenerEtapasPorLitigioId(litigioId);
	}
	
	
	@GetMapping("/litigios/{litigioId}/etapas/{id}")
	public ResponseEntity<EtapaDTO> obtenerEtapaPorId(@PathVariable(value="litigioId") Long litigioId,@PathVariable(value="id") Long etapaId){
		EtapaDTO etapaDTO = etapaService.obtenerEtapaPorId(litigioId, etapaId);
		return new ResponseEntity<>(etapaDTO, HttpStatus.OK);
	}
	
	@PostMapping("/litigios/{litigioId}/etapas")
	public ResponseEntity<EtapaDTO> guardarEtapa(@PathVariable(value="litigioId") long litigioId,@Valid @RequestBody EtapaDTO etapaDTO){
		return new ResponseEntity<>(etapaService.crearEtapa(litigioId, etapaDTO),HttpStatus.CREATED);
	}
	
	@PutMapping("/litigios/{litigioId}/etapas/{etapaId}")
	public ResponseEntity<EtapaDTO> actualizarEtapa(@PathVariable(value="litigioId") Long litigioId,@PathVariable(value="etapaId") Long etapaId,@Valid @RequestBody EtapaDTO etapaDTO){
		EtapaDTO etapaActualizada = etapaService.actualizarEtapa(litigioId, etapaId, etapaDTO);
		return new ResponseEntity<>(etapaActualizada,HttpStatus.OK);
	}
	
	@DeleteMapping("/litigios/{litigioId}/etapas/{id}")
	public ResponseEntity<String> eliminarEtapa(@PathVariable(value="litigioId") Long litigioId,@PathVariable(value="id") Long etapaId){
		etapaService.eliminarEtapa(litigioId, etapaId);
		return new ResponseEntity<>("Etapa eliminada", HttpStatus.OK);
	}
}
