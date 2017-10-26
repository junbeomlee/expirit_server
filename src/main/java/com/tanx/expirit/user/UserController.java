package com.tanx.expirit.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tanx.expirit.routine.RoutineService;
import com.tanx.expirit.user.program.ProgramDTO;
import com.tanx.expirit.util.DTOMapper;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Single;

@Slf4j
@RestController
public class UserController{

	@Autowired
	UserService userService;
	
	@Autowired
	RoutineService routineService;

	@RequestMapping(value="/programs",method=RequestMethod.GET)
	public Single<List<ProgramDTO>> getUsersProgramList(User user){
		return Observable
				.defer(()->Observable.from(user.getPrograms()))
				.map(program -> DTOMapper.mapper.map(program, ProgramDTO.class))
				.toList().toSingle();
	}
	
	@RequestMapping(value="/programs/{programNo}",method=RequestMethod.DELETE)
	public Observable<Void> deleteProgram(User user,
												   @PathVariable("programNo") int programNo){

		return userService.deleteProgram(user,programNo);
	}
	
	@RequestMapping(value="/programs/{programNo}",method=RequestMethod.PUT)
	public Single<ProgramDTO> updateProgram(User user,
												@RequestBody ProgramDTO programDTO,
												@PathVariable("programNo") String programNo){
		return userService.updateProgram(programDTO.getProgramSeq(),programDTO.getExNo(),programDTO.getDay(),user)
				.map(program->DTOMapper.mapper.map(program, ProgramDTO.class))
				.toSingle();
	}
	
	@RequestMapping(value="/programs",method=RequestMethod.POST)
	public Single<ProgramDTO> addProgram(User user,
												@RequestBody ProgramDTO programDTO){
		return userService.addProgram(user,programDTO.getExNo(),programDTO.getDay(),programDTO.getExWarmupSet(),programDTO.getExSet())
				.map(program->DTOMapper.mapper.map(program, ProgramDTO.class))
				.toSingle();
	}
	
	@RequestMapping(value="/users",method=RequestMethod.PUT)
	public Observable<User> updateUser(User user,
			@ModelAttribute("user") User requestUser){
		return userService.updateUser(user,requestUser);
	}
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value="/users/setUp",method=RequestMethod.POST)
	public Single<UserDTO> setUp(User user,
			@RequestBody SetUpDTO setUpDTO){
		
		return userService
				.updateUser(user, setUpDTO.user)
				.concatMap(updatedUser->{
					return routineService.getProgramListByDays(setUpDTO.days)
					.concatMap(programList->{
						return userService.fillWarmUpAndCleaUpExercise(programList)
								.map(fillUpProgramList->{
									updatedUser.setPrograms(fillUpProgramList);
									updatedUser.setQuestYN("Y");
									userRepository.save(updatedUser);
									updatedUser.sortProgramByExOrder();
									return updatedUser;
								});
					});
				})
				.map(setUser->DTOMapper.mapUserToUserDTO(setUser,UserDTO.class))
				.toSingle();
	}
	
	@RequestMapping(value="/password", method=RequestMethod.PUT)
	public Single<Password.PasswordResult> updatePassword(User user,@RequestBody Password.PasswordDTO passwordDTO){
		return userService.updatePassword(user,passwordDTO.getCurrentPw(),passwordDTO.getChangePw())
				.toSingle();
	}
	
	@RequestMapping(value="/records",method=RequestMethod.DELETE)
	public void deleteAllRecords(User user){
		userService.clearRecord(user);
	}
	
	@RequestMapping(value="/records/{recordNum}",method=RequestMethod.DELETE)
	public Observable<Void> deleteAllRecords(User user, @PathVariable("recordNum") int recordNum){
		return userService.deleteRecord(user,recordNum);
	}
	
	@RequestMapping(value="/users",method=RequestMethod.DELETE)
	public void deleteUser(User user){
		userService.deleteUser(user);
	}
}
