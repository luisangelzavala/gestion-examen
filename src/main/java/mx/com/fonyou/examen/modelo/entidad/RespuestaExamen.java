/**
 * RespuestaExamen.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.modelo.entidad;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Clase contenedor auxiliar obtener la calificación de un examen
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Data
public class RespuestaExamen implements Serializable {

	private static final long serialVersionUID = -7096244479478154149L;

	@NotNull(message = "Debe de especificar el examen que contesto")
	private Long idExamen;
	
	@NotNull(message = "Debe de especificar el estudiante que realizo la evaluacion")
	private Long idEstudiante;
	
	private List<PreguntaExamen> preguntas;
	
	
}
