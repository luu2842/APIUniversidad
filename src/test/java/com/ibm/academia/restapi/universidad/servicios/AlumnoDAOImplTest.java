package com.ibm.academia.restapi.universidad.servicios;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ibm.academia.restapi.universidad.datos.TestDatos;
import com.ibm.academia.restapi.universidad.modelo.entidades.Alumno;
import com.ibm.academia.restapi.universidad.modelo.entidades.Carrera;
import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;
import com.ibm.academia.restapi.universidad.repositorios.AlumnoRepository;
import com.ibm.academia.restapi.universidad.repositorios.CarreraRepository;

class AlumnoDAOImplTest 
{	
	
	AlumnoDAO alumnoDAO;
	AlumnoRepository alumnoRepository;
	CarreraDAO carreraDAO;
	CarreraRepository carreraRepository;
	
	@BeforeEach
	void setUp()
	{
		alumnoRepository = mock(AlumnoRepository.class);
		alumnoDAO = new AlumnoDAOImpl(alumnoRepository);
	}


	@Test
	@DisplayName("Test: Buscar alumnos por nombre de carrera")
	void testBuscarAlumnosPorNombreCarrera() {
		//Given
		Iterable<Persona> alumnos = alumnoRepository.saveAll(
				Arrays.asList(
						TestDatos.alumno1(),
						TestDatos.alumno2())
				);
		
		Carrera save = carreraRepository.save(TestDatos.carrera1());
		alumnos.forEach(alumno -> ((Alumno)alumno).setCarrera(save));
		
		alumnoRepository.saveAll(alumnos);
		
		String carreraNombre = "Ingenieria en computacion";
		
		when(alumnoRepository.buscarAlumnosPorNombreCarrera(carreraNombre))
        		.thenReturn(Arrays.asList(TestDatos.alumno1(), TestDatos.alumno2()));
		
		//When
		Iterable<Persona> expected = alumnoRepository.buscarAlumnosPorNombreCarrera(carreraNombre);
		
		//Then
		assertThat(((List<Persona>) expected).get(0)).isEqualTo(TestDatos.alumno1());
		assertThat(((List<Persona>) expected).get(1)).isEqualTo(TestDatos.alumno2());
		
		verify(alumnoRepository).buscarAlumnosPorNombreCarrera(carreraNombre);
	}
	

}
