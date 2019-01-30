package es.upm.fi.catering.service.backendapp.model;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductosMenuPK implements Serializable {

	@Column(name = "id_menu")
	private Integer idMenu;
	@Column(name = "id_prod")
	private Integer idProd;

	public ProductosMenuPK() {
	}

	public ProductosMenuPK(Integer idMenu, Integer idProd) {
		this.idMenu = idMenu;
		this.idProd = idProd;
	}

	/**
	 * @return the idMenu
	 */
	public Integer getIdMenu() {
		return idMenu;
	}

	/**
	 * @param idMenu the idMenu to set
	 */
	public void setIdMenu(Integer idMenu) {
		this.idMenu = idMenu;
	}

	/**
	 * @return the idProd
	 */
	public Integer getIdProd() {
		return idProd;
	}

	/**
	 * @param idProd the idProd to set
	 */
	public void setIdProd(Integer idProd) {
		this.idProd = idProd;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		ProductosMenuPK that = (ProductosMenuPK) o;
		return Objects.equals(idMenu, that.idMenu) && Objects.equals(idProd, that.idProd);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idMenu, idProd);
	}

}
