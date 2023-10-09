/**
 * EstudianteController.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mx.com.fonyou.examen.modelo.entidad.Estudiante;
import mx.com.fonyou.examen.service.EstudianteService;

/**
 * Rest Controller para las funcionalidades del modulo de Estudiante
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@RestController
@RequestMapping("/estudiante")
public class EstudianteController {

	@Autowired
	private EstudianteService estudianteService;
	
	/**
	 * Punto de entrada de la peticion rest para dar de alta a un estudiante
	 * @param estudiante @link{mx.com.fonyou.examen.modelo.entidad.Estudiante}
	 * @return response @link{org.springframework.http.ResponseEntity}
	 */
	@PostMapping("/alta")
	public ResponseEntity<Estudiante> alta(@RequestBody @Valid Estudiante estudiante) {
		Estudiante nuevoEstudiante = estudianteService.registraEstudiante(estudiante);
		return new ResponseEntity<>(nuevoEstudiante, HttpStatus.OK);
	}
}
