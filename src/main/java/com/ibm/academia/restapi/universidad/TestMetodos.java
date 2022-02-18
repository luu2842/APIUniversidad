package com.ibm.academia.restapi.universidad;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ibm.academia.restapi.universidad.enumeradores.TipoEmpleado;
import com.ibm.academia.restapi.universidad.enumeradores.TipoPizarron;
import com.ibm.academia.restapi.universidad.modelo.entidades.Alumno;
import com.ibm.academia.restapi.universidad.modelo.entidades.Aula;
import com.ibm.academia.restapi.universidad.modelo.entidades.Carrera;
import com.ibm.academia.restapi.universidad.modelo.entidades.Direccion;
import com.ibm.academia.restapi.universidad.modelo.entidades.Empleado;
import com.ibm.academia.restapi.universidad.modelo.entidades.Pabellon;
import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;
import com.ibm.academia.restapi.universidad.modelo.entidades.Profesor;
import com.ibm.academia.restapi.universidad.servicios.AlumnoDAO;
import com.ibm.academia.restapi.universidad.servicios.AulaDAO;
import com.ibm.academia.restapi.universidad.servicios.CarreraDAO;
import com.ibm.academia.restapi.universidad.servicios.EmpleadoDAO;
import com.ibm.academia.restapi.universidad.servicios.PabellonDAO;
import com.ibm.academia.restapi.universidad.servicios.ProfesorDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestMetodos implements CommandLineRunner {

	
	@Autowired
	private AlumnoDAO alumnoDAO;

	@Autowired
	private PabellonDAO pabellonDAO;

	@Autowired
	private AulaDAO aulaDAO;

	@Autowired
	private ProfesorDAO profesorDAO;

	@Autowired
	private CarreraDAO carreraDAO;

	@Autowired
	private EmpleadoDAO empleadoDAO;

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		/*
		//Creacion de 2 alumnos
		Direccion direccionAlumno1 = new Direccion("Sur 2", "2", "22222", "2","2", "Mexico");
		Persona alumno1 = new Alumno(null, "Evelyn", "Neri", "8596741236", "eneri", direccionAlumno1);
		Persona personaAlumno1 = alumnoDAO.guardar(alumno1);
		System.out.println(personaAlumno1.toString());

		Direccion direccionAlumno2 = new Direccion("Guadalupe", "4", "28282", "2","4", "Mexico");
		Persona alumno2 = new Alumno(null, "Thiago", "Valle", "2356899632", "eneri", direccionAlumno2);
		Persona personaAlumno2 = alumnoDAO.guardar(alumno2);
		System.out.println(personaAlumno2.toString());

		//Creacion de 2 aulas
		Aula aula1 = new Aula(null, 1, "200", 20, TipoPizarron.PIZARRA_TIZA,"eneri");
		Aula registroAula1 = aulaDAO.guardar(aula1);
		System.out.println(registroAula1.toString());
		Aula aula2 = new Aula(null, 2, "250", 25, TipoPizarron.PIZARRA_BLANCA,"eneri");
		Aula registroAula2 = aulaDAO.guardar(aula2);
		System.out.println(registroAula2.toString());

		//Creacion de 2 carreras
		Carrera carrera1 = new Carrera(null, "Ingenieria en computacion", 60, 5, "eneri");
		Carrera registroCarrera1 = carreraDAO.guardar(carrera1);
		System.out.println(registroCarrera1.toString());
		Carrera carrera2 = new Carrera(null, "Administracion", 50, 4, "eneri");
		Carrera registroCarrera2 = carreraDAO.guardar(carrera2);
		System.out.println(registroCarrera2.toString());

		//Creacion de 2 empleados
		Direccion direccionEmpleado1 = new Direccion("Sur 88", "8", "84848", "4","8", "Puebla");
		Persona empleado1 = new Empleado(null, "Christian", "Reyes", "8822446600", "eneri", direccionEmpleado1, new BigDecimal("10000.00"), TipoEmpleado.MANTENIMIENTO);
		Persona personaEmpleado1 = empleadoDAO.guardar(empleado1);
		System.out.println(personaEmpleado1);
		Direccion direccionEmpleado2 = new Direccion("Mariano", "12", "12121", "1","2", "Sinaloa");
		Persona empleado2 = new Empleado(null, "Ulises", "Aquino", "9636936963", "eneri", direccionEmpleado2, new BigDecimal("15000.00"), TipoEmpleado.ADMINISTRATIVO);
		Persona personaEmpleado2 = empleadoDAO.guardar(empleado2);
		System.out.println(personaEmpleado2);

		//Creacion de 2 pabellones
		Pabellon pabellon1 = new Pabellon(null, (double) 200, "Pabellon 1", new Direccion("Mariano", "45", "56523", "3", "1", "Mexico"), "eneri");
		Pabellon registroPabellon1 = pabellonDAO.guardar(pabellon1);
		System.out.println(registroPabellon1.toString());
		Pabellon pabellon2 = new Pabellon(null, (double) 250, "Pabellon 2", new Direccion("Morelos", "23", "23123", "2", "3", "Oaxaca"), "eneri");
		Pabellon registroPabellon2 = pabellonDAO.guardar(pabellon2);
		System.out.println(registroPabellon2.toString());

		//Creacion de 2 profesores
		Direccion direccionProfesor1 = new Direccion("Hidalgo", "6", "98123", "21", "6", "Jalisco");
		Persona profesor1 = new Profesor(null, "Erick", "Fuentes", "2244248824", "eneri",direccionProfesor1, new BigDecimal("20000.00"));
		Persona personaProfesor1 = profesorDAO.guardar(profesor1);
		System.out.println(personaProfesor1.toString());
		Direccion direccionProfesor2 = new Direccion("Granaditas", "200", "96852", "3", "1", "Puebla");
		Persona profesor2 = new Profesor(null, "Jorge", "Rodriguez", "2144248824", "eneri",direccionProfesor2, new BigDecimal("28000.00"));
		Persona personaProfesor2 = profesorDAO.guardar(profesor2);
		System.out.println(personaProfesor2.toString());

		*/

		// Asignarle carrera a cada alumno
		/*
		Optional<Persona> alumno1 = alumnoDAO.buscarPorId(1L);
		Optional<Carrera> carrera1 = carreraDAO.buscarPorId(1L);
		Alumno alumnoCarrera1 = (Alumno) alumno1.get();
		alumnoCarrera1.setCarrera(carrera1.get());
		alumnoDAO.guardar(alumnoCarrera1);

		Optional<Persona> alumno2 = alumnoDAO.buscarPorId(2L);
		Optional<Carrera> carrera2 = carreraDAO.buscarPorId(2L);
		Alumno alumnoCarrera2 = (Alumno) alumno2.get();
		alumnoCarrera2.setCarrera(carrera2.get());
		alumnoDAO.guardar(alumnoCarrera2);
		*/

		//Asignarle aulas a pabellon
		/*
		Optional<Pabellon> pabellon1 = pabellonDAO.buscarPorId(1L);
		Optional<Aula> aula1 = aulaDAO.buscarPorId(1L);
		Aula aulaPabellon1 = aula1.get();
		aulaPabellon1.setPabellon(pabellon1.get());
		aulaDAO.guardar(aulaPabellon1);

		Optional<Pabellon> pabellon2 = pabellonDAO.buscarPorId(2L);
		Optional<Aula> aula2 = aulaDAO.buscarPorId(2L);
		Aula aulaPabellon2 = aula2.get();
		aulaPabellon2.setPabellon(pabellon2.get());
		aulaDAO.guardar(aulaPabellon2);
		*/

		//Asignarle pabellones a empleados
		/*
		Optional<Persona> empleado1 = empleadoDAO.buscarPorId(3L);
		Optional<Pabellon> pabellon1 = pabellonDAO.buscarPorId(1L);
		Empleado empleadoPabellon1 = (Empleado) empleado1.get();
		empleadoPabellon1.setPabellon(pabellon1.get());
		empleadoDAO.guardar(empleadoPabellon1);

		Optional<Persona> empleado2 = empleadoDAO.buscarPorId(4L);
		Optional<Pabellon> pabellon2 = pabellonDAO.buscarPorId(2L);
		Empleado empleadoPabellon2 = (Empleado) empleado2.get();
		empleadoPabellon2.setPabellon(pabellon2.get());
		empleadoDAO.guardar(empleadoPabellon2);
		*/

		//Asignar carrera a profesor
		/*
		Optional<Persona> profesor1 = profesorDAO.buscarPorId(5L);
		Optional<Carrera> carrera1 = carreraDAO.buscarPorId(1L);
		Profesor profesorCarrera1 =(Profesor) profesor1.get();
		Set<Carrera> carrerasl = new HashSet<>();
		carrerasl.add(carrera1.get());
		profesorCarrera1.setCarreras(carrerasl);
		profesorDAO.guardar(profesorCarrera1);

		Optional<Persona> profesor2 = profesorDAO.buscarPorId(6L);
		Optional<Carrera> carrera2 = carreraDAO.buscarPorId(2L);
		Profesor profesorCarrera2 =(Profesor) profesor2.get();
		Set<Carrera> carreras2 = new HashSet<>();
		carreras2.add(carrera2.get());
		profesorCarrera2.setCarreras(carreras2);
		profesorDAO.guardar(profesorCarrera2);
		*/
/*
		//Profesor
		Iterable<Persona> profesores = profesorDAO.findProfesoresByCarrera("Administracion");
		profesores.forEach(System.out::println);

		//Empleado
		Iterable<Persona> empleados = empleadoDAO.findEmpleadoByTipoEmpleado(TipoEmpleado.MANTENIMIENTO);
		empleados.forEach(System.out::println);

		//Carrera
		Iterable<Carrera> carreras = carreraDAO.buscarCarrerasPorProfesorNombreYApellido("Erick", "Fuentes");
		carreras.forEach(System.out::println);

		//Aula
		Iterable<Aula> aulas = aulaDAO.findAulaByTipoPizarron(TipoPizarron.PIZARRA_BLANCA);
		aulas.forEach(System.out::println);
	
		Iterable<Aula> aulas1 = aulaDAO.findAulaByPabellonNombre("Pabellon 2");
		aulas1.forEach(System.out::println);
	
		Iterable<Aula> aulas2 = aulaDAO.findAulaByNumeroAula(1);
		aulas2.forEach(System.out::println);

		//Pabellon
		Iterable<Pabellon> pabellones = pabellonDAO.findPabellonByDireccionLocalidad("Oaxaca");
		pabellones.forEach(System.out::println);
		
		Iterable<Pabellon> pabellones2 = pabellonDAO.findPabellonByNombre("Pabellon 1");
		pabellones2.forEach(System.out::println);
		
*/

	}

}
