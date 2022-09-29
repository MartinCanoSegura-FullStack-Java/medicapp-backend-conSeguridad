package com.mitocode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitocode.model.Menu;

public interface IMenuRepo extends IGenericRepo<Menu, Integer>{

//	@Query(value="select m.* from menu_rol mr inner join usuario_rol ur on ur.id_rol = mr.id_rol inner join menu m on m.id_menu = mr.id_menu inner join usuario u on u.id_usuario = ur.id_usuario where u.nombre = :nombre", nativeQuery = true)
//	List<Menu> listarMenuPorUsuario(@Param("nombre") String nombre);
	
	
	@Query(value="select  m.id_menu id_menu, m.icono icono, m.nombre, m.url\r\n"
			+ "	from    rol r, menu_rol mr, menu m \r\n"
			+ "	where   mr.id_rol = r.id_rol and\r\n"
			+ "	        mr.id_menu = m.id_menu and\r\n"
			+ "	        r.id_rol in (select min(id_rol) rol\r\n"
			+ "			from usuario u, usuario_rol ur\r\n"
			+ "			where   u.id_usuario = ur.id_usuario and\r\n"
			+ "			        u.nombre = :nombre)", nativeQuery = true)
	List<Menu> listarMenuPorUsuario(@Param("nombre") String nombre);
	
	@Query(value = "SELECT MAX(m.id_menu) FROM menu m", nativeQuery = true)
	Integer maxIdMenus();
	
}
