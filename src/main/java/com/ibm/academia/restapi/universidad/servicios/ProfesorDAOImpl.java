package com.ibm.academia.restapi.universidad.servicios;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.academia.restapi.universidad.excepciones.NotFoundException;
import com.ibm.academia.restapi.universidad.modelo.entidades.Carrera;
import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;
import com.ibm.academia.restapi.universidad.modelo.entidades.Profesor;
import com.ibm.academia.restapi.universidad.repositorios.PersonaRepository;
import com.ibm.academia.restapi.universidad.repositorios.ProfesorRepository;

@Service
public class ProfesorDAOImpl extends PersonaDAOImpl implements ProfesorDAO 
{
	@Autowired
	private CarreraDAO carreraDAO;
	
	@Autowired
	public ProfesorDAOImpl(@Qualifier("repositorioProfesor")PersonaRepository repository) {
		super(repository);
	}

	@Override
	public Iterable<Persona> findProfesoresByCarrera(String carrera) {
		return ((ProfesorRepository)repository).findProfesoresByCarrera(carrera);
	}	
	
	@Override
	@Transactional
	public Persona actualizar(Long profesorId, Persona profesor) {
		Optional<Persona> oProfesor = repository.findById(profesorId);
			
		if(!oProfesor.isPresent())
			throw new NotFoundException(String.format("El profesor con ID %d no existe", profesorId));
			
		Persona profesorActualizado = null;
		oProfesor.get().setNombre(profesor.getNombre());
		oProfesor.get().setApellido(profesor.getApellido());
		oProfesor.get().setDireccion(profesor.getDireccion());
		profesorActualizado = repository.save(oProfesor.get());
			
		return profesorActualizado;
	}

	@Override
	public Persona asociarCarreraProfesor(List<Long> carrerasId, Long profesorId) {
		   Optional<Persona> oProfesor = repository.findById(profesorId);
           if(!oProfesor.isPresent())
                   throw new NotFoundException(String.format("El profesor con ID %d no existe", profesorId));

           Set<Carrera> carreras = new HashSet<>();
           carrerasId.forEach( carreraId -> {
               Optional<Carrera> oCarrera = carreraDAO.buscarPorId(carreraId);
               if(!oCarrera.isPresent())
                   throw new NotFoundException(String.format("La carrera con ID %d no existe", carreraId));
               else
                   carreras.add(oCarrera.get());
           });
           
           ((Profesor)oProfesor.get()).setCarreras(carreras);
           return repository.save(oProfesor.get());
	}


         
	}

