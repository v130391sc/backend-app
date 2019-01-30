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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="menu")
public class Menu {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_menu", nullable=false)
	private Integer id;
	@Size(max=0)
	@Column(name="nombre", nullable = false)
	private String nombre;
	@Column(name="tipo_menu")
	private Integer tipoMenu;
	@Column(name="precio")
	private Double precio;
	
	@OneToMany(
	        mappedBy = "menu", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	private List<ProductosMenu> listaMenu = new ArrayList<>();
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Integer getTipoMenu() {
		return tipoMenu;
	}
	
	public void setTipoMenu(Integer tipoMenu) {
		this.tipoMenu = tipoMenu;
	}
	
	public Double getPrecio() {
		return precio;
	}
	
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	public void addProductoMenu(Producto producto, Integer cantidad) {
        ProductosMenu productosMenu = new ProductosMenu(this, producto, cantidad);
        this.listaMenu.add(productosMenu);
        producto.getListaProductosMenu().add(productosMenu);
    }
 
    public void removeProducto(Producto producto) {
        for (Iterator<ProductosMenu> iterator = this.listaMenu.iterator(); 
             iterator.hasNext(); ) {
        	ProductosMenu productosMenu = iterator.next();
 
            if (productosMenu.getMenu().equals(this) &&
            		productosMenu.getProductoMenu().equals(producto)) {
                iterator.remove();
                productosMenu.getProductoMenu().getListaProductosMenu().remove(productosMenu);
                productosMenu.setMenu(null);;
                productosMenu.setProductoMenu(null);;
            }
        }
    }

	public List<ProductosMenu> getListaMenu() {
		return listaMenu;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Menu [id=");
		builder.append(id);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", tipoMenu=");
		builder.append(tipoMenu);
		builder.append(", precio=");
		builder.append(precio);
		builder.append("]");
		return builder.toString();
	}
	
	

}
