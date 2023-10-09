/**
 * Examen.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.modelo.entidad;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mx.com.fonyou.examen.modelo.catalogo.EstatusExamen;

/**
 * Clase contenedor y mapeo de tabla de Examen
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Data
@Entity
@Table(name = "examenes")
public class Examen implements Serializable {

	private static final long serialVersionUID = 4880404813124103722L;
	
	@Id
	@Column(name = "id_examen")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estatus_examen")
	private EstatusExamen estatusExamen;
	
	@Column(length = 255, nullable = false)
	@NotNull(message = "Se debe de especificar el nombre o tema del examen")
	private String nombre;
	
	@Column(name = "valor_examen", nullable = false)
	private Double valorExamen;
	
	@Column(name = "fecha_inicio_examen")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "Se debe de especificar la fecha de inicio del del examen")
	private Timestamp fechaInicioExamen;
	
	@Column(name = "fecha_fin_examen")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "Se debe de especificar la fecha de fin del del examen")
	private Timestamp fechaFinExamen;
	
	@OneToMany
	private List<Pregunta> preguntas;
	
}
