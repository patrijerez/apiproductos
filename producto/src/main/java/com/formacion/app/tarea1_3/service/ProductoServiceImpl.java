package com.formacion.app.tarea1_3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacion.app.tarea1_3.dao.ProductoRepository;
import com.formacion.app.tarea1_3.entity.Producto;


@Service
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	private ProductoRepository pr;

	@Override
	@Transactional(readOnly=true)
	public List<Producto> findAll() {		
		return (List<Producto>) pr.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Producto findById(Long id) {		
		return pr.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Producto save(Producto p) {		
		return pr.save(p);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		pr.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Producto deleteConRetorno(Long id) {				
		Producto p = pr.findById(id).get();
		pr.deleteById(id);
		return p;
	}

}
