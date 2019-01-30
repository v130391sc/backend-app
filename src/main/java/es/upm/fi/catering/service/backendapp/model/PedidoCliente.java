package es.upm.fi.catering.service.backendapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="pedidocli")
public class PedidoCliente {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "numero", nullable=false)
	private Integer id;
	//Última fecha de modificación @LastModifiedDate
	@CreationTimestamp
	@Column(name = "fecha_pedido")
	private Date fechaPedido;
	@Column(name = "fecha_entrega")
	private Date fechaEntrega;
	@Column(name = "direccion")
	private String direccion;
	@Column(name = "cod_postal")
	private String codigoPostal;
	@Column(name = "ciudad")
	private String ciudad;
	@Column(name = "provincia")
	private String provincia;
	@Column(name = "n_comensales")
	private Integer numComensales;
	@Column(name = "t_evento")
	private String tEvento;
	@Column(name = "t_servicio")
	private String tServicio;
	@Column(name = "requisistos")
	private String requisitos;
	@Column(name="precio")
	private Double precio;
	@Column(name = "comentarios")
	private String comentarios;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "cliente")
    @OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Client cliente;
	
	@OneToMany(
	        mappedBy = "pedidoCliente", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	@JsonManagedReference
	private List<ProductosPedido> listaProductosPedido = new ArrayList<>();
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_factura")
	private FacturaCliente facturaCliente;

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
	 * @return the fechaPedido
	 */
	public Date getFechaPedido() {
		return fechaPedido;
	}

	/**
	 * @param fechaPedido the fechaPedido to set
	 */
	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	/**
	 * @return the fechaEntrega
	 */
	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	/**
	 * @param fechaEntrega the fechaEntrega to set
	 */
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	/**
	 * @return the numComensales
	 */
	public Integer getNumComensales() {
		return numComensales;
	}

	/**
	 * @param numComensales the numComensales to set
	 */
	public void setNumComensales(Integer numComensales) {
		this.numComensales = numComensales;
	}

	/**
	 * @return the tEvento
	 */
	public String gettEvento() {
		return tEvento;
	}

	/**
	 * @param tEvento the tEvento to set
	 */
	public void settEvento(String tEvento) {
		this.tEvento = tEvento;
	}

	/**
	 * @return the tServicio
	 */
	public String gettServicio() {
		return tServicio;
	}

	/**
	 * @param tServicio the tServicio to set
	 */
	public void settServicio(String tServicio) {
		this.tServicio = tServicio;
	}

	/**
	 * @return the requisitos
	 */
	public String getRequisitos() {
		return requisitos;
	}

	/**
	 * @param requisitos the requisitos to set
	 */
	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}

	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * @return the cliente
	 */
	public Client getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Client cliente) {
		this.cliente = cliente;
	}
	
	public void addProducto(Producto producto, Integer cantidad) {
        ProductosPedido productosPedido = new ProductosPedido(this, producto, cantidad);
        this.listaProductosPedido.add(productosPedido);
    }
 
    public void removeProducto(Producto producto) {
        for (Iterator<ProductosPedido> iterator = this.listaProductosPedido.iterator(); 
             iterator.hasNext(); ) {
        	ProductosPedido productosPedido = iterator.next();
 
            if (productosPedido.getPedidoCliente().equals(this) &&
            		productosPedido.getProducto().equals(producto)) {
                iterator.remove();
                productosPedido.setPedidoCliente(null);
                productosPedido.setProducto(null);
            }
        }
    }

	public List<ProductosPedido> getListaProductosPedido() {
		return listaProductosPedido;
	}

	public void setListaProductosPedido(List<ProductosPedido> listaProductosPedido) {
		this.listaProductosPedido = listaProductosPedido;
	}

	public FacturaCliente getFacturaCliente() {
		return facturaCliente;
	}

	public void setFacturaCliente(FacturaCliente facturaCliente) {
		this.facturaCliente = facturaCliente;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PedidoCliente [id=");
		builder.append(id);
		builder.append(", fechaPedido=");
		builder.append(fechaPedido);
		builder.append(", fechaEntrega=");
		builder.append(fechaEntrega);
		builder.append(", direccion=");
		builder.append(direccion);
		builder.append(", codigoPostal=");
		builder.append(codigoPostal);
		builder.append(", ciudad=");
		builder.append(ciudad);
		builder.append(", provincia=");
		builder.append(provincia);
		builder.append(", numComensales=");
		builder.append(numComensales);
		builder.append(", tEvento=");
		builder.append(tEvento);
		builder.append(", tServicio=");
		builder.append(tServicio);
		builder.append(", requisitos=");
		builder.append(requisitos);
		builder.append(", precio=");
		builder.append(precio);
		builder.append(", comentarios=");
		builder.append(comentarios);
		builder.append(", cliente=");
		builder.append(cliente);
		builder.append(", listaProductosPedido=");
		builder.append(listaProductosPedido);
		builder.append(", facturaCliente=");
		builder.append(facturaCliente);
		builder.append("]");
		return builder.toString();
	}
	
}
