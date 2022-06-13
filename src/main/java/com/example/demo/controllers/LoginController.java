package com.example.demo.controllers;

import java.sql.Date;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RegistroDTO;
import com.example.demo.entities.Licencia;
import com.example.demo.entities.Role;
import com.example.demo.entities.Usuario;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.LicenciaRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UsuarioRepository;
import com.example.demo.security.JWTAuthResonseDTO;
import com.example.demo.security.JwtTokenProvider;

@RestController
@RequestMapping("/sial/login")
public class LoginController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired 
	private LicenciaRepository licenciaRepository;
	
	
	@PostMapping("/iniciarsesion")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO){

		//Obtneemos la licencia del usuario
		Optional<Usuario> usuario=usuarioRepository.findByUsername(loginDTO.getUsername());
		Licencia licencia = usuario.get().getLicencia();
		
		long miliseconds = System.currentTimeMillis();
        Date fecha_actual = new Date(miliseconds);
		
        if(fecha_actual.before(licencia.getFechaFin())) {
        	Authentication authentication = 
    				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
    		
    		SecurityContextHolder.getContext().setAuthentication(authentication);
    		
    		//Obtenemos el token del jwtTokenProvider
    		String token = jwtTokenProvider.generarToken(authentication);
    		
    		return ResponseEntity.ok(new JWTAuthResonseDTO(token));
        }
        else {
        	return new ResponseEntity<>("Licencia Vencida",HttpStatus.BAD_REQUEST);
        }
        
	}
	
	@PostMapping("/licencias/{licenciaId}/registrar")
	public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDTO registroDTO, long licenciaId){
		
		if(usuarioRepository.existsByUsername(registroDTO.getUsername())) {
			return new ResponseEntity<>("Ese nombre de usuario ya existe",HttpStatus.BAD_REQUEST);
		}
		
		if(usuarioRepository.existsByEmail(registroDTO.getEmail())) {
			return new ResponseEntity<>("Ese email de usuario ya existe",HttpStatus.BAD_REQUEST);
		}
		
		Usuario usuario = new Usuario();
		usuario.setNombre(registroDTO.getNombre());
		usuario.setUsername(registroDTO.getUsername());
		usuario.setEmail(registroDTO.getEmail());
		usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
		
		Licencia licencia = licenciaRepository.findById(licenciaId).orElseThrow(() -> new ResourceNotFoundException("Licencia", "id", licenciaId));
		
		usuario.setLicencia(licencia);
		
		Role roles = roleRepository.findByNombre("ROLE_ADMIN").get();
		usuario.setRoles(Collections.singleton(roles));
		
		usuarioRepository.save(usuario);
		return new ResponseEntity<>("Usuario registrado exitosamente",HttpStatus.OK);
	}
}
