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
import com.ibm.academia.restapi.universidad.enumeradores.TipoPizarron;
import com.ibm.academia.restapi.universidad.modelo.entidades.Aula;
import com.ibm.academia.restapi.universidad.repositorios.AulaRepository;

class AulaDAOImplTest {
	
	AulaDAO aulaDAO;
	AulaRepository aulaRepository;
	
	@BeforeEach
	void setUp()
	{
		aulaRepository = mock(AulaRepository.class);
		aulaDAO = new AulaDAOImpl(aulaRepository);
	}

	@Test
	@DisplayName("Test: Buscar aulas por tipo de pizarron")
	void testFindAulaByTipoPizarron() {
		//Given
        when(aulaRepository.findAulaByTipoPizarron(TipoPizarron.PIZARRA_TIZA))
                .thenReturn(Arrays.asList(TestDatos.aula1(), TestDatos.aula2()));

        //When
        List<Aula> expected = (List<Aula>) aulaDAO.findAulaByTipoPizarron(TipoPizarron.PIZARRA_TIZA);

        //Then
        assertThat(expected.get(0)).isEqualTo(TestDatos.aula1());
        assertThat(expected.get(1)).isEqualTo(TestDatos.aula2());

        verify(aulaRepository).findAulaByTipoPizarron(TipoPizarron.PIZARRA_TIZA);
	}

	@Test
	@DisplayName("Test: Buscar aula por nombre de pabellon")
	void testFindAulaByPabellonNombre() {
		//Given
		String nombrePabellon = "Pabellon 1";
		when(aulaRepository.findAulaByPabellonNombre(nombrePabellon))
			.thenReturn(Arrays.asList());

        //When
        List<Aula> expected = (List<Aula>) aulaDAO.findAulaByPabellonNombre(nombrePabellon);

        //Then
        assertThat(expected.isEmpty());

        verify(aulaRepository).findAulaByPabellonNombre(nombrePabellon);
	}

	@Test
	@DisplayName("Teste: Buscar aula por numero de aula")
	void testFindAulaByNumeroAula() {
		//Given
        when(aulaRepository.findAulaByNumeroAula(21))
                .thenReturn(Arrays.asList(TestDatos.aula2()));

        //When
        List<Aula> expected = (List<Aula>) aulaDAO.findAulaByNumeroAula(21);

        //Then
        assertThat(expected.get(0)).isEqualTo(TestDatos.aula2());

        verify(aulaRepository).findAulaByNumeroAula(21);
	}

}
