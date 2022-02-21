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
import com.ibm.academia.restapi.universidad.modelo.entidades.Pabellon;
import com.ibm.academia.restapi.universidad.servicios.PabellonDAO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/restapi")
@Api(value = "Metodos relacionados con los pabellones", tags = "Metodos sobre pabellones")
public class PabellonController {
	
	private final static Logger logger = LoggerFactory.getLogger(PabellonController.class);
	
	@Autowired
	private PabellonDAO pabellonDAO;
	
	
	/**
	 * Endpoint para consultar todas los pabellones
	 * @return Retorna una lista de pabellones
	 * @author Evelyn Neri - 19-02-2021
	 */
	@ApiOperation(value = "Consultar todas los pabellones")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@GetMapping("/pabellon/lista")
	public ResponseEntity<?> listarTodas(){
		List<Pabellon> pabellones = (List<Pabellon>) pabellonDAO.buscarTodos();
		
		if(pabellones.isEmpty())
			throw new NotFoundException("No existen pabellones");
			
		return new ResponseEntity<List<Pabellon>>(pabellones, HttpStatus.OK);
		
	}

	/**
	 * Endpoint para consultar un pabellon por id
	 * @param pabellonId Parametro de busqueda del pabellon
	 * @return Retorna un objeto de tipo pabellon
	 * @NotFoundException En caso de que falle buscando el pabellon
	 * @author Evelyn Neri - 19-02-2021
	 */
	@ApiOperation(value = "Consultar pabellones por id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@GetMapping("/pabellon/pabellonId/{pabellonId}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long pabellonId){
		
		Optional<Pabellon> oPabellon = pabellonDAO.buscarPorId(pabellonId);
		
		if (!oPabellon.isPresent()) {
			throw new NotFoundException(String.format("El pabellon con id: %d no existe", pabellonId));
		}
		return new ResponseEntity<>(oPabellon.get(), HttpStatus.OK);
	}	
	
	/**
	 *Endpoint para guardar un pabellon
	 * @param pabellon
	 * @return Retorna el pabellon guardado
	 * @author Evelyn Neri - 19-02-2021
	 */
	@ApiOperation(value = "Crear pabellones")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@PostMapping("/pabellon")
	public ResponseEntity<?> crearPabellon(@RequestBody Pabellon pabellon, BindingResult result){
		
		Map<String, Object> validaciones = new HashMap<String, Object>();
		if(result.hasErrors()) {
			List<String> listaErrores = result.getFieldErrors()
					.stream()
					.map(errores -> "Campo: '" + errores.getField()+"' " + errores.getDefaultMessage())
					.collect(Collectors.toList());
			validaciones.put("Lista de errores", listaErrores);
			return new ResponseEntity<Map<String, Object>>(validaciones, HttpStatus.BAD_REQUEST);
		
		
		}
		Pabellon pabellonGuardado = pabellonDAO.guardar(pabellon);
		return new ResponseEntity<Pabellon>(pabellonGuardado, HttpStatus.CREATED);
	}
	
	/**
	 * Endpoint para eliminar un pabellon por Id
	 * @param pabellonId
	 * @return Retorna el status
	 * @NotFoundException En caso de que falle buscando el pabellon
	 * @author Evelyn Neri - 19-02-2021 
	 */
	@ApiOperation(value = "Eliminar pabellon por id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Endpoint ejecutado satisfactoriamente pero no devuelve ninguna respuesta"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@DeleteMapping("/pabellon/eliminar/pabellonId/{pabellonId}")
	public ResponseEntity<?> eliminar(@PathVariable Long pabellonId){
		Optional<Pabellon> oPabellon = pabellonDAO.buscarPorId(pabellonId);
		
		if (!oPabellon.isPresent()) 
			throw new NotFoundException(String.format("El pabellon con id: %d no existe", pabellonId));
		
		pabellonDAO.eliminarPorId(pabellonId);
		return new ResponseEntity<>("El pabellon con id: " + pabellonId + "fue eliminado", HttpStatus.NO_CONTENT);
	}
	 

	/**
	 * Endpoint para actualizar los datos de un pabellon por Id
	 * @param pabellonId 
	 * @param pabellon
	 * @return Los datos actualizados del pabellon
	 * @author EJNL - 19-02-2021
	 */
	@ApiOperation(value = "Actualizar pabellon")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 400, message = "No pudo interpretar la solicitud dada una sintaxis inválida")
	})
	@PutMapping("/pabellon/actualizar/pabellonId/{pabellonId}")
	public ResponseEntity<?> actualizar(@PathVariable Long pabellonId, @Valid @RequestBody Pabellon pabellon, BindingResult result)
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
		
		Pabellon pabellonActualizado = null;
		
		try
		{
			pabellonActualizado = pabellonDAO.actualizar(pabellonId, pabellon);
		}
		catch (Exception e) 
		{
			logger.info(e.getMessage());
			throw e;
		}
		
		return new ResponseEntity<Pabellon>(pabellonActualizado, HttpStatus.OK);
		}
	
	/**
	 * Endpoint para obtener una lista de pabellones por localidad
	 * @param localidad
	 * @return Lista de pabellones por localidad
	 * @author EJNL - 19-02-2021
	 */
	@ApiOperation(value = "Buscar pabellones por nombre de localidad")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente")
	})
	@GetMapping("/pabellon/lista/localidad/{localidad}")
	public ResponseEntity<?> buscarPabellonesPorLocalidad(@PathVariable String localidad){
		List<Pabellon> pabellones = (List<Pabellon>) pabellonDAO.findPabellonByDireccionLocalidad(localidad);
		return new ResponseEntity<List<Pabellon>>(pabellones, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para obtener una lista de pabellones por nombre
	 * @param nombrePabellon
	 * @return Lista de pabellones por nombre
	 * @author EJNL - 19-02-2021
	 */
	@ApiOperation(value = "Buscar pabellones por nombre de pabellon")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente")
	})
	@GetMapping("/pabellon/lista/nombrePabellon/{nombrePabellon}")
	public ResponseEntity<?> findPabellonByNombre(@PathVariable String nombrePabellon){
		List<Pabellon> pabellones = (List<Pabellon>) pabellonDAO.findPabellonByNombre(nombrePabellon);
		return new ResponseEntity<List<Pabellon>>(pabellones, HttpStatus.OK);
	}


}
