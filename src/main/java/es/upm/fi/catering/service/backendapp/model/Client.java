package es.upm.fi.catering.service.backendapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFilter;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "cliente")
@JsonFilter("client-filter")
public class Client {

	@Id
	@Size(min = 5, max = 40, message = "User should have at least 5 characters")
	@Column(name = "usuario", nullable = false)
	private String usuario;
	@NotNull
	@Size(max = 50)
	@Column(name = "nombre", nullable = false)
	private String nombre;
	@Column(name = "correo", nullable = false)
	private String correo;
	@Past
	@Column(name = "fecha_nacimiento")
	private Date fechaNacimiento;
	@Size(max = 30)
	@Column(name = "razon_social")
	private String razonSocial;
	@Size(max = 10)
	@Column(name = "cif")
	private String cif;
	@Size(max = 12)
	@Column(name = "telefono")
	private String telefono;

	// para ignorar el campo en todas las respuestas @JsonIgnore
	@Column(name = "password")
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_cliente")
	private UserTypeEnum tipoCliente;
	@Size(max = 30)
	@Column(name = "alergenos")
	private String alergenos;
	@Size(max = 30)
	@Column(name = "necesidades_alim")
	private String necesidadesAlimentacion;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PedidoCliente> pedidoCliente = new ArrayList<>();

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * @return the fechaNacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @param fechaNacimiento
	 *            the fechaNacimiento to set
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial
	 *            the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * @return the cif
	 */
	public String getCif() {
		return cif;
	}

	/**
	 * @param cif
	 *            the cif to set
	 */
	public void setCif(String cif) {
		this.cif = cif;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the tipoCliente
	 */
	public UserTypeEnum getTipoCliente() {
		return tipoCliente;
	}

	/**
	 * @param tipoCliente the tipoCliente to set
	 */
	public void setTipoCliente(UserTypeEnum tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	/**
	 * @return the alergenos
	 */
	public String getAlergenos() {
		return alergenos;
	}

	/**
	 * @param alergenos
	 *            the alergenos to set
	 */
	public void setAlergenos(String alergenos) {
		this.alergenos = alergenos;
	}

	/**
	 * @return the necesidadesAlimentacion
	 */
	public String getNecesidadesAlimentacion() {
		return necesidadesAlimentacion;
	}

	/**
	 * @param necesidadesAlimentacion
	 *            the necesidadesAlimentacion to set
	 */
	public void setNecesidadesAlimentacion(String necesidadesAlimentacion) {
		this.necesidadesAlimentacion = necesidadesAlimentacion;
	}

	/**
	 * @return the pedidoCliente
	 */
	public List<PedidoCliente> getPedidoCliente() {
		return pedidoCliente;
	}

	public void setPedidoCliente(List<PedidoCliente> pedidoCliente) {
		this.pedidoCliente = pedidoCliente;
	}

	/**
	 * @param pedidoCliente
	 *            the pedidoCliente to set
	 */
	public void addPedidoCliente(PedidoCliente pedidoCliente) {
		this.pedidoCliente.add(pedidoCliente);
	}
	
	/**
	 * Remove pedido Cliente
	 */
	public void removePedidoCliente(PedidoCliente pedidoCliente) {
		this.pedidoCliente.remove(pedidoCliente);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Client [usuario=");
		builder.append(usuario);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", fechaNacimiento=");
		builder.append(fechaNacimiento);
		builder.append(", razonSocial=");
		builder.append(razonSocial);
		builder.append(", cif=");
		builder.append(cif);
		builder.append(", telefono=");
		builder.append(telefono);
		builder.append(", password=");
		builder.append(password);
		builder.append(", tipoCliente=");
		builder.append(tipoCliente);
		builder.append(", alergenos=");
		builder.append(alergenos);
		builder.append(", necesidadesAlimentacion=");
		builder.append(necesidadesAlimentacion);
		builder.append("]");
		return builder.toString();
	}

}
