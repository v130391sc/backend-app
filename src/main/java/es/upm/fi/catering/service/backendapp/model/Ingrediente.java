package es.upm.fi.catering.service.backendapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ingrediente")
public class Ingrediente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ingrediente", nullable = false)
	private Integer id;
	@Size(max=20)
	@Column(name="nombre", nullable = false)
	private String nombre;
	@Size(max=20)
	@Column(name="marca")
	private String marca;
	@Size(max=30)
	@Column(name="alergenos")
	private String alergenos;
	
	@ManyToMany(mappedBy="listaIngredientes")
	@JsonIgnore
	private List<Producto> listaProductos = new ArrayList<>();
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * @return the alergenos
	 */
	public String getAlergenos() {
		return alergenos;
	}

	/**
	 * @param alergenos the alergenos to set
	 */
	public void setAlergenos(String alergenos) {
		this.alergenos = alergenos;
	}

	/**
	 * @return the listaProductos
	 */
	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	/**
	 * @param listaProductos the listaProductos to set
	 */
	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ingrediente [id=");
		builder.append(id);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", marca=");
		builder.append(marca);
		builder.append(", alergenos=");
		builder.append(alergenos);
		builder.append("]");
		return builder.toString();
	}
}
