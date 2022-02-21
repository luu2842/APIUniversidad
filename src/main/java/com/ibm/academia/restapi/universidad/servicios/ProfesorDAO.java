package com.ibm.academia.restapi.universidad.servicios;

import java.util.List;

import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;

public interface ProfesorDAO extends PersonaDAO 
{
	public Iterable<Persona> findProfesoresByCarrera(String carrera);
	
	public Persona actualizar(Long profesorId, Persona profesor);
	
	public Persona asociarCarreraProfesor(List<Long> carrerasId , Long profesorId);

}
