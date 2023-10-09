/**
 * GestionExamenException.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.exception;

/**
 * Clase de Excepcion generica para los errores de logica de negocio
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
public class GestionExamenException extends RuntimeException {

	/** Serial Id de la clase */
	private static final long serialVersionUID = -1497296883004265538L;

	/**
	 * Constructor GestionExamenException.
	 * @param mensaje
	 */
	public GestionExamenException(String mensaje) {
        super(mensaje);
    }
	
	/**
	 * Constructor GestionExamenException.
	 * @param message
	 * @param causa
	 */
	public GestionExamenException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
	
}
