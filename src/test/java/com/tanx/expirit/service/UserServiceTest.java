package com.tanx.expirit.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tanx.expirit.Expirit2Application;
import com.tanx.expirit.exercise.ExerciseRepository;
import com.tanx.expirit.user.UserService;
import com.tanx.expirit.user.program.Program;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Expirit2Application.class)
@WebAppConfiguration
@Transactional
public class UserServiceTest {

	@Autowired
	UserService userService;
	
	@Autowired
	ExerciseRepository exerciseRepository;
	
	@Test
	public void getVideoTest(){
		Program p1= new Program();
		p1.setDay("MON");
		p1.setEmail("leebduk@gmail.com");
		p1.setExercise(exerciseRepository.findOne("30"));
		
//		p2.setDay("MON");
//		p1.setEmail("leebduk@gmail.com");
//		p1.setExercise(exerciseRepository.findOne("58"));
		Program p2= new Program();
		p2.setDay("WED");
		p2.setEmail("leebduk@gmail.com");
		p2.setExercise(exerciseRepository.findOne("57"));

		List<Program> programList = new ArrayList<Program>();
		programList.add(p1);
		programList.add(p2);
		programList=userService.fillWarmUpAndCleaUpExercise(programList).toBlocking().single();
		
		programList.forEach(pr->{
			System.out.println(pr.getExercise().getExNo());
		});
		System.out.println(programList);
		System.out.println(programList.size());
	}
}
