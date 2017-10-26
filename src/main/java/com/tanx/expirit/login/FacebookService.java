package com.tanx.expirit.login;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;

import rx.Observable;

@Service
public class FacebookService {

//	@Autowired
//	Facebook facebook;
	
	@PostConstruct
	private void init() {
	    try {
	        String[] fieldsToMap = { "id", "about", "age_range", "birthday",
	                "context", "cover", "currency", "devices", "education",
	                "email", "favorite_athletes", "favorite_teams",
	                "first_name", "gender", "hometown", "inspirational_people",
	                "installed", "install_type", "is_verified", "languages",
	                "last_name", "link", "locale", "location", "meeting_for",
	                "middle_name", "name", "name_format", "political",
	                "quotes", "payment_pricepoints", "relationship_status",
	                "religion", "security_settings", "significant_other",
	                "sports", "test_group", "timezone", "third_party_id",
	                "updated_time", "verified", "viewer_can_send_gift",
	                "website", "work" };

	        Field field = Class.forName(
	                "org.springframework.social.facebook.api.UserOperations")
	                .getDeclaredField("PROFILE_FIELDS");
	        field.setAccessible(true);

	        Field modifiers = field.getClass().getDeclaredField("modifiers");
	        modifiers.setAccessible(true);
	        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
	        field.set(null, fieldsToMap);

	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	
	public Observable<User> getEmail(String accessToken){
		return Observable.defer(()->{
			Facebook facebook = new FacebookTemplate(accessToken);
			return Observable.just(facebook.userOperations().getUserProfile());
		});
	}
}
