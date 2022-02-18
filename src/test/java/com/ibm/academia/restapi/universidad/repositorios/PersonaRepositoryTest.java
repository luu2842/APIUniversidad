package com.ibm.academia.restapi.universidad.repositorios;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ibm.academia.restapi.universidad.datos.TestDatos;
import com.ibm.academia.restapi.universidad.modelo.entidades.Empleado;
import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;
import com.ibm.academia.restapi.universidad.modelo.entidades.Profesor;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class PersonaRepositoryTest {
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Autowired
	private ProfesorRepository profesorRepository;;
	
	@AfterEach
	void tearDown() {
		alumnoRepository.deleteAll();
		profesorRepository.deleteAll();
		empleadoRepository.deleteAll();
	}

	@Test
	@DisplayName("Test: Buscar por Nombre y Apellido")
	void buscarPorNombreYApellido()
	{
		//Given
        Persona empleado = empleadoRepository.save(TestDatos.empleado2());

        //When
        Optional<Persona> expected = empleadoRepository.buscarPorNombreYApellido("Cristian", "Morales");
        //Then
        assertThat(expected.get()).isInstanceOf(Empleado.class);
        assertThat(expected.get()).isEqualTo(empleado);
        assertThat(expected.get().getNombre()).isEqualTo(empleado.getNombre());
        assertThat(expected.get().getApellido()).isEqualTo(empleado.getApellido());
	}
	
	@Test
	@DisplayName("Test: Buscar persona por DNI")
	void buscarPorDni() 
	{
		//Given
        Persona profesor = profesorRepository.save(TestDatos.profesor1());

        //When
        Optional<Persona> expected = profesorRepository.buscarPorDni(TestDatos.profesor1().getDni());

        //Then
        assertThat(expected.get()).isInstanceOf(Profesor.class);
        assertThat(expected.get()).isEqualTo(profesor);
        assertThat(expected.get().getDni()).isEqualTo(profesor.getDni());

	}
	
	@Test
	@DisplayName("Test: Buscar persona por Apellido")
	void buscarPersonaPorApellido()
	{
		//Given
		alumnoRepository.save(TestDatos.alumno1());
		alumnoRepository.save(TestDatos.alumno2());
        //When
        String apellido = "Valencia";
        List<Persona> expected = (List<Persona>) alumnoRepository.buscarPersonaPorApellido(apellido);

        //Then
        assertThat(expected.size() == 1).isTrue();
	}
}