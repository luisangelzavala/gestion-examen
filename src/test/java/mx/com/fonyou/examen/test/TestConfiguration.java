/**
 * TestConfiguration.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Clase de configuracion para Spring en la parte de Test
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Configuration
@ComponentScan("mx.com.fonyou.examen")
public class TestConfiguration {

	/** Creando un RestTemplate para ejecutar las pruebas unitarias */
	@Bean
	RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
