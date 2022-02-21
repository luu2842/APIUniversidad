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

import com.ibm.academia.restapi.universidad.enumeradores.TipoEmpleado;
import com.ibm.academia.restapi.universidad.excepciones.NotFoundException;
import com.ibm.academia.restapi.universidad.modelo.entidades.Empleado;
import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;
import com.ibm.academia.restapi.universidad.servicios.EmpleadoDAO;
import com.ibm.academia.restapi.universidad.servicios.PersonaDAO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/restapi")
@Api(value = "Metodos relacionados con los empleados", tags = "Metodos sobre empleados")
public class EmpleadoController {

	private final static Logger logger = LoggerFactory.getLogger(EmpleadoController.class);
	
	@Autowired
	@Qualifier("empleadoDAOImpl")
	private PersonaDAO empleadoDAO;
	
	/**
	 * Endpoint para consultar todas los empleados
	 * @return Retorna una lista de empleados
	 * @author Evelyn Neri - 18-02-2021
	 */
	@ApiOperation(value = "Consultar todas los empleados")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@GetMapping("/empleado/lista")
	public ResponseEntity<?> obtenerTodos(){
		List<Persona> empleados = (List<Persona>) empleadoDAO.buscarTodos();
			
		if(empleados.isEmpty())
			throw new NotFoundException("No existen empleados");
			
		return new ResponseEntity<List<Persona>>(empleados, HttpStatus.OK);
	}
		
	/**
	 * Endpoint para consultar una empleado por id
	 * @param empleadoId Parametro de busqueda del empleado
	 * @return Retorna un objeto de tipo empleado
	 * @NotFoundException En caso de que falle buscando al empleado
	 * @author Evelyn Neri - 18-02-2021
	 */
	@ApiOperation(value = "Consultar empleados por id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@GetMapping("/empleado/empleadoId/{empleadoId}")
	public ResponseEntity<?> obtenerEmpleadoPorId(@PathVariable Long empleadoId){
		Optional<Persona> oEmpleado = empleadoDAO.buscarPorId(empleadoId);
		
		if (!oEmpleado.isPresent())
			throw new NotFoundException(String.format("Empleado con id %d no existe", empleadoId));
		
		return new ResponseEntity<Persona>(oEmpleado.get(), HttpStatus.OK);
	}
	
	/**
	 *Endpoint para guardar un empleado
	 * @param empleado
	 * @return Retorna el empleado guardado
	 * @author Evelyn Neri - 18-02-2021
	 */
	@ApiOperation(value = "Crear empleados")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@PostMapping("/empleado")
	public ResponseEntity<?> crearEmpleado(@RequestBody Persona empleado){
		Persona empleadoGuardado = empleadoDAO.guardar(empleado);
		return new ResponseEntity<Persona>(empleadoGuardado, HttpStatus.CREATED);
	}
			
	/**
	 * Endpoint para eliminar un empleado por Id
	 * @param empleadoId
	 * @return Retorna el status
	 * @NotFoundException En caso de que falle buscando al empleado
	 * @author Evelyn Neri - 18-02-2021 
	 */
	@ApiOperation(value = "Eliminar un empleado por id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Endpoint ejecutado satisfactoriamente pero no devuelve ninguna respuesta"),
		@ApiResponse(code = 404, message = "No hay elementos en la base de datos")
	})
	@DeleteMapping("/empleado/eliminar/empleadoId/{empleadoId}")
	public ResponseEntity<?> eliminarEmpleado(@PathVariable Long empleadoId){
		Optional<Persona> oEmpleado = empleadoDAO.buscarPorId(empleadoId);
		
		if(!oEmpleado.isPresent())
			throw new NotFoundException(String.format("El empleado con ID %d no existe", empleadoId));
			
		empleadoDAO.eliminarPorId(oEmpleado.get().getId()); 
		return new ResponseEntity<String>("Empleado ID: " + empleadoId + " se elimino satisfactoriamente",  HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Endpoint para actualizar los datos de un empleado por Id
	 * @param empleadoId 
	 * @param empleado
	 * @return Los datos actualizados del empleado
	 * @author EJNL - 18-02-2021
	 */
	@ApiOperation(value = "Actualizar empleado")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente"),
		@ApiResponse(code = 400, message = "No pudo interpretar la solicitud dada una sintaxis inválida")
	})
	@PutMapping("/empleado/actualizar/empleadoId/{empleadoId}")
	public ResponseEntity<?> actualizar(@PathVariable Long empleadoId, @Valid @RequestBody Empleado empleado, BindingResult result)
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
		
		Empleado empleadoActualizado = null;
		
		try
		{
			empleadoActualizado = (Empleado) ((EmpleadoDAO)empleadoDAO).actualizar(empleadoId, empleado);
		}
		catch (Exception e) 
		{
			logger.info(e.getMessage());
			throw e;
		}
		
		return new ResponseEntity<Persona>(empleadoActualizado, HttpStatus.OK);

	}
	
	/**
	 * Endpoint para obtener una lista de empleados por tipo de empleado
	 * @param tipoEmpleado
	 * @return Lista de empleados
	 * @author EJNL - 18-02-2021
	 */
	@ApiOperation(value = "Buscar empleado por tipo de empleado")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente")
	})
	@GetMapping("/empleado/lista/tipoEmpleado/{tipoEmpleado}")
	public ResponseEntity<?> findEmpleadoByTipoEmpleado(@PathVariable TipoEmpleado tipoEmpleado){
		List<Persona> empleados = (List<Persona>) ((EmpleadoDAO)empleadoDAO).findEmpleadoByTipoEmpleado(tipoEmpleado);
		return new ResponseEntity<List<Persona>>(empleados, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para asignar pabellon a empleado
	 * @param pabellonId
	 * @param empleadoId
	 * @return Los datos del empleado con el pabellon asignado
	 * @author EJNL - 18-02-2021
	 */
	@ApiOperation(value = "Asignar pabellon a empleado")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente")
	})
	@PutMapping("/empleado/asociar-pabellon")
	public ResponseEntity<?> asignarPabellonEmpleado(@RequestParam Long pabellonId, @RequestParam(name = "empleado_id") Long empleadoId){
		Persona empleado = ((EmpleadoDAO)empleadoDAO).asociarPabellonEmpleado(pabellonId, empleadoId);
		return new ResponseEntity<Persona>(empleado, HttpStatus.OK);
	}
	
	
}
