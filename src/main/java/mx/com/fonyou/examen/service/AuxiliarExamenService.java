/**
 * AuxiliarExamenService.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.service;

import java.util.List;

import mx.com.fonyou.examen.modelo.entidad.Examen;
import mx.com.fonyou.examen.modelo.entidad.Pregunta;

/**
 * Interface para las funcionalidades auxiliares del servicio de Examen
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
public interface AuxiliarExamenService {
	
	/**
	 * Obtiene una lista de Examenes que no tengan preguntas definidas
	 * @author Luis Angel Zavala
	 * @return listaExamen  @link{java.util.List}
	 */
	public List<Examen> obtenExamenesSinPregunta();
	
	/**
	 * Obtiene una lista de Preguntas que no tengan opciones definidas
	 * @author Luis Angel Zavala
	 * @return listaExamen  @link{java.util.List}
	 */
	public List<Pregunta> obtenPreguntaSinOpciones();

}
