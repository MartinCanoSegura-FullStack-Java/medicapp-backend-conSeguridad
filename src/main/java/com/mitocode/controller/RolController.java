package com.mitocode.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Rol;
import com.mitocode.service.IRolService;

@RestController
@RequestMapping("/roles")
public class RolController {
	
	@Autowired
	private IRolService service;
	
	@GetMapping
	public ResponseEntity<List<Rol>> listar() throws Exception {
		List<Rol> lista = service.listar();
		return new ResponseEntity<List<Rol>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Rol> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Rol obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Rol>(obj, HttpStatus.OK);
	}
	
	@GetMapping("/hateoas/{id}")
	public EntityModel<Rol> listarPorIdHateoas(@PathVariable("id") Integer id) throws Exception {
		Rol obj = service.listarPorId(id);

		if (obj.getIdRol() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		// localhost:8080/Rol/{id}
		EntityModel<Rol> recurso = EntityModel.of(obj);

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));

		recurso.add(linkTo.withRel("Rol-recurso"));

		return recurso;
	}
	
	@PostMapping
	public ResponseEntity<Rol> registrar(@Valid @RequestBody Rol s) throws Exception {
		Integer idRol = service.getNextIdRol();
		Rol rol = new Rol();
		rol.setIdRol(idRol);
		rol.setNombre(s.getNombre());
		rol.setDescripcion(s.getDescripcion());
		
		Rol obj = service.registrar(rol);

		// localhost:8080/Rol/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getIdRol()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Rol> modificar(@Valid @RequestBody Rol p) throws Exception {
		Rol obj = service.modificar(p);
		return new ResponseEntity<Rol>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Rol obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/pageable")
	public ResponseEntity<Page<Rol>> listarPageable(Pageable pageable) throws Exception{
		Page<Rol> Rol = service.listarPageable(pageable);
		return new ResponseEntity<Page<Rol>>(Rol, HttpStatus.OK);
	}

}
