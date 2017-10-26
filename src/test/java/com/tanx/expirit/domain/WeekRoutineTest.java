package com.tanx.expirit.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tanx.expirit.routine.WeekRoutine;

import rx.Observable;

public class WeekRoutineTest {

	WeekRoutine weekRoutine;
	
	@Before
	public void setUp() {
		weekRoutine = new WeekRoutine();
	}

	@After
	public void tearDown() {

	}

	@Test
	public void hasDaysTest(){
		//when
		weekRoutine.getDaysList().add(new String[]{"MON","WED","FRI"});
		weekRoutine.getDaysList().add(new String[]{"MON","WED","SAT"});
		weekRoutine.getDaysList().add(new String[]{"SAT","MON","SUN"});
		//then
		String[] testdays= new String[]{"MON","WED","FRI"};
		String[] testdays2= new String[]{"MON","SAT","SUN"};
		String[] testdays3= new String[]{"SAT","MON","SUN"};
		
		Arrays.sort(testdays2);
		Arrays.sort(testdays3);
		System.out.println(Arrays.toString(testdays2));
		System.out.println(Arrays.toString(testdays3));
		System.out.println(Arrays.toString(testdays2).equals(Arrays.toString(testdays3)));
		//
		assertTrue(weekRoutine.hasDays(testdays));
		assertFalse(weekRoutine.hasDays(testdays2));
	}
	
	@Test
	public void justNullTest(){
		Observable
		.from(new ArrayList<String>())
		.take(1)
		.subscribe();
	}
	
	@Test
	public void hasAllTest(){
		weekRoutine.getDaysList().add(new String[]{"ALL"});
		assertTrue(weekRoutine.hasAll());
	}
}
