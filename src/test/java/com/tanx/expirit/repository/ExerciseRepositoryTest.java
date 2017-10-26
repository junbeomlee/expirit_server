package com.tanx.expirit.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tanx.expirit.Expirit2Application;
import com.tanx.expirit.exercise.Exercise;
import com.tanx.expirit.exercise.ExerciseRepository;
import com.tanx.expirit.user.program.Program;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Expirit2Application.class)
@WebAppConfiguration
@Transactional
public class ExerciseRepositoryTest {
	
	@Autowired
	ExerciseRepository exerciseRepository;
	
	@Test
	public void getVideoTest(){
		Program p1= new Program();
		Program p2= new Program();
		Map<String,Program> exerciseTypeMap = new HashMap<String,Program>();
		
		exerciseTypeMap.put("006002", p1);
		exerciseTypeMap.put("006003", p2);

		List<Exercise> exList= exerciseRepository.findAll();
		System.out.println(exList);
		List<Exercise> warmUpList=exList
		.stream()
		.filter(ex->ex.getExDepth1().equals("005010")&&exerciseTypeMap.containsKey(ex.getExDepth2()))
		.collect(Collectors.toList());
		
		System.out.println(warmUpList.size());
	}
}
