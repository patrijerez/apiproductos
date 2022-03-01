package com.formacion.app.tarea1_3.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formacion.app.tarea1_3.entity.Producto;


@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {

}
