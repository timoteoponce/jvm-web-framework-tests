package org.timo.depotseam.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class LineItem implements java.io.Serializable {
	@Id
	private @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	long id = 0;
	@Version
	private @Column(name = "version")
	int version = 0;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@ManyToOne
	private Product product = new Product();

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(final Product product) {
		this.product = product;
	}

	@ManyToOne
	private Cart cart = new Cart();

	public Cart getCart() {
		return this.cart;
	}

	public void setCart(final Cart cart) {
		this.cart = cart;
	}

	@Column
	private Date creationDate;

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column
	private int quantity;

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final int quantity) {
		this.quantity = quantity;
	}
}
