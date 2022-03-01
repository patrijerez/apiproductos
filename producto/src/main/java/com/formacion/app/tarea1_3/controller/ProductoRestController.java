package com.formacion.app.tarea1_3.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formacion.app.tarea1_3.entity.Producto;
import com.formacion.app.tarea1_3.service.ProductoService;


@RestController
@RequestMapping("/catalogo")
public class ProductoRestController {


		@Autowired
		private ProductoService servicio;

		@GetMapping("/productos")
		public List<Producto> index() {
			return servicio.findAll();
		}

		@GetMapping("/productos/{id}") // 
		public ResponseEntity<?> findProductoById(@PathVariable Long id) {

			Producto p = null;
			Map<String, Object> response = new HashMap<>();

			try {

				p = servicio.findById(id);

			} catch (DataAccessException e) {
				response.put("mensaje", "Error al reallizar consulta a base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

			}

			if (p == null) {
				response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Producto>(p, HttpStatus.OK);
		}

		@PostMapping("/producto/guardarProducto")
		@ResponseStatus(HttpStatus.CREATED)
		public ResponseEntity<?> saveProducto(@RequestBody Producto producto) {
			Map<String, Object> response = new HashMap<>();

			try {
				servicio.save(producto);
			} catch (DataAccessException e) {

				response.put("mensaje", "Error al insertar en la base de datos");
				response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));

				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			response.put("mensaje", "¡El producto ha sido creado con exito!");
			response.put("producto", producto);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
	
		@PutMapping("/producto/{id}")
		@ResponseStatus(HttpStatus.CREATED)
		public ResponseEntity<?> upDateProducto(@RequestBody Producto producto, @PathVariable Long id) {

			Producto productoActual = servicio.findById(id);
			
			Map<String, Object> response = new HashMap<>();
			
			if (productoActual == null) {
				response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try {
				productoActual.setId(id);
				productoActual.setCodigo(producto.getCodigo());
				productoActual.setTipo(producto.getTipo());
				productoActual.setCantidad(producto.getCantidad());
				productoActual.setPrecio(producto.getPrecio());
				productoActual.setMarca(producto.getMarca());
				productoActual.setFechaIngreso(producto.getFechaIngreso());
				productoActual.setDescripcion(producto.getDescripcion());
				

				servicio.save(productoActual);
			} catch (DataAccessException e) {

				response.put("mensaje", "Error al realizar la actualización a la base de datos");
				response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));

				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			response.put("mensaje", "¡El producto ha sido actualizado con exito!");
			response.put("producto", productoActual);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		
		@DeleteMapping("/productos/{id}")
		@ResponseStatus(HttpStatus.OK)
		public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
			
			Producto productoBorrar = servicio.findById(id);
			
			Map<String, Object> response = new HashMap<>();
			
			if (productoBorrar == null) {
				response.put("mensaje", "El producto ID: ".concat(id.toString().concat(" no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try {
				servicio.delete(id);
			} catch (DataAccessException e) {

				response.put("mensaje", "Error al borrar en la base de datos");
				response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));

				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			response.put("mensaje", "¡El producto ha sido borrado con exito!");
			response.put("producto", productoBorrar);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
	
}
