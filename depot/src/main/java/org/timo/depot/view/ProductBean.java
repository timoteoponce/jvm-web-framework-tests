package org.timo.depot.view;

import java.util.List;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.timo.depot.domain.Product;
import org.jboss.seam.forge.persistence.PersistenceUtil;
import org.jboss.seam.transaction.Transactional;

@Transactional
@Named
@Stateful
@RequestScoped
public class ProductBean extends PersistenceUtil {
	private List<Product> list = null;
	private Product product = new Product();
	private long id = 0;

	public void load() {
		product = findById(Product.class, id);
	}

	public String create() {
		create(product);
		return "view?faces-redirect=true&id=" + product.getId();
	}

	public String delete() {
		delete(product);
		return "list?faces-redirect=true";
	}

	public String save() {
		save(product);
		return "view?faces-redirect=true&id=" + product.getId();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		load();
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getList() {
		if (list == null) {
			list = findAll(Product.class);
		}
		return list;
	}

	public void setList(List<Product> list) {
		this.list = list;
	}
}
