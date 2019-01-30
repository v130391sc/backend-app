package es.upm.fi.catering.service.backendapp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="producto")
public class Producto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_producto", nullable=false)
	private Integer id;
	@Column(name="nombre", nullable = false)
	private String nombre;
	@Column(name="coste")
	private Double costeUnidad;
	@Column(name="disp")
	private Integer disp;
	@Column(name="url_img")
	private String nombreImg;
	@Column(name="contador")
	private Integer contador=1;
	
	@ManyToMany
	@JoinTable(name = "prod_ing",
	joinColumns = @JoinColumn(name = "id_prod"),
	inverseJoinColumns = @JoinColumn(name = "id_ing"))
	private List<Ingrediente> listaIngredientes = new ArrayList<>();
	
	@OneToMany(
			mappedBy = "productoMenu",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	@JsonIgnore
	private List<ProductosMenu> listaProductosMenu = new ArrayList<>();

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
	public Double getCosteUnidad() {
		return costeUnidad;
	}
	public void setCosteUnidad(Double costeUnidad) {
		this.costeUnidad = costeUnidad;
	}

	public Integer getDisp() {
		return disp;
	}
	public void setDisp(Integer disp) {
		this.disp = disp;
	}

	public String getNombreImg() {
		return nombreImg;
	}
	public void setNombreImg(String nombreImg) {
		this.nombreImg = nombreImg;
	}
	public Integer getContador() {
		return contador;
	}
	public void setContador(Integer contador) {
		this.contador = contador;
	}
	
	public void addIngrediente(Ingrediente ingrediente) {
		this.listaIngredientes.add(ingrediente);
    }
	
	public void removeIngrediente(Ingrediente ingrediente) {
		this.listaIngredientes.remove(ingrediente);
	}

	public void setListaIngredientes(List<Ingrediente> listaIngredientes) {
		this.listaIngredientes = listaIngredientes;
	}
	/**
	 * @return the listaIngredientes
	 */
	public List<Ingrediente> getListaIngredientes() {
		return listaIngredientes;
	}
	
	public List<ProductosMenu> getListaProductosMenu() {
		return listaProductosMenu;
	}
	public void setListaProductosMenu(List<ProductosMenu> listaProductosMenu) {
		this.listaProductosMenu = listaProductosMenu;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Producto [id=");
		builder.append(id);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", costeUnidad=");
		builder.append(costeUnidad);
		builder.append(", unidades=");
		builder.append(", precio=");
		builder.append(", disp=");
		builder.append(disp);
		builder.append(", nombreImg=");
		builder.append(nombreImg);
		builder.append(", listaIngredientes=");
		builder.append(listaIngredientes);
		builder.append(", listaProductosMenu=");
		builder.append(listaProductosMenu);
		builder.append("]");
		return builder.toString();
	}
	
}
