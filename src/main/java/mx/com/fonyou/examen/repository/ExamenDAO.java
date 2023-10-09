/**
 * ExamenDAO.java
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

import mx.com.fonyou.examen.modelo.catalogo.EstatusExamen;
import mx.com.fonyou.examen.modelo.entidad.Examen;

/**
 * Clase para gestionar el CRUD de Examen
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
public interface ExamenDAO extends JpaRepository<Examen, Long> {

	/**
	 * Ejecuta un @link{org.springframework.data.jpa.repository.Query} customizado e independiente a 
	 * @link{org.springframework.data.jpa.repository.JpaRepository}
	 * @author Luis Angel Zavala
	 * @return listaExamen @link{java.util.List}
	 */
	@Query("SELECT e FROM Examen e WHERE e.valorExamen = 0")
    public List<Examen> buscarExamenVacio();
	
	/**
	 * Ejecuta un @link{org.springframework.data.jpa.repository.Query} customizado e independiente a 
	 * @link{org.springframework.data.jpa.repository.JpaRepository}
	 * @author Luis Angel Zavala
	 * @param estatusExamen @link{mx.com.fonyou.examen.modelo.catalogo.EstatusExamen}
	 * @return listaExamen @link{java.util.List}
	 */
	@Query("SELECT e FROM Examen e WHERE e.estatusExamen = :estatusExamen")
	public List<Examen> buscaExamenByEstatus(EstatusExamen estatusExamen);
	
	/**
	 * Ejecuta un @link{org.springframework.data.jpa.repository.Query} customizado e independiente a 
	 * @link{org.springframework.data.jpa.repository.JpaRepository}
	 * @author Luis Angel Zavala
	 * @param estatusExamen @link{mx.com.fonyou.examen.modelo.catalogo.EstatusExamen}
	 * @return listaExamen @link{java.util.List}
	 */
	@Query("SELECT e FROM Examen e WHERE e.id = :id AND e.estatusExamen = :estatusExamen")
	public Examen buscaExamenByIdAndByEstatus(Long id, EstatusExamen estatusExamen);
	
}
