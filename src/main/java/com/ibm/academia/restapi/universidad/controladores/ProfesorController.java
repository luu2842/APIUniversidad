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
import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;
import com.ibm.academia.restapi.universidad.modelo.entidades.Profesor;
import com.ibm.academia.restapi.universidad.servicios.PersonaDAO;
import com.ibm.academia.restapi.universidad.servicios.ProfesorDAO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/restapi")
@Api(value = "Metodos relacionados con los profesores", tags = "Metodos sobre profesores")
public class ProfesorController {

	private final static Logger logger = LoggerFactory.getLogger(ProfesorController.class);
	
	@Autowired
	@Qualifier("profesorDAOImpl")
	private PersonaDAO profesorDAO;
	
	/**
	 * Endpoint para consultar todas los profesores
	 * @return Retorna una lista de profesores
	 * @author Evelyn Neri - 19-02-2021
	 */
	@ApiOperation(value = "Consultar todas los profesores")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@GetMapping("/profesor/lista")
	public ResponseEntity<?> obtenerTodos(){
		List<Persona> profesores = (List<Persona>) profesorDAO.buscarTodos();
		
		if(profesores.isEmpty())
			throw new NotFoundException("No existen profesores");
		
		return new ResponseEntity<List<Persona>>(profesores, HttpStatus.OK);
	}

	/**
	 * Endpoint para consultar un profesor por id
	 * @param profesorId Parametro de busqueda del profesor
	 * @return Retorna un objeto de tipo profesor
	 * @NotFoundException En caso de que falle buscando al profesor
	 * @author Evelyn Neri - 19-02-2021
	 */
	@ApiOperation(value = "Consultar profesores por id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@GetMapping("/profesor/profesorId{profesorId}")
	public ResponseEntity<?> obtenerProfesorPorId(@PathVariable Long profesorId){
		Optional<Persona> oProfesor = profesorDAO.buscarPorId(profesorId);
		
		if (!oProfesor.isPresent())
			throw new NotFoundException(String.format("Profesor con id %d no existe", profesorId));
			
		return new ResponseEntity<Persona>(oProfesor.get(), HttpStatus.OK);
	}
	
	/**
	 *Endpoint para guardar un profesor
	 * @param profesor
	 * @return Retorna el profesor guardado
	 * @author Evelyn Neri - 19-02-2021
	 */
	@ApiOperation(value = "Crear profesores")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@PostMapping("/profesor")
	public ResponseEntity<?> crearProfesor(@RequestBody Persona profesor){
		Persona profesorGuardado = profesorDAO.guardar(profesor);
		return new ResponseEntity<Persona>(profesorGuardado, HttpStatus.CREATED);
	}
	
	/**
	 * Endpoint para eliminar un profesor por Id
	 * @param profesorId
	 * @return Retorna el status
	 * @NotFoundException En caso de que falle buscando al profesor
	 * @author Evelyn Neri - 19-02-2021 
	 */
	@ApiOperation(value = "Eliminar un profesor por id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Endpoint ejecutado satisfactoriamente pero no devuelve ninguna respuesta"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@DeleteMapping("/profesor/eliminar/profesorId/{profesorId}")
	public ResponseEntity<?> eliminarProfesor(@PathVariable Long profesorId){
		Optional<Persona> oProfesor = profesorDAO.buscarPorId(profesorId);
		
		if(!oProfesor.isPresent())
			throw new NotFoundException(String.format("El profesor con ID %d no existe", profesorId));
		
		profesorDAO.eliminarPorId(oProfesor.get().getId()); 
		return new ResponseEntity<String>("Profesor ID: " + profesorId + " se elimino satisfactoriamente",  HttpStatus.NO_CONTENT);
	}

	/**
	 * Endpoint para actualizar los datos de un profesor por Id
	 * @param profesorId 
	 * @param profesor
	 * @return Los datos actualizados del profesor
	 * @author EJNL - 19-02-2021
	 */
	@ApiOperation(value = "Actualizar profesor")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 400, message = "No pudo interpretar la solicitud dada una sintaxis inválida")
	})
	@PutMapping("/profesor/actualizar/profesorId/{profesorId}")
	public ResponseEntity<?> actualizarProfesor(@PathVariable Long profesorId, @RequestBody Persona profesor, BindingResult result){
	
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
		
		Profesor profesorActualizado = null;
		
		try
		{
			profesorActualizado =  (Profesor) ((ProfesorDAO)profesorDAO).actualizar(profesorId, profesor);
		}
		catch (Exception e) 
		{
			logger.info(e.getMessage());
			throw e;
		}
		return new ResponseEntity<Persona>(profesorActualizado, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para buscar carreras por nombre de carrera
	 * @param nombreCarrera
	 * @return Lista de carreras
	 * @author EJNL - 19-02-2021
	 */

	@ApiOperation(value = "Buscar profesor por nombre de carrera")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente")
	})
	@GetMapping("/profesor/lista/nombreCarrera/{nombreCarrera}")
	public ResponseEntity<?> findProfesoresByCarrera(@PathVariable String nombreCarrera){
		List<Persona> profesores = (List<Persona>) ((ProfesorDAO)profesorDAO).findProfesoresByCarrera(nombreCarrera);
		return new ResponseEntity<List<Persona>>(profesores,HttpStatus.OK); 
	}
	
	 /**
	  * Endpoint para asignar carrera a profesor
	  * @param carreraId
	  * @param alumnoId
	  * @return Los datos del profesor y la carrera asignada
	  * @author EJNL - 19-02-2021
	  */
	@ApiOperation(value = "Asignar carrera a profesor")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente")
	})
	@PutMapping("/profesor/asociar-carrera")
	public ResponseEntity<?> asignarCarreraProfesor(@RequestParam List<Long> carreraId, @RequestParam(name = "profesor_id") Long profesorId){
		Persona profesor = ((ProfesorDAO)profesorDAO).asociarCarreraProfesor(carreraId, profesorId); 
		return new ResponseEntity<Persona>(profesor, HttpStatus.OK);
	}
	
}
