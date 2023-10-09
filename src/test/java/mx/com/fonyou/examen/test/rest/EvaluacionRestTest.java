/**
 * EstudianteRestTest.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.test.rest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;
import mx.com.fonyou.examen.modelo.entidad.AsignaExamen;
import mx.com.fonyou.examen.modelo.entidad.Estudiante;
import mx.com.fonyou.examen.modelo.entidad.Examen;
import mx.com.fonyou.examen.modelo.entidad.PreguntaExamen;
import mx.com.fonyou.examen.modelo.entidad.RespuestaExamen;
import mx.com.fonyou.examen.service.EstudianteService;
import mx.com.fonyou.examen.service.ExamenService;
import mx.com.fonyou.examen.test.TestConfiguration;

/**
 * Test que validan las peticiones Rest del modulo de evaluacion
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Log4j2
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {TestConfiguration.class})
public class EvaluacionRestTest {

	@Value("${url.servidor}")
	private String urlServidor;
	
	@Value("${puerto.servidor}")
	private String puertoServidor;
	
	@Autowired
	private EstudianteService estudianteService;
	
	@Autowired
	private ExamenService examenService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * Cambia el estatus de un examen a Disponible para poder ser aplicado
	 * @author Luis Angel Zavala
	 */
	@Test
	public void asignaExamen() {
		log.debug("Iniciando el Test para asignacion");
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        Estudiante estudiante = estudianteService.obtenEstudiante(1L);
        log.debug("Estudiante: {}", estudiante.getId());
        Examen examen = examenService.obtenExamenDisponible(1L);
        log.debug("Examen: {}", examen.getId());

        AsignaExamen asignaExamen = new AsignaExamen();
        asignaExamen.setIdEstudiante(estudiante.getId());
        asignaExamen.setIdExamen(examen.getId());
        log.debug("AsignaExamen: {}", asignaExamen);
		HttpEntity<AsignaExamen> requestEntity = new HttpEntity<>(asignaExamen, headers);
        ResponseEntity<AsignaExamen> responseEntity = 
        		restTemplate.exchange("http://" + urlServidor + ":" + puertoServidor + "/gestionExamen/asignar", 
        							  HttpMethod.POST, requestEntity, AsignaExamen.class);
        log.debug("StatusCode: {}", responseEntity.getStatusCode());
		log.debug("Body: {}", responseEntity.getBody());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode() );
	}
	
	@Test
	public void presentaExamen() {
		log.debug("Iniciando el examen tecnico");
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        Estudiante estudiante = estudianteService.obtenEstudiante(1L);
        log.debug("Estudiante: {}", estudiante.getId());
        Examen examen = examenService.obtenExamenDisponible(1L);
        log.debug("Examen: {}", examen.getId());
        
        RespuestaExamen respuestaExamen = new RespuestaExamen();
        respuestaExamen.setIdEstudiante(estudiante.getId());
        respuestaExamen.setIdExamen(examen.getId());
        List<PreguntaExamen> listaPreguntasExamen = new ArrayList<>();
        PreguntaExamen pregunta = new PreguntaExamen();
        pregunta.setId(11L);
        pregunta.setIdOpcion(32L);
        listaPreguntasExamen.add(pregunta);
        respuestaExamen.setPreguntas(listaPreguntasExamen);
        
        HttpEntity<RespuestaExamen> requestEntity = new HttpEntity<>(respuestaExamen, headers);
        ResponseEntity<RespuestaExamen> responseEntity = 
        		restTemplate.exchange("http://" + urlServidor + ":" + puertoServidor + "/gestionExamen/evaluar", 
        							  HttpMethod.POST, requestEntity, RespuestaExamen.class);
        log.debug("StatusCode: {}", responseEntity.getStatusCode());
		log.debug("Body: {}", responseEntity.getBody());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode() );
        
	}
}
