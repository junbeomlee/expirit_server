package com.tanx.expirit.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tanx.expirit.domainFactory.DomainFactory;
import com.tanx.expirit.exercise.Exercise;
import com.tanx.expirit.user.program.Program;
import com.tanx.expirit.user.program.ProgramDTO;

public class DozerTest {

	@Before
	public void setUp(){
		
	}
	
	@Test
	public void dozerDTOtoDomainObjectTest(){
		
		//when
		Exercise exercise = DomainFactory.createExercise("11");
		Program program = DomainFactory.createProgram(1, exercise, "MON");
		ProgramDTO programDTO=DTOMapper.mapper.map(program, ProgramDTO.class);
		
		//then
		Program program2 = DTOMapper.mapper.map(programDTO,Program.class);
		
		assertEquals(program.toString(),program2.toString());
		
	}
}

