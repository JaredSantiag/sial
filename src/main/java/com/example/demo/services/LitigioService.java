package com.example.demo.services;


import com.example.demo.dto.LitigioDTO;
import com.example.demo.dto.LitigioRespuesta;

public interface LitigioService {

	public LitigioDTO crearLitigio(LitigioDTO litigioDTO);
	
	public LitigioRespuesta obtenerLitigios(int NumeroPagina, int medidaPagina, String ordenarPor, String sortDir);
	
	public LitigioDTO obtenerLitigioPorId(Long id);
	
	public LitigioDTO actualizarLitigio(LitigioDTO litigioDTO, long id);
	
	public void eliminarLitigio(Long id);

}
