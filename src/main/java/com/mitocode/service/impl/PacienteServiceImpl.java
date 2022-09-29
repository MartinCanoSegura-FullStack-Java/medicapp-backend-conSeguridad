package com.mitocode.service.impl;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mitocode.model.Paciente;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IPacienteRepo;
import com.mitocode.service.IPacienteService;

@Service
public class PacienteServiceImpl extends CRUDImpl<Paciente, Integer> implements IPacienteService{

	@Autowired
	private IPacienteRepo repo;

	@Override
	protected IGenericRepo<Paciente, Integer> getRepo() {
		return repo;
	}
	
	@Override
	public Page<Paciente> listarPageable(Pageable pageable) {
		List<Paciente> pacientes = repo.findAll();
		
		pacientes.forEach(p -> {
			p.setTotalSignos( p.getSignos().size() );
		});
		
		
		Comparator<Paciente> byIdPaciente = (Paciente p1, Paciente p2) -> p1.getIdPaciente().compareTo(p2.getIdPaciente());
		pacientes.sort(byIdPaciente);
		
		final int start = (int) pageable.getOffset();
		final int end = Math.min((start + pageable.getPageSize()) , pacientes.size());

		return new PageImpl<>(pacientes.subList(start, end), pageable, pacientes.size());
//		return repo.findAll(pageable);
	}
	
}
