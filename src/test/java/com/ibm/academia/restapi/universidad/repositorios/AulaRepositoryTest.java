package com.ibm.academia.restapi.universidad.repositorios;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ibm.academia.restapi.universidad.datos.TestDatos;
import com.ibm.academia.restapi.universidad.enumeradores.TipoPizarron;
import com.ibm.academia.restapi.universidad.modelo.entidades.Aula;
import com.ibm.academia.restapi.universidad.modelo.entidades.Pabellon;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class AulaRepositoryTest {
	
	@Autowired
	AulaRepository aulaRepository;
	@Autowired
	PabellonRepository pabellonRepository;
	
	@BeforeEach
	void setup() {
		aulaRepository.save(TestDatos.aula1());
		aulaRepository.save(TestDatos.aula2());
	}
	
	@AfterEach
	void tearDown() {
		aulaRepository.deleteAll();
	}
	

	@Test
	@DisplayName("Test: Buscar aula por tipo de pizarron")
	void testFindAulaByTipoPizarron() {
		
		//When
		Iterable<Aula> expected = aulaRepository.findAulaByTipoPizarron(TipoPizarron.PIZARRA_BLANCA);
		
		//Then
		assertThat(((List<Aula>)expected).size() == 0).isTrue();
	}

	@Test
	@DisplayName("Test: Buscar aula por nombre de pabellon")
	void testFindAulaByPabellonNombre() {

		Iterable<Aula> aulas = aulaRepository.saveAll(
				Arrays.asList(
						TestDatos.aula1(),
						TestDatos.aula2())
				);
		
		Pabellon save =pabellonRepository.save(TestDatos.pabellon2());
		aulas.forEach(aula -> ((Aula)aula).setPabellon(save));
		
		aulaRepository.saveAll(aulas); 
		
		//When
		Iterable<Aula> expected = aulaRepository.findAulaByPabellonNombre("Pabellon 2");

		//Then
		assertThat(((List<Aula>)expected).size() == 2).isTrue();
	}

	@Test
	@DisplayName("Test: Buscar aula por numero de aula")
	void testFindAulaByNumeroAula() {

		//When
		Iterable<Aula> expected = aulaRepository.findAulaByNumeroAula(12);
		
		//Then
		assertThat(((List<Aula>)expected).size() == 1).isTrue();
	}

}
