package com.mitocode.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.mitocode.dto.SignosDTO;
import com.mitocode.model.Paciente;
import com.mitocode.model.Signos;
import com.mitocode.service.IPacienteService;
import com.mitocode.service.ISignosService;

@RestController
@RequestMapping("/signos")
public class SignosController {
	Logger log = LoggerFactory.getLogger(SignosController.class);
	
	@Autowired
	private ISignosService service;
	
	@Autowired
	private IPacienteService pService;
		
	@GetMapping("/pageable/{idP}")
	public ResponseEntity<Page<Signos>> listarPageableByIdPaciente(Pageable pageable, @PathVariable("idP") Integer idP) throws Exception{
		Page<Signos> signos = service.listarPageableByIdPaciente(pageable, idP);
		return new ResponseEntity<Page<Signos>>(signos, HttpStatus.OK);
	}
	
	@GetMapping("/{idP}")
	public ResponseEntity<List<Signos>> listarPorIdPaciente(@Valid @PathVariable("idP") Integer idP) throws Exception {
		Paciente paciente = pService.listarPorId(idP);
		return new ResponseEntity<List<Signos>>(paciente.getSignos(), HttpStatus.OK);
	}
	
	@GetMapping("listarPorIdS/{idS}")
	public ResponseEntity<Signos> listarPorIdS(@PathVariable("idS") Integer idS) throws Exception{
		Signos signos = service.listarPorId(idS);
		return new ResponseEntity<Signos>(signos, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Signos> registrar(@Valid @RequestBody SignosDTO signos) throws Exception {
		Integer totalSignos;
		Paciente p = pService.listarPorId(signos.getIdPaciente());
		
		totalSignos = p.getSignos().size() + 1;
		p.setTotalSignos(totalSignos);
		
		Signos signoss = new Signos();
		
		signoss.setFecha(signos.getSignos().getFecha());
		signoss.setPulso(signos.getSignos().getPulso());
		signoss.setRitmo(signos.getSignos().getRitmo());
		signoss.setTemperatura(signos.getSignos().getTemperatura());
		signoss.setPaciente(p);
		signoss.setIdSignos(0);
		
		Signos obj = service.registrar(signoss);
		return new ResponseEntity<Signos>(obj, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Signos> modificar(@Valid @RequestBody Signos signos) throws Exception {
		Signos s = service.listarPorId(signos.getIdSignos());
		Integer idPaciente = s.getPaciente().getIdPaciente();
		s.setPaciente(pService.listarPorId(idPaciente));
		s.setFecha(signos.getFecha());
		s.setIdSignos(signos.getIdSignos());
		s.setPulso(signos.getPulso());
		s.setRitmo(signos.getRitmo());
		s.setTemperatura(signos.getTemperatura());
		Signos obj = service.modificar(s);
		return new ResponseEntity<Signos>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("{idS}")
	public ResponseEntity<Void> deleteByPK(@Valid @PathVariable("idS") Integer idS ) throws Exception {
		service.eliminar(idS);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	
	
//	@GetMapping
//	public ResponseEntity<List<Signos>>findAll() throws Exception {
//		List<Signos> lista = service.listar();
//		return new ResponseEntity<List<Signos>>(lista, HttpStatus.OK);
//	}
	
	
//	@GetMapping("/sequence/{idP}")
//	public ResponseEntity<Integer> sequence(@PathVariable("idP") Integer idP) throws Exception {
//		Integer sequencia = service.sequencia(idP);
//		return new ResponseEntity<Integer>(sequencia, HttpStatus.OK);
//	}
	
//	@GetMapping("/funcion/{idP}")
//	public ResponseEntity<Integer> funcion(@PathVariable("idP") Integer idP) throws Exception {
//		Integer retorno = service.funcion(idP);
//		return new ResponseEntity<Integer>(retorno, HttpStatus.OK);
//	}
	
	
	
	
	
	
//	@GetMapping("/hateoas/{id}")
//	public EntityModel<Signos> listarPorIdHateoas(@PathVariable("id") Integer id) throws Exception {
//		Signos obj = service.listarPorId(id);
//
//		if (obj.getIdSignos() == null) {
//			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
//		}
//
//		// localhost:8080/signos/{id}
//		EntityModel<Signos> recurso = EntityModel.of(obj);
//
//		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));
//
//		recurso.add(linkTo.withRel("signos-recurso"));
//
//		return recurso;
//	}
	
	

}
