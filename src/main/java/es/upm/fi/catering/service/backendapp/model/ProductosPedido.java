package es.upm.fi.catering.service.backendapp.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="productos_pedido")
public class ProductosPedido {
	
	@EmbeddedId
	private ProductosPedidoPK id;
	
	@ManyToOne
	@MapsId("idPedido")
	@JsonBackReference
	private PedidoCliente pedidoCliente;
	
	@ManyToOne
	@MapsId("idProducto")
	private Producto producto;
	
	@Column(name="cantidad")
	private Integer cantidad;
	
	
	private ProductosPedido() {}

	public ProductosPedido(PedidoCliente pedidoCliente, Producto producto, Integer cantidad) {
		this.id = new ProductosPedidoPK(pedidoCliente.getId(), producto.getId());
		this.pedidoCliente = pedidoCliente;
		this.producto = producto;
		this.cantidad = cantidad;
	}
	
	public ProductosPedidoPK getId() {
		return id;
	}

	public void setId(ProductosPedidoPK id) {
		this.id = id;
	}

	public PedidoCliente getPedidoCliente() {
		return pedidoCliente;
	}

	public void setPedidoCliente(PedidoCliente pedidoCliente) {
		this.pedidoCliente = pedidoCliente;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
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
 
        ProductosPedido that = (ProductosPedido) o;
        return Objects.equals(pedidoCliente, that.pedidoCliente) &&
               Objects.equals(producto, that.producto);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(pedidoCliente, producto);
    }
	
	

}
