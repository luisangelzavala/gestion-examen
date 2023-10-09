/**
 * AsignaExamen.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.modelo.entidad;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Clase contenedor para Asignar un Estudiante a un Examen registrado
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Data
public class AsignaExamen implements Serializable {
	
	private static final long serialVersionUID = -9120697928337516315L;
	
	@NotNull(message = "No se puede realizar la asignación si no indica el estudiante")
	private Long idEstudiante;
	
	@NotNull(message = "No se puede realizar la asignación si no indica el examen")
	private Long idExamen;

}
