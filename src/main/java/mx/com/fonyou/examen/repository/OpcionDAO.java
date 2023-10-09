/**
 * OpcionDAO.java
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

import mx.com.fonyou.examen.modelo.entidad.Opcion;

/**
 * Clase para gestionar el CRUD de Opcion
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
public interface OpcionDAO extends JpaRepository<Opcion, Long> {

	/**
	 * Ejecuta un @link{org.springframework.data.jpa.repository.Query} customizado e independiente a 
	 * @link{org.springframework.data.jpa.repository.JpaRepository}
	 * @author Luis Angel Zavala
	 * @param idPregunta @link{Long}
	 * @return listaOpcion @link{java.util.List}
	 */
	@Query("SELECT o FROM Opcion o WHERE o.idPregunta = :idPregunta")
	public List<Opcion> buscarOpciones(Long idPregunta);
}
