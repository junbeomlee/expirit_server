package com.tanx.expirit.routine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import rx.Single;

@Slf4j
@RestController
public class RoutineController{

	@Autowired
	RoutineService routineService;

	@RequestMapping(value = "/weekRoutines", method = RequestMethod.POST)
	public Single<WeekRoutine> addWeekRoutin(@RequestBody WeekRoutine weekRoutine){
		return this.routineService
				.addWeekRoutine(weekRoutine)
				.toSingle();
	}
	
	@RequestMapping(value = "/weekRoutines/{weekId}", method = RequestMethod.POST)
	public Single<WeekRoutine> addDays(@RequestBody Days days,
										  @PathVariable("weekId") String id){
		return this.routineService
				.addDays(days.getDays(),id)
				.toSingle();
	}
	
	@RequestMapping(value = "/weekRoutines", method = RequestMethod.DELETE)
	public void deleteAllRoutines(){
		this.routineService
				.deleteAll();
	}
	
	@RequestMapping(value= "/weekRoutines", method =RequestMethod.GET)
	public Single<List<WeekRoutine>> findWeekRoutinesByDays(@RequestParam(value="days",required=false) String[] days){
		if(days==null || days.length==0)
			return this.routineService.findAll().toSingle();
		else
			return this.routineService
					.getWeekRoutineByDays(days)
					.toSingle();
	}
	
//	@RequestMapping(value = "/weekRoutine", method = RequestMethod.GET)
//	public void routine(HttpServletRequest request, HttpServletResponse response) {
//		WeekRoutine weekRoutine = new WeekRoutine();
//		weekRoutine.setNumOfDays(3);
//		weekRoutine.setRoutineName("jun's week routine");
//		weekRoutine.setDescription("jun made it");
//		
//		String[] days = new String[3];
//		days[0] = Day.MON.toString();
//		days[1] = Day.WED.toString();
//		days[2] = Day.FRI.toString();
//		weekRoutine.getDaysList().add(days);
//		
//		
//		
//		
//		RoutineEx mondayEx = new RoutineEx();
//		mondayEx.setExNo("27");
//		mondayEx.setExSet(4);
//		mondayEx.setWarmUpSet(1);
//		RoutineEx wendayEx = new RoutineEx();
//		
//		wendayEx.setExNo("30");
//		wendayEx.setExSet(4);
//		wendayEx.setWarmUpSet(0);
//		
//		Routine routine = new Routine();
//		routine.setBodyPart("100011");
//		routine.getRoutineExList().add(mondayEx);
//		
//		Routine routine2 = new Routine();
//		routine2.setBodyPart("100012");
//		routine2.getRoutineExList().add(wendayEx);
//		
//		weekRoutine.getRoutineList().add(routine);
//		weekRoutine.getRoutineList().add(routine2);
//		routineRepository.save(weekRoutine);
//		////System.out.println(routineRepository.count());
//	}
}
