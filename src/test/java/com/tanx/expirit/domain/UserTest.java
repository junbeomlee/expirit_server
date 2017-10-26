package com.tanx.expirit.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tanx.expirit.exercise.Exercise;
import com.tanx.expirit.user.User;
import com.tanx.expirit.user.program.Program;

public class UserTest {

	User user;

	@Before
	public void setUp() {
		user = createUser("leebduk@gmail.com");
	}

	@After
	public void tearDown() {

	}

	@Test
	public void userAddProgramTest() {

		// When
		Program program = createProgram(1,null,"MON");

		// then
		user.addProgram(program);

		//
		assertEquals(1, user.getPrograms().size());
		assertEquals(program.getProgramSeq(), user.getPrograms().get(0).getProgramSeq());
	}

	@Test
	public void userDeleteProgramTest() {
		// when
		Program program = createProgram(1,null,"MON");
		user.addProgram(program);
		assertEquals(1, user.getPrograms().size());
		assertEquals(program.getProgramSeq(), user.getPrograms().get(0).getProgramSeq());

		// then
		user.deleteProgram(1);

		assertEquals(0, user.getPrograms().size());
	}

	@Test
	public void userFindProgramTest() {
		// when
		Program program = createProgram(1,null,"MON");
		Program program2 = createProgram(2,null,"TUE");
		user.addProgram(program);
		user.addProgram(program2);

		assertEquals(2, user.getPrograms().size());

		// then
		Program findedProgram = user.findProgram(2);
		
		//
		assertEquals("TUE", findedProgram.getDay());
	}

	@Test
	public void userFindProgramWhenNoSuchItemTest() {
		// when
		Program program = createProgram(1,null,"MON");
		Program program2 = createProgram(2,null,"MON");
		
		user.addProgram(program);
		user.addProgram(program2);

		assertEquals(2, user.getPrograms().size());

		// then
		Program findedProgram = user.findProgram(3);

		//
		assertNull(findedProgram);
	}

	@Test
	public void userFindProgramByExerciseAndDayTest() {
		// when
		Program program = createProgram(1,createExercise("100"),"MON");
		Program program2 = createProgram(2,createExercise("200"),"MON");

		user.addProgram(program);
		user.addProgram(program2);
		assertEquals(2, user.getPrograms().size());
		
		// then
		Program findedProgram=user.findProgramByDayAndExNo("MON", "100");
		
		//
		assertEquals(findedProgram.toString(),program.toString());
	}
	
	@Test
	public void sortProgramByExOrder(){
		user.sortProgramByExOrder();
		assertNotNull(user.getPrograms());
	}

	@Test
	public void updateTest(){
		User user=new User();
		user.setEmail("leebduk@gmail.com");
		user.setExPrefer("asd");
		
		
		
		User user2= new User();
		user2.setExPrefer("zxc");
		user2.setUserGender("man");
		user.update(user2);
		assertEquals(user.getExPrefer(),"zxc");
		assertEquals(user.getUserGender(),user2.getUserGender());
	}
	public User createUser(String email) {
		return new User(email);
	}
	
	public Program createProgram(int programSeq,Exercise exercise,String day){
		Program program = new Program();
		program.setDay(day);
		program.setProgramSeq(programSeq);
		program.setExercise(exercise);
		return program;
	}
	
	public Exercise createExercise(String exNo){
		Exercise exercise = new Exercise();
		exercise.setExNo(exNo);
		
		return exercise;
	}

}
