package com.ibm.academia.restapi.universidad.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.ibm.academia.restapi.universidad.excepciones.NotFoundException;
import com.ibm.academia.restapi.universidad.modelo.entidades.Carrera;
import com.ibm.academia.restapi.universidad.servicios.CarreraDAO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/restapi")
@Api(value = "Metodos relacionados con las carreras", tags = "Metodos sobre carreras")
public class CarreraController {
	
	private final static Logger logger = LoggerFactory.getLogger(CarreraController.class);
	
	@Autowired
	private CarreraDAO carreraDAO;
	
	/**
	 * Endpoint para consultar todas las carreras
	 * @return Retorna una lista de carreras
	 * @author Evelyn Neri - 17-02-2021
	 */
	@ApiOperation(value = "Consultar todas las carreras")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@GetMapping("/carrera/lista")
	public ResponseEntity<?> listarTodas(){
		List<Carrera> carreras = (List<Carrera>) carreraDAO.buscarTodos();
		
		if(carreras.isEmpty())
			throw new NotFoundException("No existen carreras");
			
		return new ResponseEntity<List<Carrera>>(carreras, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para consultar una carrera por id
	 * @param carreraId Parametro de busqueda de la carrera
	 * @return Retorna un objeto de tipo carrera
	 * @NotFoundException En caso de que falle buscando la carrera
	 * @author Evelyn Neri - 17-02-2021
	 */
	@ApiOperation(value = "Consultar carreras por id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@GetMapping("/carrera/carreraId/{carreraId}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long carreraId){
		
		Optional<Carrera> oCarrera = carreraDAO.buscarPorId(carreraId);
		
		if (!oCarrera.isPresent()) {
			throw new NotFoundException(String.format("La carrera con id: %d no existe", carreraId));
		}
		return new ResponseEntity<>(oCarrera.get(), HttpStatus.OK);
	}
	
	/**
	 *Endpoint para guardar una carrera
	 * @param carrera
	 * @return Retorna la carrera guardada
	 * @author Evelyn Neri - 17-02-2021
	 */

	@ApiOperation(value = "Crear carreras")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@PostMapping("/carrera")
	public ResponseEntity<?> guardar(@Valid @RequestBody Carrera carrera, BindingResult result){
		Map<String, Object> validaciones = new HashMap<String, Object>();
		if(result.hasErrors()) {
			List<String> listaErrores = result.getFieldErrors()
					.stream()
					.map(errores -> "Campo: '" + errores.getField()+"' " + errores.getDefaultMessage())
					.collect(Collectors.toList());
			validaciones.put("Lista de errores", listaErrores);
			return new ResponseEntity<Map<String, Object>>(validaciones, HttpStatus.BAD_REQUEST);
		}
		
		Carrera carreraGuardada = carreraDAO.guardar(carrera);
		return new ResponseEntity<Carrera>(carreraGuardada, HttpStatus.CREATED);
	}

	/**
	 * Endpoint para eliminar una carrera por Id
	 * @param carreraId
	 * @return Retorna el status
	 * @NotFoundException En caso de que falle buscando la carrera
	 * @author Evelyn Neri - 17-02-2021 
	 */
	@ApiOperation(value = "Eliminar carrera por id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Endpoint ejecutado satisfactoriamente pero no devuelve ninguna respuesta"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@DeleteMapping("/carrera/eliminar/carreraId/{carreraId}")
	public ResponseEntity<?> eliminar(@PathVariable Long carreraId){
		Optional<Carrera> oCarrera = carreraDAO.buscarPorId(carreraId);
		
		if (!oCarrera.isPresent()) 
			throw new NotFoundException(String.format("La carrera con id: %d no existe", carreraId));
		
		carreraDAO.eliminarPorId(carreraId);
		return new ResponseEntity<>("La carrera con id: " + carreraId + "fue eliminada", HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Endpoint para actualizar los datos de una carrera por Id
	 * @param carreraId 
	 * @param carrera
	 * @return Los datos actualizados de la carrera
	 * @author EJNL - 18-02-2021
	 */

	@ApiOperation(value = "Actualizar carrera")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 400, message = "No pudo interpretar la solicitud dada una sintaxis inválida")
	})
	@PutMapping("/carrera/actualizar/carreraId/{carreraId}")
	public ResponseEntity<?> actualizar(@PathVariable Long carreraId, @Valid @RequestBody Carrera carrera, BindingResult result)
	{
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
		
		Carrera carreraActualizada = null;
		
		try
		{
			carreraActualizada = carreraDAO.actualizar(carreraId, carrera);
		}
		catch (Exception e) 
		{
			logger.info(e.getMessage());
			throw e;
		}
		
		return new ResponseEntity<Carrera>(carreraActualizada, HttpStatus.OK);
	}

	/**
	 * Endpoint para obtener la lista de las carreras por nombre 
	 * @param nombreCarrera
	 * @return Retorna una lista de carreras 
	 * @author EJNL - 18-02-2021
	 */
	@ApiOperation(value = "Buscar carrera por nombre de carrera")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente")
	})
	@GetMapping("/carrera/lista/nombreCarrera/{nombreCarrera}")
	public ResponseEntity<?> findCarrerasByNombreContains (@PathVariable String nombreCarrera){
		List<Carrera> carreras = (List<Carrera>) carreraDAO.findCarrerasByNombreContains(nombreCarrera);
		return new ResponseEntity<List<Carrera>>(carreras, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para obtener la lista de las carreras por nombre (Ignore case)
	 * @param nombreCarrera
	 * @return Retorna una lista de carreras 
	 * @author EJNL - 18-02-2021
	 */
	@ApiOperation(value = "Buscar carrera por nombre de carrera (Ignore case)")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente")
	})
	@GetMapping("/carrera/lista/nombreCarreraIgnoreCase/{nombreCarrera}")
	public ResponseEntity<?> findCarrerasByNombreContainsIgnoreCase (@PathVariable String nombreCarrera){
		List<Carrera> carreras = (List<Carrera>) carreraDAO.findCarrerasByNombreContainsIgnoreCase(nombreCarrera);
		return new ResponseEntity<List<Carrera>>(carreras, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para obtener una lista de las carreras por nombre y apellido de profesor
	 * @param nombre
	 * @param apellido
	 * @return Retorna una lista de profesores 
	 * @author EJNL - 18-02-2021
	 */
	@ApiOperation(value = "Buscar carrera por nombre y apellido de profesor")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente")
	})
	@GetMapping("/carrera/lista/nombreProfesor/{nombre}/{apellido}")
	public ResponseEntity<?> buscarCarrerasPorProfesorNombreYApellido(@PathVariable String nombre, @PathVariable String apellido){
		List<Carrera> carreras = (List<Carrera>) carreraDAO.buscarCarrerasPorProfesorNombreYApellido(nombre, apellido);
		return new ResponseEntity<List<Carrera>>(carreras, HttpStatus.OK);
	}
}
