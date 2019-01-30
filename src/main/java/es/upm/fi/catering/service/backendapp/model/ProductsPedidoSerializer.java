package es.upm.fi.catering.service.backendapp.model;

import java.util.ArrayList;
import java.util.List;

public class ProductsPedidoSerializer {

	private PedidoCliente pedidoCliente;
	
	private List<CantidadProductos> cantidadProductos = new ArrayList<>();

	public PedidoCliente getPedidoCliente() {
		return pedidoCliente;
	}

	public void setPedidoCliente(PedidoCliente pedidoCliente) {
		this.pedidoCliente = pedidoCliente;
	}

	public List<CantidadProductos> getCantidadProductos() {
		return cantidadProductos;
	}

	public void setCantidadProductos(List<CantidadProductos> cantidadProductos) {
		this.cantidadProductos = cantidadProductos;
	}
}
