package com.ibm.academia.restapi.universidad.servicios;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;
import com.ibm.academia.restapi.universidad.repositorios.ProfesorRepository;

class ProfesorDAOImplTest {
	
	ProfesorDAO profesorDAO;
	ProfesorRepository profesorRepository;
	
	@BeforeEach
	void setUp()
	{
		profesorRepository = mock(ProfesorRepository.class);
		profesorDAO = new ProfesorDAOImpl(profesorRepository);
	}

	@Test
	@DisplayName("Test: Buscar profesores por carrera")
	void findProfesoresByCarrera() {
		Iterable<Persona> expected = profesorRepository.findProfesoresByCarrera("Filosofia");
		
		assertThat(((List<Persona>) expected).size() == 1).isFalse();
		
		verify(profesorRepository).findProfesoresByCarrera("Filosofia");
	}
}

