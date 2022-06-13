package com.example.demo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LitigioDTO;
import com.example.demo.dto.LitigioRespuesta;
import com.example.demo.services.LitigioService;

@RestController
@RequestMapping("sial/litigios")
public class LitigioController {

	@Autowired
	private LitigioService litigioService;

	@GetMapping
	public LitigioRespuesta listarLitigios(
			@RequestParam(value="pageNumber", defaultValue="0", required = false) int numeroPagina,
			@RequestParam(value="pageSize", defaultValue="5", required = false) int medidaPagina,
			@RequestParam(value="sortBy", defaultValue="id", required=false) String ordenarPor,
			@RequestParam(value="sortDir", defaultValue="asc", required=false) String sortDir) {
		return litigioService.obtenerLitigios(numeroPagina, medidaPagina,ordenarPor, sortDir);
	}

	@GetMapping("/{id}")
	public ResponseEntity<LitigioDTO> obtenerLitigioPorId(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(litigioService.obtenerLitigioPorId(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<LitigioDTO> guardarLitigio(@Valid @RequestBody LitigioDTO litigioDTO) {
		return new ResponseEntity<>(litigioService.crearLitigio(litigioDTO), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<LitigioDTO> actualizarLitigio(@Valid @RequestBody LitigioDTO litigioDTO,
			@PathVariable(name = "id") long id) {
		LitigioDTO litigioRespuesta = litigioService.actualizarLitigio(litigioDTO, id);
		return new ResponseEntity<>(litigioRespuesta, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarLitigio(@PathVariable(name = "id") long id) {
		litigioService.eliminarLitigio(id);
		return new ResponseEntity<>("Litigio eliminado", HttpStatus.OK);
	}

}
