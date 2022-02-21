package com.ibm.academia.restapi.universidad.servicios;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.academia.restapi.universidad.enumeradores.TipoPizarron;
import com.ibm.academia.restapi.universidad.excepciones.NotFoundException;
import com.ibm.academia.restapi.universidad.modelo.entidades.Aula;
import com.ibm.academia.restapi.universidad.repositorios.AulaRepository;

@Service
public class AulaDAOImpl extends GenericoDAOImpl<Aula, AulaRepository> implements AulaDAO
{

	public AulaDAOImpl(AulaRepository aulaRepository) {
		super(aulaRepository);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Aula> findAulaByTipoPizarron(TipoPizarron tipoPizarron) {
		return repository.findAulaByTipoPizarron(tipoPizarron);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Aula> findAulaByPabellonNombre(String nombre) {
		return repository.findAulaByPabellonNombre(nombre);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Aula> findAulaByNumeroAula(Integer numeroAula) {
		return repository.findAulaByNumeroAula(numeroAula);
	}

	@Override
	@Transactional
	public Aula actualizar(Long aulaId, Aula aula) {
		
		Optional<Aula> oAula = repository.findById(aulaId);
		
		if(!oAula.isPresent())
			throw new NotFoundException(String.format("La aula con ID %d no existe", aulaId)); 
		
		Aula aulaActualizada = null;
		oAula.get().setCantidadPupitres(aula.getCantidadPupitres());
		oAula.get().setMedidas(aula.getMedidas());
		oAula.get().setTipoPizarron(aula.getTipoPizarron());
		
		aulaActualizada = repository.save(oAula.get());
		return aulaActualizada;
		
	}

}

