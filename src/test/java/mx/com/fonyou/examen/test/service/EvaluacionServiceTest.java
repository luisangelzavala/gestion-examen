/**
 * EvaluacionServiceTest.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.test.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import mx.com.fonyou.examen.modelo.entidad.Estudiante;
import mx.com.fonyou.examen.modelo.entidad.Evaluacion;
import mx.com.fonyou.examen.modelo.entidad.Examen;
import mx.com.fonyou.examen.modelo.entidad.PreguntaExamen;
import mx.com.fonyou.examen.modelo.entidad.RespuestaExamen;
import mx.com.fonyou.examen.service.EstudianteService;
import mx.com.fonyou.examen.service.EvaluacionService;
import mx.com.fonyou.examen.service.ExamenService;
import mx.com.fonyou.examen.test.TestConfiguration;

/**
 * Test que validan los Servicios del modulo de evaluacion
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Log4j2
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {TestConfiguration.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class EvaluacionServiceTest {
	
	@Autowired
	private EvaluacionService evaluacionService;
	
	@Autowired
	private ExamenService examenService;
	
	@Autowired
	private EstudianteService estudianteService;
	
	/**
	 * Test que valida la asignacion de un estudiante a un examen
	 * @author Luis Angel Zavala
	 */
	@Ignore
	@Test
	public void asignaExamen() {
		log.debug("Asigna examen al alumno");
		Examen examen = examenService.obtenExamenDisponible(2L);
		Estudiante estudiante = estudianteService.obtenEstudiante(1L);
		assertNotNull(examen);
		assertNotNull(estudiante);
		evaluacionService.asignaExamen(estudiante.getId(), examen.getId());
	}
	
	
	@Test
	public void validaHorarios() {
		Examen examen = examenService.obtenExamenDisponible(2L);
		Estudiante estudiante = estudianteService.obtenEstudiante(1L);
		assertNotNull(examen);
		assertNotNull(estudiante);
		
		evaluacionService.validaFechaEvaluacion(estudiante.getId(), examen.getId());
	}
	
	
	/**
	 * Test que genera la calificacion de un estudiante que presento un examen
	 * @author Luis Angel Zavala
	 */
	@Ignore
	@Test
	public void calificaExamen() {
		log.debug("Califica el examen del alumno");
		Examen examen = examenService.obtenExamenDisponible(2L);
		Estudiante estudiante = estudianteService.obtenEstudiante(1L);
		assertNotNull(examen);
		assertNotNull(estudiante);
		
		RespuestaExamen respuestaExamen = new RespuestaExamen();
        respuestaExamen.setIdEstudiante(estudiante.getId());
        respuestaExamen.setIdExamen(examen.getId());
        List<PreguntaExamen> listaPreguntasExamen = new ArrayList<>();
        PreguntaExamen pregunta = new PreguntaExamen();
        pregunta.setId(11L);
        pregunta.setIdOpcion(32L);
        listaPreguntasExamen.add(pregunta);
        respuestaExamen.setPreguntas(listaPreguntasExamen);
        
        Evaluacion evaluacion = evaluacionService.generaEvaluacion(respuestaExamen);
        log.debug("Evaluacion: {}", evaluacion);
        assertNotNull(evaluacion);
		
	}

}
