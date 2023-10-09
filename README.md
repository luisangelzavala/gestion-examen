# Examen Técnico - Fonyou
 
 La siguiente aplicación fue realizada con SpringBoot 3 y Java 17 y es un gestor de examenes de selección múltiple el cual contiene las siguientes funcionalidades:
 
 * Crear un Estudiante
 * Crear un Examen.
 * Validación de la hora del examen conforme a la Zona Horaria del Estudiante
 * Recopilar las respuestas de un Estudiante
 * Calificar los Exámenes
 
 A continuación se describe como consumir los servicios:

##### Crear Estudiante.

Para consumir el servicio que crea un Estudiante se necesita realizar una petición **POST** a la siguiente URL:

```
	http://{{APP_URL}}:{{APP_PORT}}/estudiante/alta
```

La petición acepta un formato JSON con los siguientes valores:

```
	{
	    "nombre": "Hilda",
	    "primerApellido": "Hernandez",
	    "segundoApellido": "Lopez",
	    "edad": 26,
	    "ciudad": "Mexico",
	    "zonaHoraria": "America/New_York",
	    "correoElectronico": "correointentado@inventado.com"
	}
```

* **nombre:** Nombre del estudiante, el campo es obligatorio.
* **primerApellido:** Primer apellido del estudiante, el campo no es obligatorio.
* **segundoApellido:** Segundo apellido del estudiante, el campo no es obligatorio.
* **edad:** Edad del estudiante, el campo es obligatorio.
* **ciudad:** Ciudad del estudiante, el campo es obligatorio.
* **zonaHoraria:** Zona horaria del estudiante, el campo es obligatorio.
* **correoElectronico:** Correo electronico del estudiante, el campo es obligatorio.


##### Crear Examen.

Para crear un examen existen 2 formas posibles, una que permite crear el examen de manera completa con una sola petición y otra que permite realizar el examen en varios pasos, a continuación se describe la funcionalidad de estas peticiones:

###### Crear Examen Completo.
Para consumir el servicio que crea un Examen de manera completa se necesita realizar una petición **POST** a la siguiente URL:

```
	http://{{APP_URL}}:{{APP_PORT}}/examen/crearCompleto
```

La petición acepta un formato JSON con los siguientes valores:

```
	{
	    "nombre": "Programacion",
	    "fechaInicioExamen": "2023-10-07T09:32:00.000+02:00",
	    "fechaFinExamen": "2023-10-10T09:32:00.000+02:00",
	    "preguntas": [ 
	        { "descripcion": "Pregunta 1 del examen de Programacion", 
	          "valor": 50, 
	          "opciones" : [ { "descripcion": "Opcion Multiple 1", "correcta": true }, 
	                         { "descripcion": "Opcion Multiple 2", "correcta": false }, 
	                         { "descripcion": "Opcion Multiple 3", "correcta": false },
	                         { "descripcion": "Opcion Multiple 4", "correcta": false } ] }, 
	        { "descripcion": "Pregunta 2 del examen de Programacion", 
	          "valor": 50,
	          "opciones" : [ { "descripcion": "Opcion Multiple 1", "correcta": false }, 
	                         { "descripcion": "Opcion Multiple 2", "correcta": true }, 
	                         { "descripcion": "Opcion Multiple 3", "correcta": false },
	                         { "descripcion": "Opcion Multiple 4", "correcta": false } ] },
	    ]
	}
```

* **nombre:** Nombre del examen, el campo es obligatorio.
* **fechaInicioExamen:** Fecha de incio del examen, el campo es obligatorio.
* **fechaFinExamen:** Fecha de fin del examen, el campo es obligatorio.
* **preguntas:** Lista de preguntas, en esta petición el campo es obligatorio.
    * **descripcion:** Descripcion o pregunta, el campo es obligatorio.
    * **valor:** Valor que tendra la pregunta en el examen, el campo es obligatorio.
    * **opciones:** Lista de opciones multiples (maximo 4).
        * **descripcion:** Descripción u opcion, el campo es obligatorio.
        * **correcta:** Valor **TRUE|FALSE** que indica si la opción es la respuesta correcta a la pregunta.

###### Crear Examen Parcial.
Para consumir el servicio que crea un Examen de manera parcial se necesita seguir una serie de peticiones **POST**, en primer lugar, se debe de crear el examen ya que es el punto de partida para asignar las preguntas y las opciones multiples:

```
http://{{APP_URL}}:{{APP_PORT}}/examen/crear
```

La petición acepta un formato JSON con los siguientes valores:

```
	{
	    "nombre": "Programacion",
	    "fechaInicioExamen": "2023-10-07T09:32:00.000+02:00",
	    "fechaFinExamen": "2023-10-10T09:32:00.000+02:00"
	}
```

* **nombre:** Nombre del examen, el campo es obligatorio.
* **fechaInicioExamen:** Fecha de incio del examen, el campo es obligatorio.
* **fechaFinExamen:** Fecha de fin del examen, el campo es obligatorio.

###### Crear Pregunta.

Despues se debe de invocar el servicio para crear las preguntas y asignarlas al examen

```
	http://{{APP_URL}}:{{APP_PORT}}/examen/agregaPregunta
```

