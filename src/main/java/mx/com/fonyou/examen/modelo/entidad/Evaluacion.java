/**
 * Evaluacion.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.modelo.entidad;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mx.com.fonyou.examen.modelo.catalogo.EstatusEvaluacion;

/**
 * Clase contenedor y mapeo de tabla de Evaluacion
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Data
@Entity
@Table(name = "evaluaciones")
@EqualsAndHashCode(callSuper = false)
public class Evaluacion implements Serializable {

	private static final long serialVersionUID = 1749360767185310329L;

	@Id
	@Column(name = "id_evaluacion")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_examen")
    private Examen examen;

	@ManyToOne
	@JoinColumn(name = "id_estudiante")
	private Estudiante estudiante;
	
	@Column(name = "fecha_evaluacion")
	@Temporal(TemporalType.DATE)
	private Date fechaEvaluacion;
	
	@Column(nullable = false)
	private Double calificacion;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estatus_evaluacion", nullable = false)
	private EstatusEvaluacion estatusEvaluacion;
	
}
