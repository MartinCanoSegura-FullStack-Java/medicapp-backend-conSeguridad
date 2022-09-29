package com.mitocode.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mitocode.model.Signos;

public interface ISignosService extends ICRUD<Signos, Integer> {
	
	Page<Signos> listarPageableByIdPaciente(Pageable pageable, Integer idP);
	
//	Integer maxId();
	
//	Integer sequencia(Integer idP);
	
//	Integer funcion(Integer idP);
	
//	Signos listarPorIdSIdP(Integer idS, Integer idP);
		
	

}
