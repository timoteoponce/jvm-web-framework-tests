package org.timo.depotseam.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;


import org.timo.depotseam.model.Cart;
import org.timo.depotseam.model.Product;
import org.timo.paginator.ListProvider;
import org.timo.paginator.Paginator;
import org.timo.paginator.Segment;
import org.timo.paginator.SegmentProvider;

@Named
@SessionScoped
public class CartController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6518622802913859943L;

	@Inject
	private EntityManager entityManager;

	private Paginator<Cart> paginator;

	@PostConstruct
	public void init() {
		paginator = new Paginator<Cart>(new ListProvider<Cart>() {
			@Override
			public List<Cart> provideList(SegmentProvider segmentProvider) {
				CriteriaBuilder builder = entityManager.getCriteriaBuilder();
				CriteriaQuery<Long> countQuery = builder
						.createQuery(Long.class);
				countQuery.select(builder.count(countQuery.from(Cart.class)));
				Segment segment = segmentProvider.getSegment(entityManager
						.createQuery(countQuery).getSingleResult().intValue());
				//
				CriteriaQuery<Cart> criteriaQuery = builder
						.createQuery(Cart.class);
				criteriaQuery.from(Cart.class);
				TypedQuery<Cart> query = entityManager
						.createQuery(criteriaQuery);
				query.setFirstResult(segment.getFromIndex());
				query.setMaxResults(segment.getToIndex());
				return query.getResultList();
			}
		});
		paginator.setPageSize(2);
	}

	public Paginator<Cart> getPaginator() {
		return paginator;
	}
	
	public void dismiss(Cart cart){
		Cart localCart = entityManager.merge(cart);
		localCart.setDismissed(true);
		entityManager.persist(localCart);
	}
}
