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
import com.ibm.academia.restapi.universidad.modelo.entidades.Pabellon;
import com.ibm.academia.restapi.universidad.repositorios.PabellonRepository;

class PabellonDAOImplTest {
	
	PabellonDAO pabellonDAO;
	PabellonRepository pabellonRepository;
	
	@BeforeEach
	void setUp()
	{
		pabellonRepository= mock(PabellonRepository.class);
		pabellonDAO = new PabellonDAOImpl(pabellonRepository);
	}

	@Test
	@DisplayName("Test: Buscar pabellon por nombre")
	void testFindPabellonByNombre() {
		//Given
		when(pabellonRepository.findPabellonByNombre("Pabellon 1"))
			.thenReturn(Arrays.asList(TestDatos.pabellon1()));

        //When
        List<Pabellon> expected = (List<Pabellon>) pabellonDAO.findPabellonByNombre("Pabellon 1");

        //Then
        assertThat(expected.get(0)).isEqualTo(TestDatos.pabellon1());

        verify(pabellonRepository).findPabellonByNombre("Pabellon 1");
	}

	@Test
	@DisplayName("Test: Buscar Pabellon por localidad")
	void testFindPabellonByDireccionLocalidad() {
		//Given
		when(pabellonRepository.findPabellonByDireccionLocalidad("Puebla"))
			.thenReturn(Arrays.asList(TestDatos.pabellon2()));
		
		//When
		List<Pabellon> expected = (List<Pabellon>) pabellonDAO.findPabellonByDireccionLocalidad("Puebla");
		
		//Then
		assertThat(expected.get(0)).isEqualTo(TestDatos.pabellon2());
		
		verify(pabellonRepository).findPabellonByDireccionLocalidad("Puebla");
	}

}
