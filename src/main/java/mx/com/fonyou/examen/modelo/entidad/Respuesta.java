/**
 * Respuesta.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.modelo.entidad;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

/**
 * Clase contenedor y mapeo de tabla de Respuesta
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Data
@Entity
@Table(name = "respuestas")
public class Respuesta implements Serializable {
	
	private static final long serialVersionUID = -4337581391574948055L;
	
	@Id
	@Column(name = "id_respuesta")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "id_pregunta")
	private Pregunta pregunta;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "id_opcion")
	private Opcion opcion;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "id_evaluacion")
	private Evaluacion evaluacion;
	
	@Transient
	private Double totalPuntos;
	
}
