/**
 * AuxiliarExamenServiceImpl.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.fonyou.examen.modelo.entidad.Examen;
import mx.com.fonyou.examen.modelo.entidad.Pregunta;
import mx.com.fonyou.examen.repository.ExamenDAO;
import mx.com.fonyou.examen.repository.PreguntaDAO;

/**
 * Implementación de la interface para las funcionalidades auxiliares del servicio de Examen
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Service("auxiliarExamenService")
public class AuxiliarExamenServiceImpl implements AuxiliarExamenService {

	@Autowired
	private ExamenDAO examenDAO;
	
	@Autowired
	private PreguntaDAO preguntaDAO;
	
	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.AuxiliarExamenService#obtenExamenesSinPregunta()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Examen> obtenExamenesSinPregunta() {
		return examenDAO.buscarExamenVacio();
	}

	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.AuxiliarExamenService#obtenPreguntaSinOpciones()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Pregunta> obtenPreguntaSinOpciones() {
		return preguntaDAO.buscarPreguntaVacia();
	}

}
