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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.mitocode.model.Usuario;
import com.mitocode.service.IUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private IUsuarioService service;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listar() throws Exception {
		List<Usuario> lista = service.listar();
		return new ResponseEntity<List<Usuario>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Usuario obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Usuario>(obj, HttpStatus.OK);
	}
	
	@GetMapping("/hateoas/{id}")
	public EntityModel<Usuario> listarPorIdHateoas(@PathVariable("id") Integer id) throws Exception {
		Usuario obj = service.listarPorId(id);
		if (obj.getIdUsuario() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		// localhost:8080/Usuario/{id}
		EntityModel<Usuario> recurso = EntityModel.of(obj);
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));
		recurso.add(linkTo.withRel("Usuario-recurso"));
		return recurso;
	}
	
	@PostMapping
	public ResponseEntity<Usuario> registrar(@Valid @RequestBody Usuario u) throws Exception {
		Integer nextIdUsuario = service.maxIdUsuarios();
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(nextIdUsuario + 1);
		usuario.setUsername(u.getUsername());
		usuario.setEnabled(u.isEnabled());
		usuario.setPassword(bcrypt.encode(u.getPassword()));
		usuario.setRoles(u.getRoles());
		
		Usuario obj = service.registrar(usuario);
		
		// localhost:8080/Usuario/2
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getIdUsuario()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Usuario> modificar(@Valid @RequestBody Usuario p) throws Exception {
		Usuario obj = service.modificar(p);
		return new ResponseEntity<Usuario>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Usuario obj = service.listarPorId(id);
		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/pageable")
	public ResponseEntity<Page<Usuario>> listarPageable(Pageable pageable) throws Exception{
		Page<Usuario> Usuario = service.listarPageable(pageable);
		return new ResponseEntity<Page<Usuario>>(Usuario, HttpStatus.OK);
	}
	

}
