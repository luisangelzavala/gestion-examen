/**
 * GestionExamenController.java
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import mx.com.fonyou.examen.exception.GestionExamenException;
import mx.com.fonyou.examen.modelo.catalogo.EstatusEvaluacion;
import mx.com.fonyou.examen.modelo.entidad.AsignaExamen;
import mx.com.fonyou.examen.modelo.entidad.Evaluacion;
import mx.com.fonyou.examen.modelo.entidad.RespuestaExamen;
import mx.com.fonyou.examen.modelo.entidad.handler.CustomErrorResponse;
import mx.com.fonyou.examen.modelo.entidad.handler.CustomSuccessResponse;
import mx.com.fonyou.examen.service.EvaluacionService;

/**
 * Rest Controller para las funcionalidades del modulo de Gestion de Examen
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Log4j2
@RestController
@RequestMapping("/gestionExamen")
public class GestionExamenController {

	@Autowired
	private EvaluacionService evaluacionService;
	
	/**
	 * Punto de entrada de la peticion rest para asignar el examen a un estudiante
	 * @param asignacion @link{mx.com.fonyou.examen.modelo.entidad.AsignaExamen}
	 * @return response @link{org.springframework.http.ResponseEntity}
	 */
	@PostMapping("/asignar")
	public ResponseEntity<CustomSuccessResponse> asignarExamen(@RequestBody @Valid AsignaExamen asignacion) throws GestionExamenException {
		log.debug("Asigna un examen a un estudiante");
		evaluacionService.asignaExamen(asignacion.getIdEstudiante(), asignacion.getIdExamen());
		CustomSuccessResponse response = new CustomSuccessResponse(LocalDateTime.now(), HttpStatus.OK.value(), "Examen asignado correctamente");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Punto de entrada de la peticion rest para generar la evalucacion de un examen finalizado por el estudiante
	 * @param respuestas @link{mx.com.fonyou.examen.modelo.entidad.RespuestaExamen}
	 * @return response @link{org.springframework.http.ResponseEntity}
	 */
	@PostMapping("/evaluar")
	public ResponseEntity<Object> evaluaExamen(@RequestBody @Valid RespuestaExamen respuestas) throws GestionExamenException {
		log.debug("Evalua el examen {} presentado por el alumno {}", respuestas.getIdExamen(), respuestas.getIdEstudiante());
		if(evaluacionService.validaFechaEvaluacion(respuestas.getIdEstudiante(), respuestas.getIdExamen())) {
			Evaluacion evaluacion = evaluacionService.generaEvaluacion(respuestas);
			StringBuilder message = new StringBuilder();
			if(evaluacion != null) {
				if(evaluacion.getEstatusEvaluacion().equals(EstatusEvaluacion.APROBADO)) {
					message.append("Felicidades! Aprobaste el examen ");
				} else {
					message.append("Lo sentimos! Reprobaste el examen");
				}
				message.append(evaluacion.getExamen().getNombre());
				message.append(", tu calificacion fue de ");
				message.append(evaluacion.getCalificacion());
				message.append(" puntos, en breve se enviara el resultado a tu correo electronico (");
				message.append(evaluacion.getEstudiante().getCorreoElectronico());
				message.append(")");
			}
			log.debug("Respuesta de validacion: {}", message.toString());
			CustomSuccessResponse response = new CustomSuccessResponse(LocalDateTime.now(), HttpStatus.OK.value(), message.toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			CustomErrorResponse response = new CustomErrorResponse(LocalDateTime.now(), HttpStatus.OK.value(), "Error: el estudiante se encuentra fuera del horario");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
	}
	
}
