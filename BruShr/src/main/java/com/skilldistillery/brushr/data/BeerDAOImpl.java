package com.skilldistillery.brushr.data;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.skilldistillery.brushr.entities.BeerRecipe;
import com.skilldistillery.brushr.entities.Comment;
import com.skilldistillery.brushr.entities.User;

@Service
@Transactional
public class BeerDAOImpl implements BeerDAO{
	
	

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<BeerRecipe> getAllBeers(){
		String jpql = " SELECT b FROM BeerRecipe b WHERE b.enabled = true";
		
		List<BeerRecipe> listOfBeers = em.createQuery(jpql, BeerRecipe.class).getResultList();
	
		return listOfBeers;
	}

	@Override
	public BeerRecipe getBeerById(int id) {
		BeerRecipe b =  em.find(BeerRecipe.class, id);
		System.out.println(id + "" + b);
		return b.isEnabled() ? b : null;
		
	}

	@Override
	public List<BeerRecipe> getBeersByStyle(String style) {
		String jpql = "SELECT b from BeerRecipe WHERE b.type LIKE :style AND b.enabled = true";
		List<BeerRecipe> recipes = em.createQuery(jpql, BeerRecipe.class)
				.setParameter("style", "%"+ style+ "%").getResultList();
		return recipes;
	}

	@Override
	public List<BeerRecipe> getBeersByNameOrDescription(String search) {
		String jpql = "SELECT b from BeerRecipe b WHERE b.beerName LIKE :beerName OR b.beerType LIKE :beerType AND b.enabled = true";
		List<BeerRecipe> recipes = em.createQuery(jpql, BeerRecipe.class)
				.setParameter("beerName", "%"+ search+ "%").setParameter("beerType", "%" + search + "%").getResultList();
		return recipes;
	}

	@Override
	public boolean deleteBeer(int id, User user) {
		boolean isDeleted = false;
		User u = em.find(User.class, user.getId());
		BeerRecipe b = em.find(BeerRecipe.class, id);
		
		if (u != null && b != null) {
			b.setEnabled(false);
			isDeleted = true;
		}
		
		em.flush();
		em.close();

		return isDeleted;	
	}

	@Override
	public BeerRecipe updateBeer(int id, BeerRecipe beer) {
		BeerRecipe b = em.find(BeerRecipe.class, id);
		
		b.setId(id);
		b.setBeerName(beer.getBeerName());
		b.setBeerType(beer.getBeerType());
		b.setYeast(beer.getYeast());
		b.setDescription(beer.getDescription());
		b.setEnabled(true);
		b.setCreatedAt(beer.getCreatedAt());
		b.setUpdatedAt(beer.getUpdatedAt());
		b.setImgUrl(beer.getImgUrl());
		em.flush();
		em.close();
		return b;
	}

	@Override
	public BeerRecipe createBeer(BeerRecipe beer, User user) {
		beer.addUser(user);	
		user.addBeer(beer); //  updated while MD    
    beer.setEnabled(true);
		em.persist(beer);
		em.flush();
		em.close();
		return beer;
	}
	
	@Override
	public Comment createComment(int id, Comment comment, User user) {
		BeerRecipe beer = em.find(BeerRecipe.class, id);
		User persistUser = em.find(User.class, user.getId());
		persistUser.getComments().get(0);
		
		LocalDateTime time = LocalDateTime.now();
		
		
		
		beer.addComment(comment);
		comment.setCreatedAt(time);
		comment.addBeerRecipe(beer);
		comment.setUser(persistUser);
		persistUser.addComment(comment);
		
		em.persist(comment);
		em.flush();
	
		return comment;
	}
	
	//@override Comment 

}
