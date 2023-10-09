/**
 * ExamenRestTest.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.test.rest;

import static org.junit.Assert.assertEquals;
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
import mx.com.fonyou.examen.modelo.entidad.Examen;
import mx.com.fonyou.examen.modelo.entidad.Opcion;
import mx.com.fonyou.examen.modelo.entidad.Pregunta;
import mx.com.fonyou.examen.service.AuxiliarExamenService;
import mx.com.fonyou.examen.service.ExamenService;
import mx.com.fonyou.examen.test.TestConfiguration;

/**
 * Test que validan las peticiones Rest del modulo de examen
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Log4j2
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {TestConfiguration.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExamenRestTest {

	@Value("${url.servidor}")
	private String urlServidor;
	
	@Value("${puerto.servidor}")
	private String puertoServidor;
	
	@Value("${opciones.maximas.pregunta}")
	private Integer maximoOpciones; 
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	public ExamenService examenService;
	
	@Autowired
	private AuxiliarExamenService auxiliarExamenService;
	
	/**
	 * Test que valida la creacion de un examen por medio de una peticion Rest
	 * Ordenado mediante el nombre para ejecución de test futuros
	 * @author Luis Angel Zavala
	 */
	@Test
	public void ordenACrearExamen() {
		log.debug("Prueba de ejecución de testcase para crear Examen");
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        Calendar fechaInicio = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        fechaFin.set(Calendar.DAY_OF_MONTH, fechaInicio.get(Calendar.DAY_OF_MONTH) + 1);
        
        Examen examen = new Examen();
        examen.setNombre("Examen Tecnico de Java");
        examen.setFechaInicioExamen( new Timestamp(fechaInicio.getTimeInMillis()) );
        examen.setFechaFinExamen( new Timestamp(fechaFin.getTimeInMillis()) );

        HttpEntity<Examen> requestEntity = new HttpEntity<>(examen, headers);
        ResponseEntity<Examen> responseEntity = 
        		restTemplate.exchange("http://" + urlServidor + ":" + puertoServidor + "/examen/crear", 
        							  HttpMethod.POST, requestEntity, Examen.class);
		log.debug("StatusCode: {}", responseEntity.getStatusCode());
		log.debug("Body: {}", responseEntity.getBody());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode() );
	}
	
	/**
	 * Test que valida la creacion las preguntas de un examen por medio de una peticion Rest
	 * Ordenado mediante el nombre para ejecución de test futuros
	 * @author Luis Angel Zavala
	 */
	@Test
	public void ordenBCrearPregunta() {
		log.debug("Prueba de ejecución de testcase para crear Preguntas");
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        List<Examen> examenes = auxiliarExamenService.obtenExamenesSinPregunta();
        Examen examen = examenes.stream().findFirst().orElse(null);
        
        if(examen != null) {
        	Pregunta pregunta1 = new Pregunta();
            pregunta1.setDescripcion("Que es JDK?");
            pregunta1.setValor(10.0d);
            pregunta1.setIdExamen(examen.getId());
            
            Pregunta pregunta2 = new Pregunta();
            pregunta2.setDescripcion("Que es JRE?");
            pregunta2.setValor(10.0d);
            pregunta2.setIdExamen(examen.getId());
            
            HttpEntity<Pregunta> requestEntity1 = new HttpEntity<>(pregunta1, headers);

            ResponseEntity<Pregunta> responseEntity1 = 
            		restTemplate.exchange("http://" + urlServidor + ":" + puertoServidor + "/examen/agregaPregunta", 
            							  HttpMethod.POST, requestEntity1, Pregunta.class);
    		
            HttpEntity<Pregunta> requestEntity2 = new HttpEntity<>(pregunta2, headers);

            ResponseEntity<Pregunta> responseEntity2 = 
            		restTemplate.exchange("http://" + urlServidor + ":" + puertoServidor + "/examen/agregaPregunta", 
            							  HttpMethod.POST, requestEntity2, Pregunta.class);
            
    		
    		log.debug("StatusCode: {}", responseEntity1.getStatusCode());
    		log.debug("Body: {}", responseEntity1.getBody());
    		assertEquals(HttpStatus.OK, responseEntity1.getStatusCode() );
    		
    		log.debug("StatusCode: {}", responseEntity2.getStatusCode());
    		log.debug("Body: {}", responseEntity2.getBody());
    		assertEquals(HttpStatus.OK, responseEntity2.getStatusCode() );
        }
        
	}
	
	/**
	 * Test que valida la creacion las opciones de una pregunta por medio de una peticion Rest
	 * Ordenado mediante el nombre para ejecución de test futuros
	 * @author Luis Angel Zavala
	 */
	@Test
	public void ordenCCrearpciones() {
		log.debug("Prueba de ejecución de testcase para crear Opciones");
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        List<Pregunta> preguntas = auxiliarExamenService.obtenPreguntaSinOpciones();
        Pregunta pregunta = preguntas.stream().findFirst().orElse(null);
        
        if(pregunta != null) {
        	for(int i = 0; i<=2; i++) {
            	Opcion opcion = new Opcion();
                opcion.setDescripcion("Opcion incorrecta " + i);
                opcion.setCorrecta( false );
                opcion.setIdPregunta(pregunta.getId());
                HttpEntity<Opcion> requestEntity = new HttpEntity<>(opcion, headers);
                ResponseEntity<Opcion> responseEntity = 
                		restTemplate.exchange("http://" + urlServidor + ":" + puertoServidor + "/examen/agregarOpcion", 
                							  HttpMethod.POST, requestEntity, Opcion.class);
        		log.debug("StatusCode: {}", responseEntity.getStatusCode());
        		log.debug("Body: {}", responseEntity.getBody());
        		assertEquals(HttpStatus.OK, responseEntity.getStatusCode() );
            }
            Opcion opcion = new Opcion();
            opcion.setDescripcion("Java Development Kit");
            opcion.setCorrecta( true );
            opcion.setIdPregunta(pregunta.getId());
            HttpEntity<Opcion> requestEntity = new HttpEntity<>(opcion, headers);
            ResponseEntity<Opcion> responseEntity = 
            		restTemplate.exchange("http://" + urlServidor + ":" + puertoServidor + "/examen/agregarOpcion", 
            							  HttpMethod.POST, requestEntity, Opcion.class);
    		log.debug("StatusCode: {}", responseEntity.getStatusCode());
    		log.debug("Body: {}", responseEntity.getBody());
    		assertEquals(HttpStatus.OK, responseEntity.getStatusCode() );
        }

	}
	
	/**
	 * Test que valida la creacion total de un examen por medio de una peticion Rest
	 * es decir, el objeto Examen contiene la lista de las Preguntas y estas a su vez contiene la lista de las Opciones multiples
	 * @author Luis Angel Zavala
	 */
	@Test
	public void ordenDCrearExamenCompleto() {
		log.debug("Prueba de ejecución de testcase para crear Examen Completo");
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
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
        log.debug("Examen: {}", examen);
        HttpEntity<Examen> requestEntity = new HttpEntity<>(examen, headers);
        ResponseEntity<Examen> responseEntity = 
        		restTemplate.exchange("http://" + urlServidor + ":" + puertoServidor + "/examen/crearCompleto", 
        							  HttpMethod.POST, requestEntity, Examen.class);
		log.debug("StatusCode: {}", responseEntity.getStatusCode());
		log.debug("Body: {}", responseEntity.getBody());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode() );
	}
	
	/**
	 * Test que valida la actualizacion de una pregunta por medio de una peticion Rest
	 * @author Luis Angel Zavala
	 */	
	@Test
	public void modificaPregunta() {
		log.debug("Prueba de ejecución de testcase para modificar una pregunta");
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        List<Examen> examenes = auxiliarExamenService.obtenExamenesSinPregunta();
        Examen examen = examenes.stream().findFirst().orElse(null);
        Pregunta preguntaGuardada = null;
        if(examen != null) {
        	Pregunta pregunta = new Pregunta();
            pregunta.setDescripcion("Que es JDK?");
            pregunta.setValor(10.0d);
            pregunta.setIdExamen(examen.getId());
            preguntaGuardada = examenService.asignarPregunta(pregunta);
            log.debug("Pregunta guardada: {}", preguntaGuardada);
            assertNotNull(preguntaGuardada);
        }
        preguntaGuardada.setDescripcion("Modificacion");
        HttpEntity<Pregunta> requestEntity = new HttpEntity<>(preguntaGuardada, headers);
        ResponseEntity<Pregunta> responseEntity = 
        		restTemplate.exchange("http://" + urlServidor + ":" + puertoServidor + "/examen/modificaPregunta", 
        							  HttpMethod.POST, requestEntity, Pregunta.class);
        log.debug("StatusCode: {}", responseEntity.getStatusCode());
		log.debug("Body: {}", responseEntity.getBody());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode() );
	}
	
	/**
	 * Test que valida la eliminacion de una pregunta con sus opciones por medio de una peticion Rest
	 * @author Luis Angel Zavala
	 */	
	@Test
	public void eliminaPregunta() {
		log.debug("Prueba de ejecución de testcase para eliminar una pregunta");
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        List<Examen> examenes = auxiliarExamenService.obtenExamenesSinPregunta();
        Examen examen = examenes.stream().findFirst().orElse(null);
        Pregunta preguntaGuardada = null;
        if(examen != null) {
        	Pregunta pregunta = new Pregunta();
            pregunta.setDescripcion("Que es JDK?");
            pregunta.setValor(10.0d);
            pregunta.setIdExamen(examen.getId());
            preguntaGuardada = examenService.asignarPregunta(pregunta);
            log.debug("Pregunta guardada: {}", preguntaGuardada);
            assertNotNull(preguntaGuardada);
        }
        
        HttpEntity<Pregunta> requestEntity = new HttpEntity<>(preguntaGuardada, headers);
        ResponseEntity<Pregunta> responseEntity = 
        		restTemplate.exchange("http://" + urlServidor + ":" + puertoServidor + "/examen/eliminaPregunta", 
        							  HttpMethod.POST, requestEntity, Pregunta.class);
        log.debug("StatusCode: {}", responseEntity.getStatusCode());
		log.debug("Body: {}", responseEntity.getBody());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode() );
	}
	
	/**
	 * Test que valida la modificacion de una opcion por medio de una peticion Rest
	 * @author Luis Angel Zavala
	 */
	@Test
	public void modificaOpcion() {
		log.debug("Prueba de ejecución de testcase para modificar una opcion");
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        List<Pregunta> preguntas = auxiliarExamenService.obtenPreguntaSinOpciones();
        Pregunta pregunta = preguntas.stream().findFirst().orElse(null);
        Opcion opcionGuardada = null;
        if(pregunta != null) {
        	Opcion opcion = new Opcion();
            opcion.setDescripcion("A Opcion");
            opcion.setCorrecta( false );
            opcion.setIdPregunta(pregunta.getId());
            opcionGuardada = examenService.definirOpcion(opcion);
            log.debug("Opcion guardada: {}", opcionGuardada);
            assertNotNull(opcionGuardada);
        }
        opcionGuardada.setDescripcion("Descripcion nueva");
        HttpEntity<Opcion> requestEntity = new HttpEntity<>(opcionGuardada, headers);
        ResponseEntity<Opcion> responseEntity = 
        		restTemplate.exchange("http://" + urlServidor + ":" + puertoServidor + "/examen/modificaOpcion", 
        							  HttpMethod.POST, requestEntity, Opcion.class);
        log.debug("StatusCode: {}", responseEntity.getStatusCode());
		log.debug("Body: {}", responseEntity.getBody());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode() );
	}
	
	/**
	 * Test que valida la eliminacion de una opcion por medio de una peticion Rest
	 * @author Luis Angel Zavala
	 */
	@Test
	public void eliminaOpcion() {
		log.debug("Prueba de ejecución de testcase para eliminar una opcion");
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        List<Pregunta> preguntas = auxiliarExamenService.obtenPreguntaSinOpciones();
        Pregunta pregunta = preguntas.stream().findFirst().orElse(null);
        Opcion opcionGuardada = null;
        if(pregunta != null) {
        	Opcion opcion = new Opcion();
            opcion.setDescripcion("A Opcion");
            opcion.setCorrecta( false );
            opcion.setIdPregunta(pregunta.getId());
            opcionGuardada = examenService.definirOpcion(opcion);
            log.debug("Opcion guardada: {}", opcionGuardada);
            assertNotNull(opcionGuardada);
        }
        HttpEntity<Opcion> requestEntity = new HttpEntity<>(opcionGuardada, headers);
        ResponseEntity<Opcion> responseEntity = 
        		restTemplate.exchange("http://" + urlServidor + ":" + puertoServidor + "/examen/eliminaOpcion", 
        							  HttpMethod.POST, requestEntity, Opcion.class);
        log.debug("StatusCode: {}", responseEntity.getStatusCode());
		log.debug("Body: {}", responseEntity.getBody());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode() );
	}
	
	/**
	 * Cambia el estatus de un examen a Disponible para poder ser aplicado
	 * @author Luis Angel Zavala
	 */
	@Test
	public void cambiarDisponible() {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
		List<Examen> examenes = auxiliarExamenService.obtenExamenesSinPregunta();
        Examen examen = examenes.stream().findFirst().orElse(null);

		HttpEntity<Examen> requestEntity = new HttpEntity<>(examen, headers);
        ResponseEntity<Examen> responseEntity = 
        		restTemplate.exchange("http://" + urlServidor + ":" + puertoServidor + "/examen/cambiarExamenDisponible", 
        							  HttpMethod.POST, requestEntity, Examen.class);
        log.debug("StatusCode: {}", responseEntity.getStatusCode());
		log.debug("Body: {}", responseEntity.getBody());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode() );
		
	}
}
