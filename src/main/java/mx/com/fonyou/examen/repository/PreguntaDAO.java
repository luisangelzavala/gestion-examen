/**
 * PreguntaDAO.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.com.fonyou.examen.modelo.entidad.Pregunta;

/**
 * Clase para gestionar el CRUD de Pregunta
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
public interface PreguntaDAO extends JpaRepository<Pregunta, Long> {

	/**
	 * Ejecuta un @link{org.springframework.data.jpa.repository.Query} customizado e independiente a 
	 * @link{org.springframework.data.jpa.repository.JpaRepository}
	 * @author Luis Angel Zavala
	 * @return listaPregunta @link{java.util.List}
	 */
	@Query("SELECT p FROM Pregunta p WHERE NOT EXISTS (SELECT 1 FROM Opcion o WHERE o.idPregunta = p.id)")
	public List<Pregunta> buscarPreguntaVacia();
	
	/**
	 * Ejecuta un @link{org.springframework.data.jpa.repository.Query} customizado e independiente a 
	 * @link{org.springframework.data.jpa.repository.JpaRepository}
	 * @author Luis Angel Zavala
	 * @param idExamen @link{Long}
	 * @return listaPregunta @link{java.util.List}
	 */
	@Query("SELECT p FROM Pregunta p WHERE p.idExamen = :idExamen")
	public List<Pregunta> buscarPreguntas(Long idExamen);
	
}
