/**
 * ExamenController.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import mx.com.fonyou.examen.exception.GestionExamenException;
import mx.com.fonyou.examen.modelo.catalogo.EstatusExamen;
import mx.com.fonyou.examen.modelo.entidad.Examen;
import mx.com.fonyou.examen.modelo.entidad.Opcion;
import mx.com.fonyou.examen.modelo.entidad.Pregunta;
import mx.com.fonyou.examen.modelo.entidad.handler.CustomSuccessResponse;
import mx.com.fonyou.examen.service.ExamenService;

/**
 * Rest Controller para las funcionalidades del modulo de Examen
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Log4j2
@RestController
@RequestMapping("/examen")
public class ExamenController {

	@Autowired
	private ExamenService examenService;
	
	/**
	 * Punto de entrada de la peticion rest para dar de alta un examen de manera parcial
	 * @param examen @link{mx.com.fonyou.examen.modelo.entidad.Examen}
	 * @return response @link{org.springframework.http.ResponseEntity}
	 */
	@PostMapping("/crear")
	public ResponseEntity<Examen> crear(@RequestBody @Valid Examen examen) {
		log.debug("Creando un examen nuevo de manera parcial");
		Examen examenNuevo = examenService.crearExamen(examen);
		return new ResponseEntity<>(examenNuevo, HttpStatus.OK);
	}
	
	/**
	 * Punto de entrada de la peticion rest para dar de alta un examen completo
	 * @param examen @link{mx.com.fonyou.examen.modelo.entidad.Examen}
	 * @return response @link{org.springframework.http.ResponseEntity}
	 */
	@PostMapping("/crearCompleto")
	public ResponseEntity<Examen> crearCompleto(@RequestBody @Valid Examen examen) {
		log.debug("Creando un examen nuevo");
		Examen examenNuevo = examenService.crearExamenCompleto(examen);
		return new ResponseEntity<>(examenNuevo, HttpStatus.OK);
	}
	
	/**
	 * Punto de entrada de la peticion rest para dar de alta una pretunta y asignarla a un examen
	 * @param pregunta @link{mx.com.fonyou.examen.modelo.entidad.Pregunta}
	 * @return response @link{org.springframework.http.ResponseEntity}
	 */
	@PostMapping("/agregaPregunta")
	public ResponseEntity<Pregunta> agregaPregunta(@RequestBody @Valid Pregunta pregunta) throws GestionExamenException {
		log.debug("Agregar una pregunta a un examen");
		Pregunta preguntaNueva = examenService.asignarPregunta(pregunta);
		return new ResponseEntity<>(preguntaNueva, HttpStatus.OK);
	}
	
	/**
	 * Punto de entrada de la peticion rest para modificar una pretunta
	 * @param pregunta @link{mx.com.fonyou.examen.modelo.entidad.Pregunta}
	 * @return response @link{org.springframework.http.ResponseEntity}
	 */
	@PostMapping("/modificaPregunta")
	public ResponseEntity<Pregunta> modificaPregunta(@RequestBody @Valid Pregunta pregunta) throws GestionExamenException {
		log.debug("Modifica una pregunta a un examen");
		Pregunta preguntaNueva = examenService.modificaPregunta(pregunta);
		return new ResponseEntity<>(preguntaNueva, HttpStatus.OK);
	}
	
	/**
	 * Punto de entrada de la peticion rest para eliminar una pretunta
	 * @param idPregunta @link{Long}
	 * @return response @link{org.springframework.http.ResponseEntity}
	 */
	@PostMapping("/eliminaPregunta")
	public ResponseEntity<CustomSuccessResponse> eliminaPregunta(@RequestBody @Valid Pregunta pregunta) throws GestionExamenException {
		log.debug("Eliminar una pregunta de un examen");
		examenService.eliminaPregunta(pregunta.getId());
		CustomSuccessResponse response = new CustomSuccessResponse(LocalDateTime.now(), HttpStatus.OK.value(), "Pregunta eliminada correctamente");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Punto de entrada de la peticion rest para dar de alta una opcion y asignarla a una pregunta
	 * @param pregunta @link{mx.com.fonyou.examen.modelo.entidad.Pregunta}
	 * @return response @link{org.springframework.http.ResponseEntity}
	 */
	@PostMapping("/agregarOpcion")
	public ResponseEntity<Opcion> agregaOpcion(@RequestBody @Valid Opcion opcion) throws GestionExamenException {
		log.debug("Agregar una opcion a una pregunta");
		Opcion opcionNueva = examenService.definirOpcion(opcion);
		return new ResponseEntity<>(opcionNueva, HttpStatus.OK);
	}
	
	/**
	 * Punto de entrada de la peticion rest para modificar una opcion
	 * @param opcion @link{mx.com.fonyou.examen.modelo.entidad.Opcion}
	 * @return response @link{org.springframework.http.ResponseEntity}
	 */
	@PostMapping("/modificaOpcion")
	public ResponseEntity<Opcion> modificaOpcion(@RequestBody @Valid Opcion opcion) throws GestionExamenException {
		log.debug("Modifica una opcion a una pregunta");
		Opcion opcionNueva = examenService.modificaOpcion(opcion);
		return new ResponseEntity<>(opcionNueva, HttpStatus.OK);
	}
	
	/**
	 * Punto de entrada de la peticion rest para eliminar una opcion
	 * @param idOpcion @link{Long}
	 * @return response @link{org.springframework.http.ResponseEntity}
	 */
	@PostMapping("/eliminaOpcion")
	public ResponseEntity<CustomSuccessResponse> eliminaOpcion(@RequestBody @Valid Opcion opcion) throws GestionExamenException {
		log.debug("Eliminar una opcion de una pregunta");
		examenService.eliminaOpcion(opcion.getId());
		CustomSuccessResponse response = new CustomSuccessResponse(LocalDateTime.now(), HttpStatus.OK.value(), "Opcion eliminada correctamente");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Punto de entrada de la peticion rest para cambiar el estatus de un examen en estatus de disponible
	 * @param idExamen @link{Long}
	 * @return response @link{org.springframework.http.ResponseEntity}
	 */
	@PostMapping("/cambiarExamenDisponible")
	public ResponseEntity<CustomSuccessResponse> cambiarExamenDisponible(@RequestBody @Valid Examen examen) {
		log.debug("Cambiar el estatus de un examen a Disponible");
		examenService.cambiaEstatusExamen(examen.getId(), EstatusExamen.DISPONIBLE);
		CustomSuccessResponse response = new CustomSuccessResponse(LocalDateTime.now(), HttpStatus.OK.value(), "Examen actualizado correctamente");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
