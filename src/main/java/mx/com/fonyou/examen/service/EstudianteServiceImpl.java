/**
 * EstudianteServiceImpl.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import mx.com.fonyou.examen.exception.GestionExamenException;
import mx.com.fonyou.examen.modelo.entidad.Estudiante;
import mx.com.fonyou.examen.repository.EstudianteDAO;

/**
 * Implementación de la interface el servicio de Estudiante
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Log4j2
@Service("estudianteService")
public class EstudianteServiceImpl implements EstudianteService {

	@Autowired
	private EstudianteDAO estudianteDAO;
	
	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.EstudianteService#registraEstudiante(mx.com.fonyou.examen.modelo.entidad.Estudiante)
	 */
	@Override
	@Transactional
	public Estudiante registraEstudiante(Estudiante estudiante) throws IllegalArgumentException {
		log.info("Registrando Estudiante");
		Estudiante estudianteRegistrado = estudianteDAO.save(estudiante);
		log.debug("Estudiante: {}", estudianteRegistrado);
		return estudianteRegistrado;
	}

	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.EstudianteService#obtenEstudiante(Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public Estudiante obtenEstudiante(Long idEstudiante) {
		Optional<Estudiante> estudiante = estudianteDAO.findById(idEstudiante);
		if(estudiante.isPresent()) {
			return estudiante.get();
		}
		throw new GestionExamenException("El estudiante que intenta obtener no existe");
	}

}
