package com.mitocode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.mitocode.model.Signos;

public interface ISignosRepo extends IGenericRepo<Signos, Integer> {
	
//	@Query(value = "SELECT s.id_signos FROM signos s", nativeQuery = true)
//	List<Integer> maxidSignos();
	
//	@Query(value = "select s.* from signos s where s.id_paciente = :idPaciente", nativeQuery = true)
//	List<Signos> listarPK(Integer idPaciente);
	
//	Page<Signos> findBySignosPK(SignosPK signosPK,Pageable pageable);
	
//	@Query(value = "select max(s.id_signos) as ultimo from signos s where s.id_paciente = :idP", nativeQuery = true)
//	Integer executeSequence(@Param("idP") Integer idP);
	
//	@Query(value = "call fn_sequence(:idP)", nativeQuery = true)
//	Integer executeFuncion(@Param("idP") Integer paciente);
//	@Procedure
//	public Integer fn_secuence(Integer idPaciente);
	
//	@Query(value = "SELECT s.* FROM signos s WHERE s.id_paciente = :idP and id_signos = :idS", nativeQuery = true)
//	Signos listarPorIdSIdP(@Param("idS") Integer idS, @Param("idP") Integer idP);


}
