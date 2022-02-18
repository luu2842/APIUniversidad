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
import com.ibm.academia.restapi.universidad.modelo.entidades.Carrera;
import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class ProfesorRepositoryTest {
	
	@Autowired
	ProfesorRepository profesorRepository;
	@Autowired
	CarreraRepository carreraRepository;

	@AfterEach
	void tearDown() {
		profesorRepository.deleteAll();
	}
	
	@Test
	@DisplayName("Test: Buscar profesor por carrera")
	void testFindProfesoresByCarrera() {
		//Given
		Iterable<Persona> profesores = profesorRepository.saveAll(
				Arrays.asList(
						TestDatos.profesor1(),
						TestDatos.profesor2())
				);

		Iterable<Carrera> carreras = carreraRepository.saveAll(
				Arrays.asList(
						TestDatos.carrera1(),
						TestDatos.carrera2())
				);
		 
		//when
		List<Persona> expected = (List<Persona>)((ProfesorRepository)profesorRepository).findProfesoresByCarrera("Administracion");

		//then
		assertThat(expected.size() == 2).isFalse();
	}

}
