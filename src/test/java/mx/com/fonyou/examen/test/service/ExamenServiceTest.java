/**
 * ExamenServiceTest.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.test.service;

import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import mx.com.fonyou.examen.modelo.catalogo.EstatusExamen;
import mx.com.fonyou.examen.modelo.entidad.Examen;
import mx.com.fonyou.examen.modelo.entidad.Opcion;
import mx.com.fonyou.examen.modelo.entidad.Pregunta;
import mx.com.fonyou.examen.service.AuxiliarExamenService;
import mx.com.fonyou.examen.service.ExamenService;
import mx.com.fonyou.examen.test.TestConfiguration;

/**
 * Test que validan los Servicios del modulo de examen
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Log4j2
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {TestConfiguration.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class ExamenServiceTest {

	@Value("${opciones.maximas.pregunta}")
	private Integer maximoOpciones; 
	
	@Autowired
	public ExamenService examenService;
	
	@Autowired
	private AuxiliarExamenService auxiliarExamenService;
	
	/**
	 * Test que valida la creacion parcial de un examen
	 * Ordenado por nombre con fines de ejecución de los siguientes test
	 * @author Luis Angel Zavala
	 */
	@Test
	public void ordenACrearExamen() {
		Calendar fechaInicio = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        fechaFin.set(Calendar.DAY_OF_MONTH, fechaInicio.get(Calendar.DAY_OF_MONTH) + 1);
		Examen examen = new Examen();
		examen.setNombre("Examen de Prueba");
		examen.setFechaInicioExamen( new Timestamp(fechaInicio.getTimeInMillis()) );
        examen.setFechaFinExamen( new Timestamp(fechaFin.getTimeInMillis()) );
		examen.setValorExamen(0d);
		examen.setEstatusExamen(EstatusExamen.CREADO);
		Examen examenGuardado = examenService.crearExamen( examen );
		log.debug("Examen guardado: {}", examenGuardado);
		assertNotNull(examenGuardado);
	}
	
	/**
	 * Test que valida la creacion de una pregunta asignada a un examen
	 * Ordenado por nombre con fines de ejecución de los siguientes test
	 * @author Luis Angel Zavala
	 */
	@Test
	public void ordenBCrearPregunta() {
		List<Examen> examenes = auxiliarExamenService.obtenExamenesSinPregunta();
        Examen examen = examenes.stream().findFirst().orElse(null);
        if(examen != null) {
        	Pregunta pregunta = new Pregunta();
            pregunta.setDescripcion("Que es JDK?");
            pregunta.setValor(10.0d);
            pregunta.setIdExamen(examen.getId());
            Pregunta preguntaGuardada = examenService.asignarPregunta(pregunta);
            log.debug("Pregunta guardada: {}", preguntaGuardada);
            assertNotNull(preguntaGuardada);
        }
	}
	
	/**
	 * Test que valida la creacion de una opcion asignada a una pregunta
	 * Ordenado por nombre con fines de ejecución de los siguientes test
	 * @author Luis Angel Zavala
	 */
	@Test
	public void ordenCCrearOpcion() {
		List<Pregunta> preguntas = auxiliarExamenService.obtenPreguntaSinOpciones();
        Pregunta pregunta = preguntas.stream().findFirst().orElse(null);
        
        if(pregunta != null) {
        	Opcion opcion = new Opcion();
            opcion.setDescripcion("A Opcion");
            opcion.setCorrecta( false );
            opcion.setIdPregunta(pregunta.getId());
            Opcion opcionGuardada = examenService.definirOpcion(opcion);
            log.debug("Opcion guardada: {}", opcionGuardada);
            assertNotNull(opcionGuardada);
        }
	}
	
	/**
	 * Test que valida la creacion completa de un examen
	 * @author Luis Angel Zavala
	 */
	@Test
	public void crearExamenCompleto() {
		Calendar fechaInicio = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        fechaFin.set(Calendar.DAY_OF_MONTH, fechaInicio.get(Calendar.DAY_OF_MONTH) + 1);
        
		Examen examen = new Examen();
        examen.setNombre("Examen Tecnico de Spring");
        examen.setFechaInicioExamen( new Timestamp(fechaInicio.getTimeInMillis()) );
        examen.setFechaFinExamen( new Timestamp(fechaFin.getTimeInMillis()) );
        
        List<Pregunta> preguntas = new ArrayList<>();
        for(int i=0; i < maximoOpciones; i++) {
        	Pregunta pregunta = new Pregunta();
            pregunta.setDescripcion( i +") Pregunta de examen");
            pregunta.setValor(20.0d);
            preguntas.add(pregunta);
        }

        for(Pregunta pregunta : preguntas) {
        	List<Opcion> opciones = new ArrayList<>();
        	boolean respuestaCorrecta = true;
        	for(int i=0; i < maximoOpciones; i++) {
        		Opcion opcion = new Opcion();
                opcion.setDescripcion("Opcion " + i);
                opcion.setCorrecta( respuestaCorrecta );
                respuestaCorrecta = false;
                opciones.add(opcion);
        	}
        	pregunta.setOpciones(opciones);
        }        
        examen.setPreguntas(preguntas);
        
        Examen examenGuardado = examenService.crearExamen( examen );
		log.debug("Examen guardado: {}", examenGuardado);
		assertNotNull(examenGuardado);
	}
	
	/**
	 * Test que valida la modificacion de una pregunta y una opcion
	 * @author Luis Angel Zavala
	 */
	@Test
	public void modificaPreguntaOpcion() {
		List<Examen> examenes = examenService.obtenExamenesCreados();
		Examen examen = examenes.stream().findFirst().orElse(null);
		List<Pregunta> preguntas = examenService.obtenPreguntas(examen.getId());
		Pregunta pregunta = preguntas.stream().findFirst().orElse(null);
		if(pregunta != null) {
			pregunta.setDescripcion("Nueva Descripcion");
			Pregunta preguntaModificada = examenService.modificaPregunta(pregunta);
			log.debug("Pregunta modificada: {}", preguntaModificada);
			assertNotNull(preguntaModificada);
			List<Opcion> opciones = examenService.obtenOpciones(preguntaModificada.getId());
			Opcion opcion = opciones.stream().findFirst().orElse(null);
			if(opcion != null) {
				opcion.setDescripcion("Descripcion nueva");
				Opcion opcionModificada = examenService.modificaOpcion(opcion);
				log.debug("Opcion modificada: {}", opcionModificada);
				assertNotNull(opcionModificada);
			}
		}
	}
	
	/**
	 * Test que valida la eliminacion de una pregunta y una opcion
	 * @author Luis Angel Zavala
	 */
	@Test
	public void eliminaPreguntaOpcion() {
		List<Examen> examenes = examenService.obtenExamenesCreados();
		Examen examen = examenes.stream().findFirst().orElse(null);
		List<Pregunta> preguntas = examenService.obtenPreguntas(examen.getId());
		Pregunta pregunta = preguntas.stream().findFirst().orElse(null);
		assertNotNull(pregunta);
		if(pregunta != null) {
			List<Opcion> opciones = examenService.obtenOpciones(pregunta.getId());
			Opcion opcion = opciones.stream().findFirst().orElse(null);
			if(opcion != null) {
				assertNotNull(opcion);
				examenService.eliminaOpcion(opcion.getId());
				log.debug("Opcion Eliminada");
			}
			examenService.eliminaPregunta(pregunta.getId());
			log.debug("Pregunta Eliminada");
		}
	}
}