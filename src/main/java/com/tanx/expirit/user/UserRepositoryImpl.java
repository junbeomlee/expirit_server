package com.tanx.expirit.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements CustomUserRepository{

	@PersistenceContext
	private EntityManager em;
	 
	@Override
	public void detach(User user) {
		// TODO Auto-generated method stub
		em.detach(user);
	}

	@Override
	public User getFromSession(String email) {
		// TODO Auto-generated method stub
		return em.getReference(User.class, email);
	}
	
	@Override
	public void putSession(User user){
		
		Session session = em.unwrap(Session.class);
		session.update(user);
	}
	
	@Override
	public void flush(){
		em.flush();
	}

}
