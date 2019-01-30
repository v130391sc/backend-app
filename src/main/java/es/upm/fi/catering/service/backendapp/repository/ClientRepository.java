package es.upm.fi.catering.service.backendapp.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.upm.fi.catering.service.backendapp.model.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, String> {	
	@Modifying
	@Transactional
	@Query("update Client c set c.nombre=:nombre, c.correo=:correo, c.fechaNacimiento=:fechaNacimiento, "
			+ "c.razonSocial=:razonSocial, c.cif=:cif, c.telefono=:telefono, c.alergenos=:alergenos, "
			+ "c.necesidadesAlimentacion=:necesidadesAlimentacion where c.usuario=:usuario")
	public void modificarCliente(@Param("usuario") String usuario, @Param("nombre") String nombre,
			@Param("correo") String correo, @Param("fechaNacimiento") Date fechaNacimiento,
			@Param("razonSocial") String razonSocial, @Param("cif") String cif, @Param("telefono") String telefono,
			@Param("alergenos") String alergenos, @Param("necesidadesAlimentacion") String necesidadesAlimentacion);	

}
