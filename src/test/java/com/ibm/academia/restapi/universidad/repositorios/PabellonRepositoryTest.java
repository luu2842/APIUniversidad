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
import com.ibm.academia.restapi.universidad.modelo.entidades.Pabellon;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class PabellonRepositoryTest {
	
	@Autowired
	PabellonRepository pabellonRepository;
	
	@BeforeEach
	void setup() {
		pabellonRepository.save(TestDatos.pabellon1());
		pabellonRepository.save(TestDatos.pabellon2());
	}
	
	@AfterEach
	void tearDown() {
		pabellonRepository.deleteAll();
	}
	

	@Test
	@DisplayName("Test: Buscar pabellon por localidad")
	void testFindPabellonByDireccionLocalidad() {
		//When
		Iterable<Pabellon> expected = pabellonRepository.findPabellonByDireccionLocalidad("Puebla");
		//Then
		assertThat(((List<Pabellon>)expected).size() == 1).isTrue();
	}

	@Test
	@DisplayName("Test: Buscar pabellon por nombre")
   	 void testFindPabellonByNombre() {
		//When
		Iterable<Pabellon> expected = pabellonRepository.findPabellonByNombre("Pabellon 2");
		//Then
		assertThat(((List<Pabellon>)expected).size() == 1).isTrue();
	}

}
