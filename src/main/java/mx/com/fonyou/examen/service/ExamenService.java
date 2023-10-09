/**
 * ExamenService.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.service;

import java.util.List;

import mx.com.fonyou.examen.exception.GestionExamenException;
import mx.com.fonyou.examen.modelo.catalogo.EstatusExamen;
import mx.com.fonyou.examen.modelo.entidad.Examen;
import mx.com.fonyou.examen.modelo.entidad.Opcion;
import mx.com.fonyou.examen.modelo.entidad.Pregunta;

/**
 * Interface para las funcionalidades del Examen
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
public interface ExamenService {

	/**
	 * Crea un examen de manera parcial
	 * @author Luis Angel Zavala
	 * @param examen @link{mx.com.fonyou.examen.modelo.entidad.Examen}
	 * @return examen @link{mx.com.fonyou.examen.modelo.entidad.Examen}
	 */
	public Examen crearExamen(Examen examen);
	
	/**
	 * Crea un examen de, el cual ya contiene sus preguntas y sus opciones multipples
	 * @author Luis Angel Zavala
	 * @param examen @link{mx.com.fonyou.examen.modelo.entidad.Examen}
	 * @return examen @link{mx.com.fonyou.examen.modelo.entidad.Examen}
	 */
	public Examen crearExamenCompleto(Examen examen) throws GestionExamenException;
	
	/**
	 * Cambia el estatus de un examen
	 * @author Luis Angel Zavala
	 * @param idExamen @link{mx.com.fonyou.examen.modelo.entidad.Examen}
	 * @param estatusExamen @link{mx.com.fonyou.examen.modelo.catalogo.EstatusExamen}
	 */
	public void cambiarEstatusExamen(Long idExamen, EstatusExamen estatusExamen);
	
	/**
	 * Registra y asigna una pregunta a un examen
	 * @author Luis Angel Zavala
	 * @param pregunta @link{mx.com.fonyou.examen.modelo.entidad.Pregunta}
	 * @return pregunta @link{mx.com.fonyou.examen.modelo.entidad.Pregunta}
	 */
	public Pregunta asignarPregunta(Pregunta pregunta) throws GestionExamenException;
	
	/**
	 * Modifica una pregunta
	 * @author Luis Angel Zavala
	 * @param pregunta @link{mx.com.fonyou.examen.modelo.entidad.Pregunta}
	 * @return pregunta @link{mx.com.fonyou.examen.modelo.entidad.Pregunta}
	 */
	public Pregunta modificaPregunta(Pregunta pregunta) throws GestionExamenException;
	
	/**
	 * Elimina una pregunta
	 * @author Luis Angel Zavala
	 * @param idPregunta @link{Long}
	 */
	public void eliminaPregunta(Long idPregunta) throws GestionExamenException;
	
	/**
	 * Registra y asigna una opcion a una pregunta previamente registrada
	 * @author Luis Angel Zavala
	 * @param opcion @link{mx.com.fonyou.examen.modelo.entidad.Opcion}
	 * @return opcion @link{mx.com.fonyou.examen.modelo.entidad.Opcion}
	 */
	public Opcion definirOpcion(Opcion opcion) throws GestionExamenException;
	
	/**
	 * Modifica una opcion
	 * @author Luis Angel Zavala
	 * @param opcion @link{mx.com.fonyou.examen.modelo.entidad.Opcion}
	 * @return opcion @link{mx.com.fonyou.examen.modelo.entidad.Opcion}
	 */
	public Opcion modificaOpcion(Opcion opcion) throws GestionExamenException;
	
	/**
	 * Elimina una opcion
	 * @author Luis Angel Zavala
	 * @param idOpcion @link{Long}
	 */
	public void eliminaOpcion(Long idOpcion) throws GestionExamenException;
	
	/**
	 * Cambia el estatus de un examen
	 * @author Luis Angel Zavala
	 * @param idExamen @link{Long}
	 * @param estatus @link{mx.com.fonyou.examen.modelo.catalogo.EstatusExamen}
	 * @return boolean @link{boolean}
	 */
	public boolean cambiaEstatusExamen(Long idExamen, EstatusExamen estatus) throws GestionExamenException;
	
	/**
	 * Obtiene los examenes con el estatus de CREADO
	 * @author Luis Angel Zavala
	 * @return listaExamen @link{java.util.List}
	 */
	public List<Examen> obtenExamenesCreados();
	
	/**
	 * Obtiene un examen con el estatus de DISPONIBLE
	 * @author Luis Angel Zavala
	 * @param idExamen @link{Long}
	 * @return examen @link{mx.com.fonyou.examen.modelo.entidad.Examen}
	 */
	public Examen obtenExamenDisponible(Long idExamen);
	
	/**
	 * Obtiene las preguntas asignadas a un examen
	 * @author Luis Angel Zavala
	 * @param idExamen @link{Long}
	 * @return listaPregunta @link{java.util.List}
	 */
	public List<Pregunta> obtenPreguntas(Long idExamen);
	
	/**
	 * Obtiene las opciones asignadas a una pregunta
	 * @author Luis Angel Zavala
	 * @param idPregunta @link{Long}
	 * @return listaPregunta @link{java.util.List}
	 */
	public List<Opcion> obtenOpciones(Long idPregunta);
	
}
