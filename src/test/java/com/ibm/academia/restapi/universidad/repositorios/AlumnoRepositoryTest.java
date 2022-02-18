package com.ibm.academia.restapi.universidad.repositorios;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ibm.academia.restapi.universidad.datos.TestDatos;
import com.ibm.academia.restapi.universidad.modelo.entidades.Alumno;
import com.ibm.academia.restapi.universidad.modelo.entidades.Carrera;
import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class AlumnoRepositoryTest {
	
	@Autowired
	AlumnoRepository alumnoRepository;
	@Autowired
	CarreraRepository carreraRepository;
	
	@AfterEach
	void tearDown() {
		alumnoRepository.deleteAll();
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
		
		//when
		String carreraNombre = "Ingenieria en computacion";
		List<Persona> expected = (List<Persona>)((AlumnoRepository)alumnoRepository).buscarAlumnosPorNombreCarrera(carreraNombre);

		//then
		assertThat(expected.size() == 2).isTrue();
	}

}
