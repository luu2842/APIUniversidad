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
import com.ibm.academia.restapi.universidad.enumeradores.TipoEmpleado;
import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class EmpleadoRepositoryTest {
	
	@Autowired
	EmpleadoRepository empleadoRepository;
	
	@BeforeEach
	void setup() {
		empleadoRepository.save(TestDatos.empleado1());
		empleadoRepository.save(TestDatos.empleado2());
	}
	
	@AfterEach
	void tearDown() {
		empleadoRepository.deleteAll();
	}
	

	@Test
	@DisplayName("Test: Buscar empleados por tipo de empleado")
	void testFindEmpleadoByTipoEmpleado() {

		//When
		Iterable<Persona> expected = empleadoRepository.findEmpleadoByTipoEmpleado(TipoEmpleado.ADMINISTRATIVO);
		//Then
		assertThat(((List<Persona>)expected).size() == 1).isTrue();
		
	}
	}

