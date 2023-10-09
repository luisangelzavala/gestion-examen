/**
 * Opcion.java
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
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Clase contenedor y mapeo de tabla de Opcion
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Data
@Entity
@Table(name = "opciones")
public class Opcion implements Serializable {

	private static final long serialVersionUID = 4771521574640562355L;

	@Id
	@Column(name = "id_opcion")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 255, nullable = false)
	@NotNull(message = "La opción no puede ser nula")
	private String descripcion;
	
	@Column(nullable = false)
	private boolean correcta;
	
	@Column(name="id_pregunta", nullable = false)
	private Long idPregunta;
		
	private boolean seleccionada;
}
