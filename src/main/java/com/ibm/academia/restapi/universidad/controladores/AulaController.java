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

import com.ibm.academia.restapi.universidad.enumeradores.TipoPizarron;
import com.ibm.academia.restapi.universidad.excepciones.NotFoundException;
import com.ibm.academia.restapi.universidad.modelo.entidades.Aula;
import com.ibm.academia.restapi.universidad.servicios.AulaDAO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/restapi")
@Api(value = "Metodos relacionados con las aulas", tags = "Metodos sobre aulas")
public class AulaController {

	private final static Logger logger = LoggerFactory.getLogger(AulaController.class);
	
	@Autowired
	private AulaDAO aulaDAO;
	
	/**
	 * Endpoint para consultar todas las aulas
	 * @return Retorna una lista de aulas
	 * @author Evelyn Neri - 17-02-2021
	 */
	@ApiOperation(value = "Consultar todas las aulas")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@GetMapping("/aula/lista")
	public ResponseEntity<?> listarTodas(){
		List<Aula> aulas = (List<Aula>) aulaDAO.buscarTodos();
		
		if(aulas.isEmpty())
			throw new NotFoundException("No existen aulas");
			
		return new ResponseEntity<List<Aula>>(aulas, HttpStatus.OK);
	}

	/**
	 * Endpoint para consultar una aula por id
	 * @param aulaId Parametro de busqueda del aula
	 * @return Retorna un objeto de tipo aula
	 * @NotFoundException En caso de que falle buscando la aula
	 * @author Evelyn Neri - 17-02-2021
	 */
	@ApiOperation(value = "Consultar aulas por id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@GetMapping("/aula/aulaId/{aulaId}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long aulaId){
		
		Optional<Aula> oAula = aulaDAO.buscarPorId(aulaId);
		
		if (!oAula.isPresent()) {
			throw new NotFoundException(String.format("La aula con id: %d no existe", aulaId));
		}
		return new ResponseEntity<>(oAula.get(), HttpStatus.OK);
	}	
	
	/**
	 *Endpoint para guardar un aula
	 * @param aula
	 * @return Retorna el aula guardada
	 * @author Evelyn Neri - 17-02-2021
	 */
	@ApiOperation(value = "Crear aulas")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@PostMapping("/aula")
	public ResponseEntity<?> crearAula(@Valid @RequestBody Aula aula, BindingResult result){
		
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
		Aula aulaGuardada = aulaDAO.guardar(aula);
		return new ResponseEntity<Aula>(aulaGuardada, HttpStatus.CREATED);
	}
	
	/**
	 * Endpoint para eliminar un aula por Id
	 * @param aulaId
	 * @return Retorna el status
	 * @NotFoundException En caso de que falle buscando el aula
	 * @author Evelyn Neri - 17-02-2021 
	 */
	@ApiOperation(value = "Eliminar un aula por id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Endpoint ejecutado satisfactoriamente pero no devuelve ninguna respuesta"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@DeleteMapping("/aula/eliminar/aulaId/{aulaId}")
	public ResponseEntity<?> eliminar(@PathVariable Long aulaId){
		Optional<Aula> oAula = aulaDAO.buscarPorId(aulaId);
		
		if (!oAula.isPresent()) 
			throw new NotFoundException(String.format("El aula con id: %d no existe", aulaId));
		
		aulaDAO.eliminarPorId(aulaId);
		return new ResponseEntity<>("El aula con id: " + aulaId + "fue eliminada", HttpStatus.NO_CONTENT);
	}
	 
	/**
	 * Endpoint para actualizar los datos de un aula por Id
	 * @param aulaId 
	 * @param aula
	 * @return Los datos actualizados del aula
	 * @author EJNL - 18-02-2021
	 */
	@ApiOperation(value = "Actualizar aula")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 400, message = "No pudo interpretar la solicitud dada una sintaxis inválida")
	})
	@PutMapping("/aula/actualizar/aulaId/{aulaId}")
	public ResponseEntity<?> actualizar(@PathVariable Long aulaId, @Valid @RequestBody Aula aula, BindingResult result)
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
		
		Aula aulaActualizada = null;
		
		try
		{
			aulaActualizada = aulaDAO.actualizar(aulaId, aula);
		}
		catch (Exception e) 
		{
			logger.info(e.getMessage());
			throw e;
		}
		
		return new ResponseEntity<Aula>(aulaActualizada, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para obtener la lista de aulas por tipo de pizarron
	 * @param tipoPizarron
	 * @return La lista de las aulas
	 * @author EJNL - 18-02-2021
	 */
	@ApiOperation(value = "Buscar aulas por tipo de pizarron")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente")
	})
	@GetMapping("/aula/lista/tipoPizarron/{tipoPizarron}")
	public ResponseEntity<?> findAulasByTipoPizarron(@PathVariable TipoPizarron tipoPizarron){
		List<Aula> aulas = (List<Aula>) aulaDAO.findAulaByTipoPizarron(tipoPizarron);
		return new ResponseEntity<List<Aula>>(aulas,HttpStatus.OK);
	}
	
	/**
	 * Endpoint para obtener la lista de aulas por nombre de pabellon 
	 * @param nombrePabellon
	 * @return lista de aulas
	 * @author EJNL - 18-02-2021
	 */
	@ApiOperation(value = "Buscar aulas por nombre de pabellon")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente")
	})
	@GetMapping("/aula/lista/nombrePabellon/{nombrePabellon}")
	public ResponseEntity<?> findAulasByPabellonNombre(@PathVariable String nombrePabellon){
		List<Aula> aulas = (List<Aula>) aulaDAO.findAulaByPabellonNombre(nombrePabellon);
		return new ResponseEntity<List<Aula>>(aulas, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para obtener la lista de aulas por numero de aula
	 * @param numeroAula
	 * @return Lista de aulas 
	 * @author EJNL - 18-02-2021
	 */
	@ApiOperation(value = "Buscar aulas por numero de aula")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente")
	})
	@GetMapping("/aula/lista/numeroAula/{numeroAula}")
	public ResponseEntity<?> findAulasByNumeroAula(@PathVariable Integer numeroAula){
		List<Aula> aulas = (List<Aula>) aulaDAO.findAulaByNumeroAula(numeroAula);
		return new ResponseEntity<List<Aula>>(aulas, HttpStatus.OK);
	}
	 	
}
