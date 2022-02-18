package com.ibm.academia.restapi.universidad.repositorios;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ibm.academia.restapi.universidad.datos.TestDatos;
import com.ibm.academia.restapi.universidad.modelo.entidades.Carrera;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class CarreraRepositoryTest {
	
	@Autowired
	CarreraRepository carreraRepository;

	@BeforeEach
	void setup() {
		carreraRepository.save(TestDatos.carrera1());
		carreraRepository.save(TestDatos.carrera2());
	}
	
	@AfterEach
	void tearDown() {
		carreraRepository.deleteAll();
	}
	
	
	@Test
	@DisplayName("Test: Buscar carreras por nombre")
	void testFindCarrerasByNombreContains() {
		
		//When
		Iterable<Carrera> expected = carreraRepository.findCarrerasByNombreContains("Administracion");
		
		
		//Then
		assertThat(((List<Carrera>)expected).size() == 1).isTrue();
		
	}

	@Test
	@DisplayName("Test: Buscar carreras por nombre")
	void testFindCarrerasByNombreContainsIgnoreCase() {
		
		//When
		Iterable<Carrera> expected = carreraRepository.findCarrerasByNombreContainsIgnoreCase("ADmINisTRaCIon");
		
		//Then
		assertThat(((List<Carrera>)expected).size() == 1).isTrue();
		
	}

	@Test
	@DisplayName("Test: Buscar carreras por cantidad de años")
	void testFindCarrerasByCantidadAniosAfter() {
		
		//When
		Iterable<Carrera> expected = carreraRepository.findCarrerasByCantidadAniosAfter(4);
		
		//Then
		assertThat(((List<Carrera>)expected).size() == 1).isTrue();
		
	}

	@Test
	@DisplayName("Test: Buscar carreras por nombre y apellido de un profesor")
	void testBuscarCarrerasPorProfesorNombreYApellido() {
		
		//When
		Iterable<Carrera> expected = carreraRepository.buscarCarrerasPorProfesorNombreYApellido("Juan", "Perez");
		
		//Then
		assertThat(((List<Carrera>)expected).size() == 0).isTrue();
		
	}

}
