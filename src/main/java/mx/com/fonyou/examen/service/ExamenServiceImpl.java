/**
 * ExamenServiceImpl.java
 * Fecha de creación: 05/10/2023
 *
 * Copyright (c) 2023 Fonyou Mexico
 * Av. Río San Joaquín 351, México, Ciudad de México, C.P. 11480
 * Todos los derechos reservados.
 */
package mx.com.fonyou.examen.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import mx.com.fonyou.examen.exception.GestionExamenException;
import mx.com.fonyou.examen.modelo.catalogo.EstatusExamen;
import mx.com.fonyou.examen.modelo.entidad.Examen;
import mx.com.fonyou.examen.modelo.entidad.Opcion;
import mx.com.fonyou.examen.modelo.entidad.Pregunta;
import mx.com.fonyou.examen.repository.ExamenDAO;
import mx.com.fonyou.examen.repository.OpcionDAO;
import mx.com.fonyou.examen.repository.PreguntaDAO;

/**
 * Implementación de la interface el servicio de Examen
 * @author Luis Angel Zavala
 * @version 0.0.1-SNAPSHOT
 */
@Log4j2
@Service("examenService")
public class ExamenServiceImpl implements ExamenService {

	@Value("${nota.maxima.examen}")
	private Double notaMaxima;
	
	@Value("${opciones.maximas.pregunta}")
	private Integer maximoOpciones;
	
	@Autowired
	private ExamenDAO examenDAO;
	
	@Autowired
	private PreguntaDAO preguntaDAO;
	
