/**
 * PreguntaExamen.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.modelo.entidad;

import java.io.Serializable;

import lombok.Data;

/**
 * Clase contenedor auxiliar para generar la evaluacion de un examen
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Data
public class PreguntaExamen implements Serializable {
	
	private static final long serialVersionUID = 927863100270454968L;

	private Long id;
	
	private Long idOpcion;
}
