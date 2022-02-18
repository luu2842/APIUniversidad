package com.ibm.academia.restapi.universidad.servicios;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.academia.restapi.universidad.enumeradores.TipoPizarron;
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

}
