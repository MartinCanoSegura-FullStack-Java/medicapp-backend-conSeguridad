package com.mitocode.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Signos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idSignos;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_paciente", nullable = false, foreignKey = @ForeignKey(name = "FK_paciente_signos"))
	private Paciente paciente;
	
	@Size(min = 2, max =2 , message = "{signos.temperatura.size}")
	@Column(name = "temperatura", nullable = false, length = 3)
	private String temperatura;
	
	@Size(min = 2, max =3 , message = "{signos.pulso.size}")
	@Column(name = "pulso", nullable = false, length = 3)
	private String pulso;
	
	@Size(min = 2, max =3 , message = "{signos.ritmo.size}")
	@Column(name = "ritmo", nullable = false, length = 3)
	private String ritmo;
	
	@Column(name = "fecha", nullable = false)
	private LocalDateTime fecha;

	public Integer getIdSignos() {
		return idSignos;
	}

	public void setIdSignos(Integer idSignos) {
		this.idSignos = idSignos;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public String getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}

	public String getPulso() {
		return pulso;
	}

	public void setPulso(String pulso) {
		this.pulso = pulso;
	}

	public String getRitmo() {
		return ritmo;
	}

	public void setRitmo(String ritmo) {
		this.ritmo = ritmo;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	
	
}
