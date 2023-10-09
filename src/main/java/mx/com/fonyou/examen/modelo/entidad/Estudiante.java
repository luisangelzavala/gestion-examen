/**
 * Estudiante.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.modelo.entidad;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Clase contenedor y mapeo de tabla de Estudiantes
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Data
@Entity
@Table(name = "estudiantes")
public class Estudiante implements Serializable {

	private static final long serialVersionUID = 1127872074948477065L;
	
	@Id
	@Column(name = "id_estudiante")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 80, nullable = false)
	@NotNull(message = "Se debe de especificar el nombre del estudiante")
	private String nombre;
	
	@Column(name = "primer_apellido", length = 50)
	private String primerApellido;
	
	@Column(name = "segundo_apellido", length = 50)
	private String segundoApellido;
	
	@Column(name = "edad", nullable = false)
	@NotNull(message = "Se debe de especificar la edad del estudiante")
	private Integer edad;
	
	@Column(length = 150, nullable = false)
	@NotNull(message = "Se debe de especificar la ciudad del estudiante")
	private String ciudad;
	
	@Column(name = "zona_horaria", length = 50, nullable = false)
	@NotNull(message = "Se debe de especificar la zona horaria del estudiante")
	private String zonaHoraria;
	
	@Column(name = "correo_electronico", length = 255, nullable = false)
	@NotNull(message = "Se debe de especificar el correo electronico del estudiante")
	private String correoElectronico;
	
}
