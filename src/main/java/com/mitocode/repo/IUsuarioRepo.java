package com.mitocode.repo;

import org.springframework.data.jpa.repository.Query;

import com.mitocode.model.Usuario;

public interface IUsuarioRepo extends IGenericRepo<Usuario, Integer>  {

	//select * from usuario where username = ?
	Usuario findOneByUsername(String username);	
	
	@Query(value = "SELECT MAX(u.id_usuario) FROM usuario u", nativeQuery = true)
	Integer maxIdUsuarios();
}
