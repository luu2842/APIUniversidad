package com.ibm.academia.restapi.universidad.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.academia.restapi.universidad.excepciones.NotFoundException;
import com.ibm.academia.restapi.universidad.modelo.entidades.Alumno;
import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;
import com.ibm.academia.restapi.universidad.servicios.AlumnoDAO;
import com.ibm.academia.restapi.universidad.servicios.PersonaDAO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/restapi")
@Api(value = "Metodos relacionados con los alumnos", tags = "Metodos sobre alumnos")
public class AlumnoController {

	private final static Logger logger = LoggerFactory.getLogger(AlumnoController.class);
		
	@Autowired
	@Qualifier("alumnoDAOImpl")
	private PersonaDAO alumnoDAO;
	
	
	/**
	 * Endpoint para consultar todas los alumnos
	 * @return Retorna una lista de alumnos
	 * @author Evelyn Neri - 17-02-2021
	 */
	@ApiOperation(value = "Consultar todas los alumnos")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@GetMapping("/alumno/lista")
	public ResponseEntity<?> obtenerTodos()
	{
		List<Persona> alumnos = (List<Persona>) alumnoDAO.buscarTodos();
		
		if(alumnos.isEmpty())
			throw new NotFoundException("No existen alumnos");
		
		return new ResponseEntity<List<Persona>>(alumnos, HttpStatus.OK);
	}

	/**
	 * Endpoint para consultar un alumno por id
	 * @param alumnoId Parametro de busqueda del alumno
	 * @return Retorna un objeto de tipo alumno
	 * @NotFoundException En caso de que falle buscando la alumno
	 * @author Evelyn Neri - 17-02-2021
	 */
	@ApiOperation(value = "Consultar alumnos por id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@GetMapping("/alumno/alumnoId/{alumnoId}")
	public ResponseEntity<?> obtenerAlumnoPorId(@PathVariable Long alumnoId){
		Optional<Persona> oAlumno = alumnoDAO.buscarPorId(alumnoId);
		
		if (!oAlumno.isPresent())
			throw new NotFoundException(String.format("Alumno con id %d no existe", alumnoId));
			
		return new ResponseEntity<Persona>(oAlumno.get(), HttpStatus.OK);
	}
	
	/**
	 *Endpoint para guardar un alumno
	 * @param alumno
	 * @return Retorna el alumno guardado
	 * @author Evelyn Neri - 17-02-2021
	 */
	@ApiOperation(value = "Crear alumnos")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@PostMapping("/alumno")
	public ResponseEntity<?> crearAlumno(@RequestBody Persona alumno, BindingResult result){
		Map<String, Object> validaciones = new HashMap<String, Object>();
		if(result.hasErrors()) {
			List<String> listaErrores = result.getFieldErrors()
					.stream()
					.map(errores -> "Campo: '" + errores.getField()+"' " + errores.getDefaultMessage())
					.collect(Collectors.toList());
			validaciones.put("Lista de errores", listaErrores);
			return new ResponseEntity<Map<String, Object>>(validaciones, HttpStatus.BAD_REQUEST);
		}
		Persona alumnoGuardado = alumnoDAO.guardar(alumno);
		return new ResponseEntity<Persona>(alumnoGuardado, HttpStatus.CREATED);
	}
	
	/**
	 * Endpoint para eliminar alumno por Id
	 * @param alumnoId
	 * @return Retorna el status
	 * @NotFoundException En caso de que falle buscando el alumno
	 * @author Evelyn Neri - 17-02-2021 
	 */
	@ApiOperation(value = "Eliminar un alumno por id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Endpoint ejecutado satisfactoriamente pero no devuelve ninguna respuesta"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@DeleteMapping("/alumno/eliminar/alumnoId/{alumnoId}")
	public ResponseEntity<?> eliminarAlumno(@PathVariable Long alumnoId){
		Optional<Persona> oAlumno = alumnoDAO.buscarPorId(alumnoId);
		
		if(!oAlumno.isPresent())
			throw new NotFoundException(String.format("El alumno con ID %d no existe", alumnoId));
		
		alumnoDAO.eliminarPorId(oAlumno.get().getId()); 
		return new ResponseEntity<String>("Alumno ID: " + alumnoId + " se elimino satisfactoriamente",  HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Endpoint para actualizar los datos de un alumno por Id
	 * @param alumnoId 
	 * @param alumno
	 * @return Los datos actualizados del alumno
	 * @author EJNL - 18-02-2021
	 */
	@ApiOperation(value = "Actualizar alumno")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 400, message = "No pudo interpretar la solicitud dada una sintaxis inválida")
	})
	@PutMapping("/alumno/actualizar/alumnoId/{alumnoId}")
	public ResponseEntity<?> actualizarAlumno(@PathVariable Long alumnoId, @RequestBody Persona alumno, BindingResult result){
		Map<String, Object> validaciones = new HashMap<String, Object>();
		if(result.hasErrors())
		{
			List<String> listaErrores = result.getFieldErrors()
					.stream()
					.map(errores -> "Campo: '" + errores.getField() + "' " + errores.getDefaultMessage())
					.collect(Collectors.toList());
			validaciones.put("Lista Errores", listaErrores);
			return new ResponseEntity<Map<String, Object>>(validaciones, HttpStatus.BAD_REQUEST);
		}
		
		Alumno alumnoActualizado = null;
		
		try
		{
			alumnoActualizado = (Alumno) ((AlumnoDAO) alumnoDAO).actualizar(alumnoId, alumno);
		}
		catch (Exception e) 
		{
			logger.info(e.getMessage());
			throw e;
		}

		return new ResponseEntity<Persona>(alumnoActualizado, HttpStatus.OK);
	}
	
	 /**
	  * Endpoint para asignarle una carrera a un alumno por id
	  * @param carreraId
	  * @param alumnoId
	  * @return Los datos del alumno con la carrera asignada
	  * @author EJNL - 18-02-2021
	  */
	@ApiOperation(value = "Asignar carrera a alumno")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente")
	})
	@PutMapping("/alumno/asociar-carrera")
	public ResponseEntity<?> asignarCarreraAlumno(@RequestParam Long carreraId, @RequestParam(name = "alumno_id") Long alumnoId){
		Persona alumno = ((AlumnoDAO)alumnoDAO).asociarCarreraAlumno(carreraId, alumnoId); 
		return new ResponseEntity<Persona>(alumno, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para buscar alumnos por nombre de carrera
	 * @param nombreCarrera
	 * @return La lista de los alumnos 
	 * @author EJNL - 18-02-2021
	 */
	@ApiOperation(value = "Buscar alumno por nombre de carrera")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente")
	})
	@GetMapping("/alumno/lista/nombrecarrera/{nombreCarrera}")
	public ResponseEntity<?> buscarAlumnoCarrera(@PathVariable String nombreCarrera){
		List<Persona> alumnos = (List<Persona>) ((AlumnoDAO)alumnoDAO).buscarAlumnosPorNombreCarrera(nombreCarrera);
		return new ResponseEntity<List<Persona>>(alumnos,HttpStatus.OK);
	}
}