	@Autowired
	private OpcionDAO opcionDAO;
	
	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.ExamenService#crearExamen(mx.com.fonyou.examen.modelo.entidad.Examen) 
	 */
	@Override
	@Transactional
	public Examen crearExamen(Examen examen) {
		examen.setEstatusExamen(EstatusExamen.CREADO);
		examen.setValorExamen(0d);
		log.debug("Creando Examen");
		return examenDAO.save(examen);
	}

	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.ExamenService#crearExamenCompleto(mx.com.fonyou.examen.modelo.entidad.Examen) 
	 */
	@Override
	@Transactional
	public Examen crearExamenCompleto(Examen examen) throws GestionExamenException {
		List<Pregunta> listaPreguntas = examen.getPreguntas();
		List<Pregunta> preguntaList = new ArrayList<>();
		examen.setPreguntas( null );
		examen.setEstatusExamen(EstatusExamen.CREADO);
		examen.setValorExamen(0d);
		Examen examenGuardado = examenDAO.save(examen);
		listaPreguntas.forEach(pregunta -> {
			preguntaList.add(pregunta);
			List<Opcion> listaOpciones = new ArrayList<>();
			listaOpciones.addAll(pregunta.getOpciones());
			log.debug("Numero de opciones {} por pregunta: {}", listaOpciones.size(), pregunta.getDescripcion());
			pregunta.getOpciones().clear();
			pregunta.setIdExamen( examenGuardado.getId() );
			try {
				Pregunta preguntaGuardada = asignarPregunta( pregunta );
				log.debug("Guardo la pregunta: {}", preguntaGuardada);
				log.debug("Lista de opciones: {}", listaOpciones.size());
				listaOpciones.forEach(opcion -> {
					log.debug("Entrando al forEach de las opciones: {}", opcion);
					opcion.setIdPregunta( preguntaGuardada.getId() );
					definirOpcion( opcion );
				});
			} catch (GestionExamenException e) {
				log.error("Error al guardar la Pregunta: {}", pregunta.getDescripcion());
				log.error(e.getMessage());
				throw new GestionExamenException(e.getMessage());
			}
		});
		examenGuardado.setEstatusExamen(EstatusExamen.DISPONIBLE);
		return examenDAO.save( examenGuardado );
	}
	
	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.ExamenService#asignarPregunta(mx.com.fonyou.examen.modelo.entidad.Pregunta) 
	 */
	@Override
	@Transactional
	public Pregunta asignarPregunta(Pregunta pregunta) throws GestionExamenException {
		log.info("Definiendo una pregunta para el examen");
		Optional<Examen> examenOptional = examenDAO.findById(pregunta.getIdExamen());
		if(examenOptional.isPresent()) {
			Examen examen = examenOptional.get();
			if((examen.getValorExamen() + pregunta.getValor()) <= notaMaxima) {
				//pregunta.setExamen(examen);
				examen.setValorExamen( examen.getValorExamen() + pregunta.getValor() );
				preguntaDAO.save(pregunta);
				examenDAO.save(examen);
				log.debug("Asignando pregunta {} a examen {}", pregunta, examen.getId());
				return pregunta;
			} else {
				Double puntosDisponibles = notaMaxima - examen.getValorExamen();
				StringBuilder message = new StringBuilder();
				if(puntosDisponibles > 0) {
					message.append("La Pregunta que desea agregar excede el valor permitido de la nota maxima, ");
					message.append("solo dispone de ");
					message.append(puntosDisponibles);
					message.append(" puntos.");
				} else {
					message.append("Ya no dispone de puntos para este examen.");
				}
				log.error(message.toString());
				throw new GestionExamenException(message.toString());
			}
		} else {
			log.error("Error al guardar la Pregunta, no tiene un examen asignado o no existe {}", pregunta.getIdExamen());
			throw new GestionExamenException("Error al guardar la Pregunta, no tiene un examen asignado o no existe");
		}
		
	}

	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.ExamenService#modificaPregunta(mx.com.fonyou.examen.modelo.entidad.Pregunta) 
	 */
	@Override
	@Transactional
	public Pregunta modificaPregunta(Pregunta parametro) throws GestionExamenException {
		log.debug("Pregunta: {}", parametro);
		Optional<Pregunta> pregunta = preguntaDAO.findById(parametro.getId());
		Optional<Examen> examenOptional = examenDAO.findById(parametro.getIdExamen());
		if(pregunta.isPresent() && examenOptional.isPresent()) {
			log.debug("La pregunta si existe");
			log.debug("Modificada: {}", parametro);
			return preguntaDAO.save( parametro );
		} else {
			log.error("La opcion que intenta editar no existe");
			throw new GestionExamenException("La opcion que intenta editar no existe");
		}
	}

	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.ExamenService#eliminaPregunta(Long) 
	 */
	@Override
	@Transactional
	public void eliminaPregunta(Long idPregunta) throws GestionExamenException {
		Optional<Pregunta> pregunta = preguntaDAO.findById(idPregunta);
		if(pregunta.isPresent()) {
			Pregunta eliminar = pregunta.get();
			List<Opcion> opciones = opcionDAO.buscarOpciones(eliminar.getId());
			log.debug("Esta pregunta tiene opciones asignadas: {}", opciones.size());
			log.debug("Eliminando las opciones");
			opciones.forEach(opcion -> {
				log.debug("Eliminando la opcion: {}", opcion);
				opcionDAO.delete( opcion );
			});
			log.debug("Eliminando la pregunta: {}", eliminar);
			preguntaDAO.delete( eliminar );
		} else {
			log.error("La pregunta que intenta eliminar no existe");
			throw new GestionExamenException("La pregunta que intenta eliminar no existe");
		}
	}
	
	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.ExamenService#definirOpcion(mx.com.fonyou.examen.modelo.entidad.Opcion) 
	 */
	@Override
	@Transactional
	public Opcion definirOpcion(Opcion opcion) throws GestionExamenException {
		log.info("Definiendo una opción para la pregunta");
		Optional<Pregunta> preguntaOptional = preguntaDAO.findById(opcion.getIdPregunta());
		log.debug("Pregunta: {}", preguntaOptional.isPresent());
		if(preguntaOptional.isPresent()) {
			Pregunta pregunta = preguntaOptional.get();
			log.debug("Opciones: {}", pregunta.getOpciones() );
			log.debug("Opciones: {}", pregunta.getOpciones().isEmpty());
			if(pregunta.getOpciones() != null && pregunta.getOpciones().size() < maximoOpciones) {
				boolean existeOpcionCorrecta = pregunta.getOpciones().stream().anyMatch(option -> option.isCorrecta());
				if( (!existeOpcionCorrecta && !opcion.isCorrecta() && pregunta.getOpciones().size() == maximoOpciones) || 
					(existeOpcionCorrecta && opcion.isCorrecta())) {
					log.error("Debe de existir una opción correcta para la pregunta");
					throw new GestionExamenException("Debe de existir una opción correcta para la pregunta");
				} else {
					//opcion.setPregunta(pregunta);
					opcionDAO.save(opcion);
					log.debug("Asignando la opción {} a la pregunta {}). {}", opcion, pregunta.getId(), pregunta.getDescripcion());
					return opcion;
				}
			} else {
				log.error("Exedio el numero maximo de opciones para una pregunta");
				throw new GestionExamenException("Exedio el numero maximo de opciones para una pregunta");
			}
			
		} else {
			log.error("Error al guardar la opción, no tiene una pregunta asignada o no existe");
			throw new GestionExamenException("Error al guardar la opción, no tiene una pregunta asignada o no existe");
		}
	}

	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.ExamenService#modificaOpcion(mx.com.fonyou.examen.modelo.entidad.Opcion) 
	 */
	@Override
	@Transactional
	public Opcion modificaOpcion(Opcion parametro) throws GestionExamenException {
		Optional<Opcion> opcion = opcionDAO.findById(parametro.getId());
		if(opcion.isPresent()) {
			Opcion modificada = opcion.get();
			modificada.setDescripcion( parametro.getDescripcion() );
			modificada.setCorrecta( parametro.isCorrecta() );
			opcionDAO.save( modificada );
			return modificada;
		} else {
			log.error("La opcion que intenta editar no existe");
			throw new GestionExamenException("La opcion que intenta editar no existe");
		}
	}
	
	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.ExamenService#eliminaOpcion(Long) 
	 */
	@Override
	@Transactional
	public void eliminaOpcion(Long idOpcion) throws GestionExamenException {
		Optional<Opcion> opcion = opcionDAO.findById(idOpcion);
		if(opcion.isPresent()) {
			opcionDAO.delete( opcion.get() );
		} else {
			log.error("La opcion que intenta eliminar no existe");
			throw new GestionExamenException("La opcion que intenta eliminar no existe");
		}
	}
	
	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.ExamenService#cambiaEstatusExamen(Long, mx.com.fonyou.examen.modelo.catalogo.EstatusExamen) 
	 */
	@Override
	@Transactional
	public boolean cambiaEstatusExamen(Long idExamen, EstatusExamen estatus) throws GestionExamenException {
		log.debug("Cerrando Examen");
		Optional<Examen> examenOptional = examenDAO.findById(idExamen);
		if(examenOptional.isPresent()) {
			Examen examen = examenOptional.get();
			examen.setEstatusExamen(estatus);
			return true;
		} else {
			log.error("El examen que intenta editar no existe");
			throw new GestionExamenException("El examen que intenta editar no existe");
		}
	}
	
	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.ExamenService#obtenExamenesCreados() 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Examen> obtenExamenesCreados() {
		return examenDAO.buscaExamenByEstatus(EstatusExamen.CREADO);
	}

	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.ExamenService#obtenPreguntas(Long) 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Pregunta> obtenPreguntas(Long idExamen) {
		return preguntaDAO.buscarPreguntas(idExamen);
	}

	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.ExamenService#obtenOpciones(Long) 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Opcion> obtenOpciones(Long idPregunta) {
		return opcionDAO.buscarOpciones(idPregunta);
	}

	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.ExamenService#obtenExamenDisponible(Long) 
	 */
	@Override
	@Transactional(readOnly = true)
	public Examen obtenExamenDisponible(Long idExamen) {
		Examen examen = examenDAO.buscaExamenByIdAndByEstatus(idExamen, EstatusExamen.DISPONIBLE);
		if(examen != null) {
			return examen;
		}
		throw new GestionExamenException("El examen que intenta obtener no existe");
	}

	/* 
	 * La documentación de este método se encuentra en la clase o interface que lo declara  (non-Javadoc)
	 * @see mx.com.fonyou.examen.service.ExamenService#cambiarEstatusExamen(Long, mx.com.fonyou.examen.modelo.catalogo.EstatusExamen) 
	 */
	@Override
	@Transactional
	public void cambiarEstatusExamen(Long idExamen, EstatusExamen estatusExamen) {
		Optional<Examen> examenOptional = examenDAO.findById(idExamen);
		if(examenOptional.isPresent()) {
			Examen examen = examenOptional.get();
			examen.setEstatusExamen(estatusExamen);
			examenDAO.save(examen);
		}
	}
	
}
