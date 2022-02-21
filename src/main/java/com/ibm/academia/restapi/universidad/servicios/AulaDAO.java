package com.ibm.academia.restapi.universidad.servicios;

import com.ibm.academia.restapi.universidad.enumeradores.TipoPizarron;
import com.ibm.academia.restapi.universidad.modelo.entidades.Aula;

public interface AulaDAO extends GenericoDAO<Aula>
{
	public Iterable<Aula> findAulaByTipoPizarron(TipoPizarron tipoPizarron);
	
	public Iterable<Aula> findAulaByPabellonNombre(String nombre);
	
	public Iterable<Aula> findAulaByNumeroAula(Integer numeroAula);
	
	public Aula actualizar (Long aulaId, Aula aula);
}
