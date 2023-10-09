CREATE DATABASE `public` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

-- public.estudiantes definition

CREATE TABLE `estudiantes` (
  `id_estudiante` bigint NOT NULL AUTO_INCREMENT,
  `ciudad` varchar(150) NOT NULL,
  `correo_electronico` varchar(255) NOT NULL,
  `edad` int NOT NULL,
  `nombre` varchar(80) NOT NULL,
  `primer_apellido` varchar(50) DEFAULT NULL,
  `segundo_apellido` varchar(50) DEFAULT NULL,
  `zona_horaria` varchar(50) NOT NULL,
  PRIMARY KEY (`id_estudiante`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- public.examenes definition

CREATE TABLE `examenes` (
  `id_examen` bigint NOT NULL AUTO_INCREMENT,
  `estatus_examen` enum('CREADO','DISPONIBLE','FINALIZADO') DEFAULT NULL,
  `fecha_fin_examen` datetime(6) NOT NULL,
  `fecha_inicio_examen` datetime(6) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `valor_examen` double NOT NULL,
  PRIMARY KEY (`id_examen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- public.preguntas definition

CREATE TABLE `preguntas` (
  `id_pregunta` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL,
  `id_examen` bigint NOT NULL,
  `valor` double NOT NULL,
  PRIMARY KEY (`id_pregunta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- public.examenes_preguntas definition

CREATE TABLE `examenes_preguntas` (
  `examen_id_examen` bigint NOT NULL,
  `preguntas_id_pregunta` bigint NOT NULL,
  UNIQUE KEY `UK_nutyulwmnt2y05s93rm8a11jb` (`preguntas_id_pregunta`),
  KEY `FK12pck32d274u10my6iib6i2hu` (`examen_id_examen`),
  CONSTRAINT `FK12pck32d274u10my6iib6i2hu` FOREIGN KEY (`examen_id_examen`) REFERENCES `examenes` (`id_examen`),
  CONSTRAINT `FKoq7847t7ic877ch1h1fr9bxa1` FOREIGN KEY (`preguntas_id_pregunta`) REFERENCES `preguntas` (`id_pregunta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- public.opciones definition

CREATE TABLE `opciones` (
  `id_opcion` bigint NOT NULL AUTO_INCREMENT,
  `correcta` bit(1) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `id_pregunta` bigint NOT NULL,
  `seleccionada` bit(1) NOT NULL,
  PRIMARY KEY (`id_opcion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- public.preguntas_opciones definition

CREATE TABLE `preguntas_opciones` (
  `pregunta_id_pregunta` bigint NOT NULL,
  `opciones_id_opcion` bigint NOT NULL,
  UNIQUE KEY `UK_tb7eco6h19vt14sgdtgw34dsi` (`opciones_id_opcion`),
  KEY `FKaluyx1m9jk4hurodar4db8k0` (`pregunta_id_pregunta`),
  CONSTRAINT `FKaluyx1m9jk4hurodar4db8k0` FOREIGN KEY (`pregunta_id_pregunta`) REFERENCES `preguntas` (`id_pregunta`),
  CONSTRAINT `FKtpv6r99bi5tciiunaygbwcllb` FOREIGN KEY (`opciones_id_opcion`) REFERENCES `opciones` (`id_opcion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- public.evaluaciones definition

CREATE TABLE `evaluaciones` (
  `id_evaluacion` bigint NOT NULL AUTO_INCREMENT,
  `calificacion` double NOT NULL,
  `estatus_evaluacion` enum('APROBADO','REPROBADO','SIN_PRESENTAR') NOT NULL,
  `fecha_evaluacion` date DEFAULT NULL,
  `id_estudiante` bigint DEFAULT NULL,
  `id_examen` bigint DEFAULT NULL,
  PRIMARY KEY (`id_evaluacion`),
  KEY `FKbrn5df1jty6mg5vhk3mwqg8vk` (`id_estudiante`),
  KEY `FKls7b0nr4n992a4gyk112iv8gt` (`id_examen`),
  CONSTRAINT `FKbrn5df1jty6mg5vhk3mwqg8vk` FOREIGN KEY (`id_estudiante`) REFERENCES `estudiantes` (`id_estudiante`),
  CONSTRAINT `FKls7b0nr4n992a4gyk112iv8gt` FOREIGN KEY (`id_examen`) REFERENCES `examenes` (`id_examen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- public.respuestas definition

CREATE TABLE `respuestas` (
  `id_respuesta` bigint NOT NULL AUTO_INCREMENT,
  `id_evaluacion` bigint DEFAULT NULL,
  `id_opcion` bigint DEFAULT NULL,
  `id_pregunta` bigint DEFAULT NULL,
  PRIMARY KEY (`id_respuesta`),
  KEY `FKkvqx7easo3w1gsn17c64fnqpp` (`id_evaluacion`),
  KEY `FKhchfij67vatuvgdqh5vuhoo0h` (`id_opcion`),
  KEY `FKhpdeptdk3p3fu5l3i57h9bhn7` (`id_pregunta`),
  CONSTRAINT `FKhchfij67vatuvgdqh5vuhoo0h` FOREIGN KEY (`id_opcion`) REFERENCES `opciones` (`id_opcion`),
  CONSTRAINT `FKhpdeptdk3p3fu5l3i57h9bhn7` FOREIGN KEY (`id_pregunta`) REFERENCES `preguntas` (`id_pregunta`),
  CONSTRAINT `FKkvqx7easo3w1gsn17c64fnqpp` FOREIGN KEY (`id_evaluacion`) REFERENCES `evaluaciones` (`id_evaluacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


