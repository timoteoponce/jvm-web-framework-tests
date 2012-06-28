package org.timo.depotseam.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.security.Credentials;
import org.timo.depotseam.model.Cart;
import org.timo.depotseam.model.LineItem;
import org.timo.depotseam.model.Product;

@Named
@Stateful
@SessionScoped
public class CurrentCart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3240588048959413453L;

	@Inject
	private EntityManager entityManager;
	
	@Inject
	private Credentials credentials;

	private Cart cart = new Cart();

	private Integer quantity = 1;

	private Product product = new Product();

	private boolean itemSelected;

	public void addItem() {
		save();
		LineItem item = new LineItem();
		item.setCart(cart);
		item.setProduct(product);
		item.setCreationDate(new Date());
		item.setQuantity(quantity);
		cart.getLineItems().add(item);
		entityManager.persist(item);
		clean();
	}

	private void clean() {
		product = new Product();
		quantity = 1;
		itemSelected = false;
	}

	public void cancel() {
		clean();
	}

	public void save() {
		if (cart.getId() > 0) {
			cart = entityManager.merge(cart);
		} else {
			cart.setUsername(credentials.getUsername());
			entityManager.persist(cart);
		}
	}

	public void newCart() {
		if (cart.getId() > 0) {
			cart = entityManager.merge(cart);
			for (LineItem item : cart.getLineItems()) {
				entityManager.remove(entityManager.merge(item));
			}
			cart.getLineItems().clear();
			cart = new Cart();
			entityManager.persist(cart);
		}
		cart.setCreationDate(new Date());
	}

	public List<LineItem> getLineItems() {
		return new ArrayList<LineItem>(cart.getLineItems());
	}

	public BigDecimal totalPrice() {		
		return totalPrice(cart);
	}
	
	public BigDecimal totalPrice(Cart cart) {
		Cart localCart = entityManager.merge(cart);
		BigDecimal totalPrice = new BigDecimal(0);
		for (LineItem item : localCart.getLineItems()) {
			BigDecimal itemPrice = BigDecimal.valueOf(item.getQuantity())
					.multiply(item.getProduct().getPrice());
			totalPrice = totalPrice.add(itemPrice);			
		}
		return totalPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setProduct(Product product) {
		this.product = entityManager.merge(product);
		itemSelected = true;
	}

	public Product getProduct() {
		return product;
	}

	public boolean isItemSelected() {
		return itemSelected;
	}
}
