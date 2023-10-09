/**
 * GestionExamenExceptionHandler.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.controller.handler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.log4j.Log4j2;
import mx.com.fonyou.examen.exception.GestionExamenException;
import mx.com.fonyou.examen.modelo.entidad.handler.CustomErrorResponse;

/**
 * Clase que permite gestionar las excepciones en las respuestas de Rest
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Log4j2
@ControllerAdvice
public class GestionExamenExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Metodo que permite manejar las excepciones definidas para responder con un objeto 
	 * @link{mx.com.fonyou.examen.modelo.entidad.handler.CustomErrorResponse} en los servicios Rest
	 * @author Luis Angel Zavala
	 * @param ex @link{Exception}
	 * @param request @link{org.springframework.web.context.request.WebRequest}
	 * @exception @link{org.springframework.dao.DataIntegrityViolationException}
	 * @exception @link{org.hibernate.PropertyValueException}
	 * @exception @link{mx.com.fonyou.examen.exception.GestionExamenException}
	 * @exception @link{org.springframework.transaction.TransactionSystemException}
	 */
	@ExceptionHandler({DataIntegrityViolationException.class, PropertyValueException.class, GestionExamenException.class, TransactionSystemException.class})
    public ResponseEntity<CustomErrorResponse> dataIntegrityViolation(Exception ex, WebRequest request) {
		log.debug("Custom ExceptionHandler para los servicios Rest");
		HttpStatus errorStatus = HttpStatus.NOT_FOUND;
		CustomErrorResponse errorResponse = new CustomErrorResponse();
		errorResponse.setFechaError(LocalDateTime.now());
		errorResponse.setError(ex.getMessage());
		errorResponse.setEstatusErrorId(errorStatus.value());
		
		return new ResponseEntity<>(errorResponse, errorStatus);
    }
	
	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleMethodArgumentNotValid(org.springframework.http.HttpHeaders, 
	 * 																														  org.springframework.http.HttpStatusCode, 
	 * 																														  org.springframework.web.context.request.WebRequest)
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		log.debug("Custom ExceptionHandler para los errores de validación de los servicios Rest");
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("fechaError", LocalDateTime.now());
		body.put("estatusErrorId", status);
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
		body.put("errors", errors);
		return new ResponseEntity<>(body, headers, status);
	}
	
}
