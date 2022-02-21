package com.ibm.academia.restapi.universidad.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.academia.restapi.universidad.excepciones.NotFoundException;
import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;
import com.ibm.academia.restapi.universidad.servicios.PersonaDAO;
import com.ibm.academia.restapi.universidad.servicios.ProfesorDAO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/restapi")
@Api(value = "Metodos relacionados con las personas", tags = "Metodos sobre personas")
public class PersonaController {
	
	@Autowired
	@Qualifier("alumnoDAOImpl")
	private PersonaDAO alumnoDAO;
	
	@Autowired
	@Qualifier("empleadoDAOImpl")
	private PersonaDAO empleadoDAO;
	
	@Autowired
	@Qualifier("profesorDAOImpl")
	private ProfesorDAO profesorDAO;
	
	/**
	 * Endpoint para obtener alumnos por dni
	 * @param dniAlumno
	 * @return Lista de alumnos por dni
	 * @author Eve 20-02-21
	 */
	@ApiOperation(value = "Buscar alumnos por dni")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente")
	})
	@GetMapping("/persona/lista/dniAlumno/{dniAlumno}")
	public ResponseEntity<?> buscarPorDniAlumno(@PathVariable String dniAlumno){
		Optional<Persona> oPersona = alumnoDAO.buscarPorDni(dniAlumno);
		
		if (!oPersona.isPresent())
			throw new NotFoundException(String.format("Alumno con dni %s no existe", dniAlumno));
			
		return new ResponseEntity<Persona>(oPersona.get(), HttpStatus.OK);
	}
	
	/**
	 * Endpoint para obtener empleados por dni
	 * @param dniEmpleado
	 * @return Lista de empleados por dni
	 * @author Eve 20-02-21
	 */
	@ApiOperation(value = "Buscar empleados por dni")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente")
	})
	@GetMapping("/persona/lista/dniEmpleado/{dniEmpleado}")
	public ResponseEntity<?> buscarPorDniEmpleado(@PathVariable String dniEmpleado){
		Optional<Persona> oPersona = empleadoDAO.buscarPorDni(dniEmpleado);
		
		if (!oPersona.isPresent())
			throw new NotFoundException(String.format("Empleado con dni %s no existe", dniEmpleado));
			
		return new ResponseEntity<Persona>(oPersona.get(), HttpStatus.OK);
	}
	
	/**
	 * Endpoint para buscar profesores por apellido
	 * @param apellidoProfesor
	 * @return Lista de profesores
	 * @author Eve 20-02-21
	 */
	@ApiOperation(value = "Buscar profesores por apellido")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Endpoint ejecutado satisfactoriamente")
	})
	@GetMapping("/persona/lista/apellidoProfesor/{apellidoProfesor}")
	public ResponseEntity<?> buscarProfesorPorApellido(@PathVariable String apellidoProfesor){
		List<Persona> personas = (List<Persona>) profesorDAO.buscarPersonaPorApellido(apellidoProfesor);
		return new ResponseEntity<List<Persona>>(personas,HttpStatus.OK);
	}
	
}