La petición acepta un formato JSON con los siguientes valores:

```
	{
	    "idExamen": 1,
	    "descripcion": "Esta es la pregunta 7?",
	    "valor": 1 
	}
```

* **idExamen:** Identificador del examen, campo obligatorio.
* **descripcion:** Descripcion o pregunta, el campo es obligatorio.
* **valor:** Valor que tendra la pregunta en el examen, el campo es obligatorio.

La petición solo acepta agregar una pregunta, por lo que si se desea tener más de una pregunta por examen se debera de consumir este servicio las veces que sean necesarias.

###### Crear Opción.

Despues se debe de invocar el servicio para crear las opciones y asignarlas a la pregunta registrada previamente.

```
	http://{{APP_URL}}:{{APP_PORT}}/examen/agregarOpcion
```

La petición acepta un formato JSON con los siguientes valores:

```
	{
	    "idPregunta": 5,
	    "descripcion": "Opcion 4",
	    "correcta": false
	}
```

* **idPregunta:** Identificador de la pregunta, campo obligatorio.
* **descripcion:** Descripción u opcion, el campo es obligatorio.
* **correcta:** Valor **TRUE|FALSE** que indica si la opción es la respuesta correcta a la pregunta.

La petición solo acepta agregar una opcion, por lo que si se desea tener más de una por pregunta se debera de consumir este servicio las veces, con un máximo de 4 opciones por pregunta.


###### Cambiar estatus de Examen a Disponible.

Una vez que se termine de generar el examen de manera parcial es necesario cambiar el estatus a **DISPONIBLE** para que pueda ser contestado por los Estudiantes, esto se logra consumiendo la siguiente petición:

```
	http://{{APP_URL}}:{{APP_PORT}}/examen/cambiarExamenDisponible
```

La petición acepta un formato JSON con los siguientes valores:

```
	{
	    "id": "1",
	    "nombre": "Español",
	    "fechaInicioExamen": "2023-10-07T09:32:00.000+02:00",
	    "fechaFinExamen": "2023-10-10T09:32:00.000+02:00"
	}
```

* **id:** Identificador del examen, el campo es obligatorio.
* **nombre:** Nombre del examen, el campo es obligatorio.
* **fechaInicioExamen:** Fecha de incio del examen, el campo es obligatorio.
* **fechaFinExamen:** Fecha de fin del examen, el campo es obligatorio.


###### Servicios auxiliares en generación de un Examen.

Se generaron una serie de servicios para apoyar en las actividades de generación de los examenes, propias que permiten editar o eliminar las preguntas y opciones multiples por si asi se requieren, a continuación se listaran las URLs de dichos servicios.

* **Modificar Pregunta**

```
	http://{{APP_URL}}:{{APP_PORT}}/examen/modificaPregunta
```

* **Eliminar Pregunta**

```
	http://{{APP_URL}}:{{APP_PORT}}/examen/eliminaPregunta
```

* **Modificar Opción**

```
	http://{{APP_URL}}:{{APP_PORT}}/examen/modificaOpcion
```

* **Eliminar Opción**

```
	http://{{APP_URL}}:{{APP_PORT}}/examen/eliminaOpcion
```


##### Gestión del Examen.

Una vez que se termina de crear el examen se debe de asignar a un alumno para que pueda presentarlo, el examen debera de tener el estatus de **DISPONIBLE**, el servicio a consumir para asignar el examen es el siguiente:

```
	http://{{APP_URL}}:{{APP_PORT}}/gestionExamen/asignar
```

La petición acepta un formato JSON con los siguientes valores:

```
	{
	    "idEstudiante": "1",
	    "idExamen": "1"
	}
```

* **idEstudiante:** Identificador del estudiante, el campo es obligatorio.
* **idExamen:** Identificador del examen, el campo es obligatorio.


Para responder el examen se necesita consumir el siguiente servicio:

```
	http://{{APP_URL}}:{{APP_PORT}}/gestionExamen/evaluar
```

La petición acepta un formato JSON con los siguientes valores:

```
	{
	    "idExamen": "1",
	    "idEstudiante": "1",
	    "preguntas": [ { "id": "1", "idOpcion": "1"}, { "id": "2", "idOpcion": "6"}, { "id": "3", "idOpcion": "10"},
	    			   { "id": "6", "idOpcion": "16"}, { "id": "5", "idOpcion": "17"}, { "id": "6", "idOpcion": "22"},
	    ]
	}
```

* **idExamen:** Identificador del examen, el campo es obligatorio.
* **idEstudiante:** Identificador del estudiante, el campo es obligatorio.
* **preguntas:** Lista de preguntas que fueron resueltas por el Estudiante
    * **id:** Identificador de la pregunta, el campo es obligatorio.
    * **idOpcion:** Identificador de la opción, el campo es obligatorio.

Este servicio verificara que estas dentro de la fecha de incio y fecha de fin del examen, validando la zona horario y permitiendo generar la calificación final del examen.


###### **{{APP_URL}}** es la URL del Servicio (Ej. localhost)
###### **{{APP_PORT}}** es el puerto en donde esta alojado el servicio **REST** (Ej. 9090)