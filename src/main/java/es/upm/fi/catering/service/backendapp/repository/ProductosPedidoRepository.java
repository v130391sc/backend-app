package es.upm.fi.catering.service.backendapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import es.upm.fi.catering.service.backendapp.model.ProductosPedido;

public interface ProductosPedidoRepository extends JpaRepository<ProductosPedido, Integer> {

}
