package es.upm.fi.catering.service.backendapp.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="prod_menu")
public class ProductosMenu {
	
	@EmbeddedId
	private ProductosMenuPK id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idMenu")
	private Menu menu;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idProd")
	private Producto productoMenu;
	
	@Column(name="cantidad")
	private Integer cantidad;
	
	private ProductosMenu() {}

	public ProductosMenu(Menu menu, Producto productoMenu, Integer cantidad) {
		this.id = new ProductosMenuPK(menu.getId(), productoMenu.getId());
		this.menu = menu;
		this.productoMenu = productoMenu;
		this.cantidad = cantidad;
	}

	public ProductosMenuPK getId() {
		return id;
	}

	public void setId(ProductosMenuPK id) {
		this.id = id;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Producto getProductoMenu() {
		return productoMenu;
	}

	public void setProductoMenu(Producto productoMenu) {
		this.productoMenu = productoMenu;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        ProductosMenu that = (ProductosMenu) o;
        return Objects.equals(menu, that.menu) &&
               Objects.equals(productoMenu, that.productoMenu);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(menu, productoMenu);
    }
	
	

}
