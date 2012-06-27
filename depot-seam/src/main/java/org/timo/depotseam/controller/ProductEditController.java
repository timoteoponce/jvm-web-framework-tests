package org.timo.depotseam.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.timo.depotseam.model.Product;

@Named
@Stateful
@ConversationScoped
public class ProductEditController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6025560851859209245L;

	private final List<String> imageFiles = Arrays.asList(
			"/resources/images/books/debug.jpg",
			"/resources/images/books/rails.png",
			"/resources/images/books/rtp.jpg",
			"/resources/images/books/ruby.jpg",
			"/resources/images/books/wd4d.jpg");

	@Inject
	private EntityManager entityManager;
	@Inject
	private Conversation conversation;

	private Product product = new Product();

	private String image;

	public Product getProduct() {
		return product;
	}

	public void begin() {
		if (conversation.isTransient()) {			
			conversation.begin();
		}		
	}

	public String save() {
		product.setImageUrl(image);		
		entityManager.persist(product);
		conversation.end();		
		return "SAVED";
	}

	public String cancel() {
		product = null;
		conversation.end();		
		return "CANCELLED";
	}

	public List<String> getImageFiles() {
		return imageFiles;
	}

	public void withImage(String image) {
		this.image = image;
		this.product.setImageUrl(image);
	}

	public String getImage() {
		return image;
	}

}
