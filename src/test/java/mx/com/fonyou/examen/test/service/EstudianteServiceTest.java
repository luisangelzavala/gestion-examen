/**
 * EstudianteServiceTest.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.test.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import mx.com.fonyou.examen.modelo.entidad.Estudiante;
import mx.com.fonyou.examen.service.EstudianteService;
import mx.com.fonyou.examen.test.TestConfiguration;

/**
 * Test que validan los Servicios del modulo de estudiante
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Log4j2
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {TestConfiguration.class})
@Transactional
public class EstudianteServiceTest {

	@Autowired
	private EstudianteService estudianteService;
	
	/**
	 * Test que valida la creacion de un estudiante medienta su servicio
	 * @author Luis Angel Zavala
	 */
	@Test
	public void agregarEstudiante() {
		Estudiante estudiante = new Estudiante();
		estudiante.setNombre("EDUARDO");
		estudiante.setPrimerApellido("GARCIA");
		estudiante.setSegundoApellido("LOPEZ");
		estudiante.setEdad(20);
		estudiante.setCiudad("Ciudad de Mexico");
		estudiante.setCorreoElectronico("correo@inventado.com");
		estudiante.setZonaHoraria("Asia/Tokyo");
		Estudiante estudianteGuardado = estudianteService.registraEstudiante(estudiante);
		log.debug("Estudiante guardado: {}", estudianteGuardado);
		assertNotNull(estudianteGuardado);
	}
	
}
