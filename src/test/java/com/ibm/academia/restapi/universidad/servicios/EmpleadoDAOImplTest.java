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
import com.ibm.academia.restapi.universidad.enumeradores.TipoEmpleado;
import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;
import com.ibm.academia.restapi.universidad.repositorios.EmpleadoRepository;

class EmpleadoDAOImplTest {
	
	EmpleadoDAO empleadoDAO;
	EmpleadoRepository empleadoRepository;
	
	@BeforeEach
	void setUp()
	{
		empleadoRepository = mock(EmpleadoRepository.class);
		empleadoDAO = new EmpleadoDAOImpl(empleadoRepository);
	}

	@Test
	@DisplayName("Test: Buscar empleado por tipo de empleado")
	void testFindEmpleadoByTipoEmpleado() {
		//Given
		when(empleadoRepository.findEmpleadoByTipoEmpleado(TipoEmpleado.MANTENIMIENTO))
			.thenReturn(Arrays.asList(TestDatos.empleado2()));

        //When
        List<Persona> expected = (List<Persona>) empleadoDAO.findEmpleadoByTipoEmpleado(TipoEmpleado.MANTENIMIENTO);

        //Then
        assertThat(expected.get(0)).isEqualTo(TestDatos.empleado2());

        verify(empleadoRepository).findEmpleadoByTipoEmpleado(TipoEmpleado.MANTENIMIENTO);
	}

}
