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
import mx.com.fonyou.examen.modelo.entidad.Estudiante;
import mx.com.fonyou.examen.test.TestConfiguration;

/**
 * Test que validan las peticiones Rest del modulo de estudiante
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Log4j2
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {TestConfiguration.class})
public class EstudianteRestTest {

	@Value("${url.servidor}")
	private String urlServidor;
	
	@Value("${puerto.servidor}")
	private String puertoServidor;
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * Test que valida el alta del estudiante por medio de una peticion Rest
	 * @author Luis Angel Zavala
	 */
	@Test
	public void altaEstudiante() {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Estudiante estudiante = new Estudiante();
        estudiante.setCiudad("JAPON");
        estudiante.setCorreoElectronico("correoinventado@outlook.com");
        estudiante.setNombre("PANCRACIO");
        estudiante.setEdad(99);
        estudiante.setZonaHoraria("Asia/Tokyo");
        
        HttpEntity<Estudiante> requestEntity = new HttpEntity<>(estudiante, headers);

        ResponseEntity<Estudiante> responseEntity = 
        		restTemplate.exchange("http://" + urlServidor + ":" + puertoServidor + "/estudiante/alta", 
        							  HttpMethod.POST, requestEntity, Estudiante.class);
		
		log.debug("Prueba de ejecución de testcase");
		log.debug("StatusCode: {}", responseEntity.getStatusCode());
		log.debug("Body: {}", responseEntity.getBody());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode() );
	}
}
