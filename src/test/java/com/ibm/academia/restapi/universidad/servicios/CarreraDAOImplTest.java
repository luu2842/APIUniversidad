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
import com.ibm.academia.restapi.universidad.modelo.entidades.Carrera;
import com.ibm.academia.restapi.universidad.repositorios.CarreraRepository;

class CarreraDAOImplTest
{
	
	CarreraDAO carreraDAO;
	CarreraRepository carreraRepository;
	
	@BeforeEach
	void setUp()
	{
		carreraRepository = mock(CarreraRepository.class);
		carreraDAO = new CarreraDAOImpl(carreraRepository);
	}
	
	@Test
	@DisplayName("Test: Buscar carreras por nombre")
	void findCarrerasByNombreContains()
	{
		//Given
		String nombreCarrera = "Ingenieria";
		when(carreraRepository.findCarrerasByNombreContains(nombreCarrera))
        		.thenReturn(Arrays.asList(TestDatos.carrera1()));
		
		//When
		List<Carrera> expected = (List<Carrera>) carreraDAO.findCarrerasByNombreContains(nombreCarrera);
		
		//Then
		assertThat(expected.get(0)).isEqualTo(TestDatos.carrera1());
		
		verify(carreraRepository).findCarrerasByNombreContains(nombreCarrera);
		
	}
	
	@Test
	@DisplayName("Test: Buscar carreras por nombre NO case sensitive")
	void findCarrerasByNombreContainsIgnoreCase()
	{
		//Given
        String nombre = "ingenieria";
        when(carreraRepository.findCarrerasByNombreContainsIgnoreCase(nombre))
                .thenReturn(Arrays.asList(TestDatos.carrera1()));

        //When
        List<Carrera> expected = (List<Carrera>) carreraDAO.findCarrerasByNombreContainsIgnoreCase(nombre);

        //Then
        assertThat(expected.get(0)).isEqualTo(TestDatos.carrera1());

        verify(carreraRepository).findCarrerasByNombreContainsIgnoreCase(nombre);
	}
	
	@Test
	@DisplayName("Test: Buscar carreras mayores a N anios")
	void findCarrerasByCantidadAniosAfter()
	{
		//Given
        Integer cantidad = 4;
        when(carreraRepository.findCarrerasByCantidadAniosAfter(cantidad))
                .thenReturn(Arrays.asList(TestDatos.carrera2()));

        //When
        List<Carrera> expected = (List<Carrera>) carreraDAO.findCarrerasByCantidadAniosAfter(cantidad);

        //Then
        assertThat(expected.get(0)).isEqualTo(TestDatos.carrera2());

        verify(carreraRepository).findCarrerasByCantidadAniosAfter(cantidad);
	}
//}

	@Test
	@DisplayName("Test: Buscar carreras por nombre y apellido de profesor")
	void testBuscarCarrerasPorProfesorNombreYApellido() {
		//Given
		when(carreraRepository.buscarCarrerasPorProfesorNombreYApellido("Erick", "Adame"))
			.thenReturn(Arrays.asList());

        //When
        List<Carrera> expected = (List<Carrera>) carreraDAO.buscarCarrerasPorProfesorNombreYApellido("Erick", "Adame");

        //Then
        assertThat(expected.isEmpty());

        verify(carreraRepository).buscarCarrerasPorProfesorNombreYApellido("Erick", "Adame");
	}

}
