/**
 * EvaluacionService.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.service;

import mx.com.fonyou.examen.exception.GestionExamenException;
import mx.com.fonyou.examen.modelo.entidad.Evaluacion;
import mx.com.fonyou.examen.modelo.entidad.RespuestaExamen;

/**
 * Interface para las funcionalidades de la Evaluacion
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
public interface EvaluacionService {

	/**
	 * Asigna un Estudiante a un examen que esta en @link{mx.com.fonyou.examen.modelo.catalogo.EstatusExamen} de DISPONIBLE
	 * @author Luis Angel Zavala
	 * @param idEstudiante @link{Long}
	 * @param idExamen @link{Long}
	 */
	public void asignaExamen(Long idEstudiante, Long idExamen) throws GestionExamenException;
	
	/**
	 * Genera la evaluacion del examen que presento un estudiante
	 * @author Luis Angel Zavala
	 * @param respuestas @link{mx.com.fonyou.examen.modelo.entidad.RespuestaExamen}
	 * @return evaluacion @link{mx.com.fonyou.examen.modelo.entidad.Evaluacion}
	 */
	public Evaluacion generaEvaluacion(RespuestaExamen respuestas);
	
	/**
	 * Valida la fecha del examen con respecto a la zona horaria del estudiante para saber si deberia de presentarlo
	 * @author Luis Angel Zavala
	 * @param idEstudiante @link{Long}
	 * @param idExamen @link{Long}
	 * @return boolean @link{boolean}
	 */
	public boolean validaFechaEvaluacion(Long idEstudiante, Long idExamen);
	
}
