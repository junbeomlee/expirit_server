package com.tanx.expirit.user;

import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserRepository{
	public void detach(User user);
	public User getFromSession(String email);
	public void putSession(User user);
	public void flush();
}
