package es.upm.fi.catering.service.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.upm.fi.catering.service.backendapp.model.FacturaCliente;

@Repository
public interface FacturaClienteRepository extends JpaRepository<FacturaCliente, Integer> {	
	
}
