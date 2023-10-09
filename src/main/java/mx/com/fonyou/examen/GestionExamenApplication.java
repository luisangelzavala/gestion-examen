/**
 * GestionExamenApplication.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

/**
 * Clase que ejecuta la aplicación de Java Spring Boot
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Log4j2
@SpringBootApplication
public class GestionExamenApplication {

	public static void main(String[] args) {
		log.debug("Inicializando Spring Boot");
		SpringApplication.run(GestionExamenApplication.class, args);
	}

}
