/**
 * EvaluacionServiceImpl.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import mx.com.fonyou.examen.exception.GestionExamenException;
import mx.com.fonyou.examen.modelo.catalogo.EstatusEvaluacion;
import mx.com.fonyou.examen.modelo.catalogo.EstatusExamen;
import mx.com.fonyou.examen.modelo.entidad.Estudiante;
import mx.com.fonyou.examen.modelo.entidad.Evaluacion;
import mx.com.fonyou.examen.modelo.entidad.Examen;
import mx.com.fonyou.examen.modelo.entidad.Opcion;
import mx.com.fonyou.examen.modelo.entidad.Pregunta;
import mx.com.fonyou.examen.modelo.entidad.PreguntaExamen;
import mx.com.fonyou.examen.modelo.entidad.Respuesta;
import mx.com.fonyou.examen.modelo.entidad.RespuestaExamen;
import mx.com.fonyou.examen.repository.EstudianteDAO;
import mx.com.fonyou.examen.repository.EvaluacionDAO;
import mx.com.fonyou.examen.repository.ExamenDAO;
import mx.com.fonyou.examen.repository.OpcionDAO;
import mx.com.fonyou.examen.repository.PreguntaDAO;
import mx.com.fonyou.examen.repository.RespuestaDAO;

/**
 * Implementación de la interface el servicio de Evaluacion
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Log4j2
@Service("evaluacionService")
public class EvaluacionServiceImpl implements EvaluacionService {

	@Value("${calificacion.minima.aprobatoria}")
	private Double calificacionMinima;
	
	@Value("${default.timezone}")
	private String defaultTimeZone;
	
	@Autowired
	private EstudianteDAO estudianteDAO;
	
	@Autowired
	private ExamenDAO examenDAO;

	@Autowired
	private EvaluacionDAO evaluacionDAO;
	
	@Autowired
	private PreguntaDAO preguntaDAO;
	
	@Autowired
	private OpcionDAO opcionDAO;
	
	@Autowired
	private RespuestaDAO respuestaDAO;
	
	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.EvaluacionService#asignaExamen(Long, Long) 
	 */
	@Override
	@Transactional
	public void asignaExamen(Long idEstudiante, Long idExamen) throws GestionExamenException {
		Optional<Estudiante> estudianteOptional = estudianteDAO.findById(idEstudiante);
		Optional<Examen> examenOptional = examenDAO.findById(idExamen);
		if(estudianteOptional.isPresent() && examenOptional.isPresent()) {
			Examen examen = examenOptional.get();
			if(examen.getEstatusExamen().equals(EstatusExamen.DISPONIBLE)) {
				Evaluacion evaluacion = new Evaluacion();
				evaluacion.setCalificacion(0d);
				evaluacion.setEstatusEvaluacion(EstatusEvaluacion.SIN_PRESENTAR);
				evaluacion.setEstudiante(estudianteOptional.get());
				evaluacion.setExamen(examen);
				evaluacionDAO.save( evaluacion );
			} else {
				log.error("Error, no se puede asignar un Estudiante a un Examen que no se encuentre {}", EstatusExamen.DISPONIBLE);
				throw new GestionExamenException("Error, no se puede asignar un Estudiante a un Examen que no se encuentre DISPONIBLE");
			}
		} else {
			log.error("Error, el examen o el estudiante no existen: (Examen: {}), (Estudiante: {})", estudianteOptional.isPresent(), examenOptional.isPresent());
			throw new GestionExamenException("Error, el examen o el estudiante no existen");
		}
	}
	
	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.EvaluacionService#generaEvaluacion(mx.com.fonyou.examen.modelo.entidad.RespuestaExamen) 
	 */
	@Override
	@Transactional
	public Evaluacion generaEvaluacion(RespuestaExamen respuestas) {
		Optional<Estudiante> estudianteOptional = estudianteDAO.findById(respuestas.getIdEstudiante());
		Optional<Examen> examenOptional = examenDAO.findById(respuestas.getIdExamen());
		if(estudianteOptional.isPresent() && examenOptional.isPresent()) {
			Evaluacion evaluacion = new Evaluacion();
			Calendar fechaActual = Calendar.getInstance();
			evaluacion.setFechaEvaluacion(fechaActual.getTime());
			evaluacion.setEstatusEvaluacion(EstatusEvaluacion.REPROBADO);
			evaluacion.setExamen(examenOptional.get());
			evaluacion.setEstudiante(estudianteOptional.get());
			if(respuestas.getPreguntas() == null || respuestas.getPreguntas().isEmpty()) {
				evaluacion.setCalificacion(0d);
				evaluacionDAO.save( evaluacion );
				return evaluacion;
			} else {
				Respuesta respuesta = obtenEvaluacion( respuestas.getPreguntas() );
				evaluacion.setCalificacion( respuesta.getTotalPuntos() );
				if(respuesta.getTotalPuntos() >= calificacionMinima ) {
					evaluacion.setEstatusEvaluacion(EstatusEvaluacion.APROBADO);
				}
				Evaluacion evaluacionTerminada = evaluacionDAO.save( evaluacion );
				respuesta.setEvaluacion(evaluacionTerminada);
				respuestaDAO.save(respuesta);
				return evaluacionTerminada;
			}
		} else {
			log.error("Error al generar la evaluacion: (Examen: {}), (Estudiante: {})", estudianteOptional.isPresent(), examenOptional.isPresent());
			throw new GestionExamenException("Error al generar la evaluacion el examen o el estudiante no existen");
		}
	}

	/** 
	 * Realiza la evaluacion de un examen presentado por un estudiante
	 * @author Luis Angel Zavala
	 * @param listaPreguntas @link{List}
	 * @return respuesta @link{mx.com.fonyou.examen.modelo.entidad.Respuesta}
	 */
	private Respuesta obtenEvaluacion(List<PreguntaExamen> listaPreguntas) {
		Respuesta respuesta = new Respuesta();
		respuesta.setTotalPuntos(0d);
		List<PreguntaExamen> preguntas = listaPreguntas;
		preguntas.forEach(pregunta -> {
			Optional<Pregunta> preguntaOptional = preguntaDAO.findById( pregunta.getId() );
			Optional<Opcion> opcionOptional = opcionDAO.findById( pregunta.getIdOpcion() );
			if(preguntaOptional.isPresent() && opcionOptional.isPresent()) {
				Pregunta preguntaContestada = preguntaOptional.get();
				Opcion opcionSeleccionada = opcionOptional.get();
				respuesta.setPregunta( preguntaContestada );
				respuesta.setOpcion( opcionSeleccionada );
				respuesta.setTotalPuntos( respuesta.getTotalPuntos() + 
										  ( opcionSeleccionada.isCorrecta() ? preguntaContestada.getValor() : 0d ) );
			}
		});
		return respuesta;
	}

	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.EvaluacionService#validaFechaEvaluacion(Long, Long) 
	 */
	@Override
	@Transactional
	public boolean validaFechaEvaluacion(Long idEstudiante, Long idExamen) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
		
		Optional<Examen> examenOptional = examenDAO.findById(idExamen);
		Optional<Estudiante> estudianteOptional = estudianteDAO.findById(idEstudiante);
		if(examenOptional.isPresent() && estudianteOptional.isPresent()) {
			Examen examen = examenOptional.get();
			Estudiante estudiante = estudianteOptional.get();
			
			ZoneId zonaHorariaDefault = ZoneId.of(defaultTimeZone);
			Timestamp timestampFechaInicio = examen.getFechaInicioExamen();
			Timestamp timestampFechaFin = examen.getFechaFinExamen();
			LocalDateTime fechaHoraInicio = timestampFechaInicio.toLocalDateTime().atZone(zonaHorariaDefault).toLocalDateTime();
			LocalDateTime fechaHoraFin = timestampFechaFin.toLocalDateTime().atZone(zonaHorariaDefault).toLocalDateTime();
			
			
			log.debug("Fecha y Hora de inicio de examen: {}", fechaHoraInicio);
			log.debug("Fecha y Hora de fin de examen: {}", fechaHoraFin);
			
			ZoneId zonaHorariaEstudiante = ZoneId.of(estudiante.getZonaHoraria());

	        ZonedDateTime fechaHoraZonaHorariaEstudiante = ZonedDateTime.now(zonaHorariaEstudiante);
	        LocalDateTime fechaHoraEstudiante = fechaHoraZonaHorariaEstudiante.toLocalDateTime();
	        String fechaHoraFormateadaEstudiante = fechaHoraZonaHorariaEstudiante.format(formatter);
	        log.debug("Fecha y hora en {} : {}", zonaHorariaEstudiante, fechaHoraFormateadaEstudiante);
	        
	        log.debug("FechaInicio {} Before fechaHoraEstudiante {} = {}", fechaHoraInicio, fechaHoraEstudiante, fechaHoraInicio.isBefore(fechaHoraEstudiante));
	        log.debug("FechaInicio {} Equals fechaHoraEstudiante {} = {}", fechaHoraInicio, fechaHoraEstudiante, fechaHoraInicio.isEqual(fechaHoraEstudiante));
	        log.debug("FechaFin {} After fechaHoraEstudiante {} = {}", fechaHoraFin, fechaHoraEstudiante, fechaHoraFin.isAfter(fechaHoraEstudiante));
	        
	        if( (fechaHoraInicio.isBefore(fechaHoraEstudiante) || fechaHoraInicio.isEqual(fechaHoraEstudiante)) ||
	        	(fechaHoraFin.isAfter(fechaHoraEstudiante) )) {
	        		 log.debug("Validando la hora del examen - Entrando a la validacion");
	        		 return true;
	        }
		}
		return false;
	}

}
