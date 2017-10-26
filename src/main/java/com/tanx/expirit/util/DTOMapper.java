package com.tanx.expirit.util;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

import com.tanx.expirit.exercise.Exercise;
import com.tanx.expirit.history.History;
import com.tanx.expirit.history.HistoryDTO;
import com.tanx.expirit.routine.RoutineEx;
import com.tanx.expirit.user.User;
import com.tanx.expirit.user.UserDTO;
import com.tanx.expirit.user.UserDeleteDTO;
import com.tanx.expirit.user.program.Program;
import com.tanx.expirit.user.program.ProgramDTO;
import com.tanx.expirit.user.record.RecordDTO;

public class DTOMapper {
	public static DozerBeanMapper mapper = new DozerBeanMapper();
//	static {
//		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
//		    @Override
//		    protected void configure() {
//
//		        String dateFormat = "yyyy-MM-dd";
//		        mapping(Program.class, ProgramDTO.class,
//		                TypeMappingOptions.dateFormat(dateFormat))
//		                .fields("exCompleteDate", "exCompleteDate");
//		    };
//		};
//		mapper.addMapping(mappingBuilder);
//	}
//
	public static <T, S> List<T> mapListObjectToListNewObject(List<S> objects, Class<T> newObjectClass) {
		final List<T> newObjects = new ArrayList<T>();
		for (S s : objects) {
			newObjects.add(mapper.map(s, newObjectClass));
		}
		return newObjects;
	}
//
	public static UserDTO mapUserToUserDTO(User user, Class<UserDTO> userDTOType) {
		UserDTO userDTO = mapper.map(user, userDTOType);
		List<ProgramDTO> programDTOList = mapListObjectToListNewObject(user.getPrograms(), ProgramDTO.class);
		List<RecordDTO> recordDTOList = mapListObjectToListNewObject(user.getRecords(), RecordDTO.class);
		userDTO.setPrograms(programDTOList);
		userDTO.setRecords(recordDTOList);
		return userDTO;
	}
	
	public static UserDeleteDTO mapUserToUserDeleteDTO(User user,List<History> historyList, Class<UserDeleteDTO> userDTOType) {
		UserDeleteDTO userDeleteDTO = mapper.map(user, userDTOType);
		List<ProgramDTO> programDTOList = mapListObjectToListNewObject(user.getPrograms(), ProgramDTO.class);
		List<RecordDTO> recordDTOList = mapListObjectToListNewObject(user.getRecords(), RecordDTO.class);
		List<HistoryDTO> historyDTOList = mapListObjectToListNewObject(historyList,HistoryDTO.class);
		userDeleteDTO.setPrograms(programDTOList);
		userDeleteDTO.setRecords(recordDTOList);
		userDeleteDTO.setHistorys(historyDTOList);
		return userDeleteDTO;
	}
	
	public static Program mapExRoutineToProgram(RoutineEx routineEx) {
		// TODO Auto-generated method stub
		Program program = new Program();
		program.setExercise(new Exercise(routineEx.getExNo()));
		program.setExSet(routineEx.getExSet());
		program.setExWarmUpSet(routineEx.getWarmUpSet());
		return program;
	}

}
