/**
 * Pregunta.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.modelo.entidad;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Clase contenedor y mapeo de tabla de Pregunta
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Data
@Entity
@Table(name = "preguntas")
public class Pregunta implements Serializable {

	private static final long serialVersionUID = -8031310274955824085L;

	@Id
	@Column(name = "id_pregunta")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 255, nullable = false)
	@NotNull(message = "Se debe de defiir la pregunta")
	private String descripcion;
	
	@Column(name = "id_examen", nullable = false)
	@NotNull(message = "Se debe de identificar el examen para la pregunta")
	private Long idExamen;
	
	@OneToMany
	private List<Opcion> opciones;
	
	@Column(nullable = false)
	@NotNull(message = "Se debe de defiir el valor de la pregunta en el examen")
	private Double valor;
}
