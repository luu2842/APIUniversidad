package com.ibm.academia.restapi.universidad.servicios;

import java.util.Optional;

public interface GenericoDAO <E>
{
	public Iterable<E> buscarTodos ();
	public E guardar (E entidad);
	public Optional<E> buscarPorId(Long id);
	public void eliminarPorId (Long id);
}
