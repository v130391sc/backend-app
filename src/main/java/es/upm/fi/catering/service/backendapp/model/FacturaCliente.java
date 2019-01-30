package es.upm.fi.catering.service.backendapp.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "facturacli")
public class FacturaCliente {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="numero", nullable=false)
	private Integer numero;
	@CreationTimestamp
	@Column(name="fecha")
	private Date fecha;
	@Column(name="concepto")
	private String concepto;
	@Column(name="t_impuesto")
	private String impuesto;
	@Column(name="coste")
	private Double coste;
	
	@OneToOne(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			mappedBy = "facturaCliente")
	@JsonIgnore
	private PedidoCliente pedidoCliente;

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(String impuesto) {
		this.impuesto = impuesto;
	}

	public Double getCoste() {
		return coste;
	}

	public void setCoste(Double coste) {
		this.coste = coste;
	}

	public PedidoCliente getPedidoCliente() {
		return pedidoCliente;
	}

	public void setPedidoCliente(PedidoCliente pedidoCliente) {
		this.pedidoCliente = pedidoCliente;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FacturaCliente [numero=");
		builder.append(numero);
		builder.append(", fecha=");
		builder.append(fecha);
		builder.append(", concepto=");
		builder.append(concepto);
		builder.append(", impuesto=");
		builder.append(impuesto);
		builder.append(", coste=");
		builder.append(coste);
		builder.append("]");
		return builder.toString();
	}
}
