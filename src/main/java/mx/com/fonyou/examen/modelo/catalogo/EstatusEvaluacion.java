/**
 * EstatusEvaluacion.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.modelo.catalogo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Clase de Enum para el Estatus de la Evaluacion
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Getter
@AllArgsConstructor
public enum EstatusEvaluacion {

	/** APROBADO - Indica que el examen fue aprovado */
	APROBADO,
	
	/** SIN_PRESENTAR - Indica que el examen aun no fue presentado por el Estudiante */
	SIN_PRESENTAR,
	
	/** REPROBADO - Indica que el examen fue reprobado */
	REPROBADO;

}
