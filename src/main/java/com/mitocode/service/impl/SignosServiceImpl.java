package com.mitocode.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mitocode.model.Paciente;
import com.mitocode.model.Signos;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.ISignosRepo;
import com.mitocode.service.IPacienteService;
import com.mitocode.service.ISignosService;

@Service
public class SignosServiceImpl extends CRUDImpl<Signos, Integer> implements ISignosService {
	
	Logger log = LoggerFactory.getLogger(SignosServiceImpl.class);

	@Autowired
	private ISignosRepo repo;
	
	@Override
	protected IGenericRepo<Signos, Integer> getRepo() {
		return repo;
	}
	
	@Autowired
	private IPacienteService pService;
	
	public Page<Signos> listarPageableByIdPaciente(Pageable pageable, Integer idP) {
		Paciente paciente = new Paciente();
		
		try {
			paciente = pService.listarPorId(idP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Comparator<Signos> byIdSignos = (Signos s1, Signos s2) -> s1.getIdSignos().compareTo(s2.getIdSignos()); 
		Collections.sort(paciente.getSignos(), byIdSignos);
		
		final int start = (int) pageable.getOffset();
		final int end = Math.min((start + pageable.getPageSize()) , paciente.getSignos().size());
		
		return new PageImpl<>(paciente.getSignos().subList(start, end), pageable, paciente.getSignos().size()) ;
	}

//	@Override
//	public Integer maxId() {
//		List<Integer> lista = repo.maxidSignos();
//		List<Integer> consecutivos = new ArrayList<>();
//		List<Integer> notIn = new ArrayList<>();
//		
//		
//		
//		Integer max;
//		System.out.println("------------------------------------");
//		Collections.sort(lista);
////		lista.forEach(e -> System.out.println(e));
//		
//		max = lista.get(lista.size() - 1);
//		
//		
//		for(int i=1; i<= max; i++) { consecutivos.add(i); }
//		
//		notIn = consecutivos.stream().filter(f -> !lista.contains(f)).collect(Collectors.toList());
//		
//		notIn.forEach(e -> System.out.println(e));
//		
//		Collections.sort(notIn);
//		System.out.println("--------------------------" + notIn.get(0));
//		
//		
//		
//		
//		return notIn.get(0);
//	}
	
	


//	@Override
//	public Integer sequencia(Integer idP) {
//		Object resp = repo.executeSequence(idP);
//		return resp != null ? Integer.valueOf(resp.toString()) + 1 : 1;
//	}
	
//	@Override
//	public Integer funcion(Integer idP) {
//		Object resp = repo.fn_secuence(idP);
//		return resp != null ? Integer.valueOf(resp.toString()) + 1 : 1;
//	}


//	@Override
//	public Signos listarPorIdSIdP(Integer idS, Integer idP) {
//		return repo.listarPorIdSIdP(idS, idP);
//	}

}
