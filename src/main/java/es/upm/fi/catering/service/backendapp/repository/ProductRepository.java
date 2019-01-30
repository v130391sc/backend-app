package es.upm.fi.catering.service.backendapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.upm.fi.catering.service.backendapp.model.Producto;

@Repository
public interface ProductRepository extends JpaRepository<Producto, Integer> {

	public List<Producto> findAll();

	public Optional<Producto> findByNombre(String nombre);

	public void deleteById(Integer id);

	@Query("select p from Producto p where LOWER(p.nombre) LIKE LOWER('%' || :nombre || '%') AND p.disp = 1")
	public List<Producto> findAllByNom(
			@Param("nombre") String nombre);

	@Query("select p from Producto p where LOWER(p.nombre) LIKE LOWER('%' || :nombre || '%') AND p.costeUnidad <= :pMax AND p.disp = 1 order by p.costeUnidad")
	public List<Producto> findAllByNombreAndPrecioMax(
			@Param("nombre") String nombre, @Param("pMax") Double pMax);
	
	@Query("select p from Producto p where LOWER(p.nombre) LIKE LOWER('%' || :nombre || '%') AND p.costeUnidad >= :pMin AND p.disp = 1 order by p.costeUnidad")
	public List<Producto> findAllByNombreAndPrecioMin(
			@Param("nombre") String nombre, @Param("pMin") Double pMin);
	
	@Query("select p from Producto p where LOWER(p.nombre) LIKE LOWER('%' || :nombre || '%') AND (p.costeUnidad >= :pMin AND p.costeUnidad <= :pMax) AND p.disp = 1 order by p.costeUnidad")
	public List<Producto> findAllByNombreAndPrecioMinAndPrecioMax(
			@Param("nombre") String nombre, @Param("pMin") Double pMin, @Param("pMax") Double pMax);
	
	@Query("select p from Producto p where p.costeUnidad >= :pMin AND p.costeUnidad <= :pMax AND p.disp = 1 order by p.costeUnidad")
	public List<Producto> findAllByPrecioMinAndPrecioMax(
			@Param("pMin") Double pMin, @Param("pMax") Double pMax);
	
	@Query("select p from Producto p where p.costeUnidad <= :pMax AND p.disp = 1 order by p.costeUnidad")
	public List<Producto> findAllByPrecioMax(
			@Param("pMax") Double pMax);
	
	@Query("select p from Producto p where p.costeUnidad >= :pMin AND p.disp = 1 order by p.costeUnidad")
	public List<Producto> findAllByPrecioMin(
			@Param("pMin") Double pMin);
	
	@Query("select p from Producto p where p.disp = 1")
	public List<Producto> findAllDisp();
	
	@Query("select p from Producto p where LOWER(p.nombre) LIKE LOWER('%' || :nombre || '%')")
	public List<Producto> findAllByNombre(@Param("nombre") String nombre);
}
