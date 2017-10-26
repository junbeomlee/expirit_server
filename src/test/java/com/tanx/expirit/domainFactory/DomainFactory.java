package com.tanx.expirit.domainFactory;

import com.tanx.expirit.exercise.Exercise;
import com.tanx.expirit.user.User;
import com.tanx.expirit.user.program.Program;

public class DomainFactory {
	
	public static User createUser(String email) {
		User user =new User(email);
		user.setUserName("asd");
		return user;
	}
	
	public static Program createProgram(int programSeq,Exercise exercise,String day){
		Program program = new Program();
		program.setDay(day);
		program.setProgramSeq(programSeq);
		program.setExercise(exercise);
		return program;
	}
	
	public static Exercise createExercise(String exNo){
		Exercise exercise = new Exercise();
		exercise.setExNo(exNo);
		
		return exercise;
	}
}
