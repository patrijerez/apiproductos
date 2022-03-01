package com.formacion.app.tarea1_3.service;

import java.util.List;

import com.formacion.app.tarea1_3.entity.Producto;

public interface ProductoService {
	
	public List<Producto> findAll();
	public Producto findById(Long id);
	public Producto save(Producto p);
	public void delete(Long id);
	public Producto deleteConRetorno(Long id);

}
