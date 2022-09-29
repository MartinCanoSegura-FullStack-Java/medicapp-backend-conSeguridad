package com.mitocode.repo;

import org.springframework.data.jpa.repository.Query;

import com.mitocode.model.Archivo;

public interface IArchivoRepo extends IGenericRepo<Archivo, Integer>{
	
	@Query(value = "select max(id_archivo) from archivo", nativeQuery = true)
	Integer maxIdArchivoImg();

}
