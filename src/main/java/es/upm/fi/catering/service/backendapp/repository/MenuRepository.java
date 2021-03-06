package es.upm.fi.catering.service.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.upm.fi.catering.service.backendapp.model.Menu;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {	

}
