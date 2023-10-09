/**
 * EstudianteDAO.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.repository;

import org.springframework.data.repository.CrudRepository;

import mx.com.fonyou.examen.modelo.entidad.Estudiante;

/**
 * Clase para gestionar el CRUD de Estudiante
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
public interface EstudianteDAO extends CrudRepository<Estudiante, Long> {

	/**
	 * La clase no necesida definición de metodos ya que son implementados por 
	 * @link{org.springframework.data.repository.CrudRepository}
	 */
	
}
