package es.upm.fi.catering.service.backendapp.model;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductosPedidoPK implements Serializable {

	@Column(name = "id_pedido")
	private Integer idPedido;
	@Column(name = "id_producto")
	private Integer idProducto;

	public ProductosPedidoPK() {
	}

	public ProductosPedidoPK(Integer idPedido, Integer idProducto) {
		this.idPedido = idPedido;
		this.idProducto = idProducto;
	}

	public Integer getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		ProductosPedidoPK that = (ProductosPedidoPK) o;
		return Objects.equals(idPedido, that.idPedido) && Objects.equals(idProducto, that.idProducto);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPedido, idProducto);
	}

}
