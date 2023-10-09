/**
 * EstudianteService.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.service;

import mx.com.fonyou.examen.modelo.entidad.Estudiante;

/**
 * Interface para las funcionalidades del Estudiante
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
public interface EstudianteService {

	/**
	 * Registra un Estudiante
	 * @author Luis Angel Zavala
	 * @param estudiante @link{mx.com.fonyou.examen.modelo.entidad.Estudiante}
	 * @return estudiante @link{mx.com.fonyou.examen.modelo.entidad.Estudiante}
	 */
	public Estudiante registraEstudiante(Estudiante estudiante);
	
	/**
	 * Registra un Estudiante
	 * @author Luis Angel Zavala
	 * @param idEstudiante @link{Long}
	 * @return estudiante @link{mx.com.fonyou.examen.modelo.entidad.Estudiante}
	 */
	public Estudiante obtenEstudiante(Long idEstudiante);
	
}
