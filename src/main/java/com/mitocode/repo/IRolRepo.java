package com.mitocode.repo;

import org.springframework.data.jpa.repository.Query;

import com.mitocode.model.Rol;

public interface IRolRepo extends IGenericRepo<Rol, Integer> {
	
	@Query(value = "SELECT MAX(r.id_rol) FROM rol r", nativeQuery = true)
	Integer nextIdRol();
}
