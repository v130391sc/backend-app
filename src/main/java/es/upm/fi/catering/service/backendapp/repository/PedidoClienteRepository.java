package es.upm.fi.catering.service.backendapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.upm.fi.catering.service.backendapp.model.PedidoCliente;

public interface PedidoClienteRepository extends JpaRepository<PedidoCliente, Integer> {

	@Query("select pc from PedidoCliente pc where pc.fechaEntrega is null order by pc.fechaPedido")
	public List<PedidoCliente> findAllActivos();
}
