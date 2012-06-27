package org.timo.depotseam.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.timo.depotseam.model.Cart;
import org.timo.depotseam.model.LineItem;
import org.timo.depotseam.model.Product;

@Named
@SessionScoped
public class CurrentCart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3240588048959413453L;

	@Inject
	private EntityManager entityManager;

	private Cart cart = new Cart();

	private Integer quantity = 1;

	private Product product;

	public void addItem() {
		save();
		LineItem item = new LineItem();
		item.setCart(cart);
		item.setProduct(product);
		item.setCreationDate(new Date());
		item.setQuantity(quantity);
		cart.getLineItems().add(item);
		entityManager.persist(item);
		quantity = 1;
	}

	public void save() {
		if (cart.getId() > 0) {
			cart = entityManager.merge(cart);
		}else{
			entityManager.persist(cart);
		}
	}

	public void newCart() {
		if (cart.getId() > 0) {
			for (LineItem item : cart.getLineItems()) {
				entityManager.remove(item);
			}
			cart.getLineItems().clear();
		}
		cart.setCreationDate(new Date());
	}

	public List<LineItem> getLineItems() {
		return new ArrayList<LineItem>(cart.getLineItems());
	}

	public BigDecimal totalPrice() {
		BigDecimal totalPrice = new BigDecimal(0);
		for (LineItem item : cart.getLineItems()) {
			BigDecimal itemPrice = BigDecimal.valueOf(item.getQuantity())
					.multiply(item.getProduct().getPrice());
			totalPrice.add(itemPrice);
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
		System.out.println("SEt product : "+product.getId());
	}

}
