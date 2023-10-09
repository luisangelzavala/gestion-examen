/**
 * EstatusExamen.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.modelo.catalogo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Clase de Enum para el Estatus del Examen
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Getter
@AllArgsConstructor
public enum EstatusExamen {

	/** CREADO - Indica que el examen fue creado */
	CREADO, 
	
	/** DISPONIBLE - Indica que el examen se encuentra disponible para contestar */
	DISPONIBLE,
	
	/** FINALIZADO - Indica que el examen no puede ser presentado */
	FINALIZADO;

}
