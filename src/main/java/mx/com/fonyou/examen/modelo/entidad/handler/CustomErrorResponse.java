/**
 * CustomErrorResponse.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.modelo.entidad.handler;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase contenedor de una respuesta Rest que representa un error
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorResponse implements Serializable {

	private static final long serialVersionUID = 836742223947834782L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime fechaError;
	
    private Integer estatusErrorId;
    
    private String error;
}
