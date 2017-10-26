package com.tanx.expirit.routine;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanx.expirit.exception.CustomRuntimeException;
import com.tanx.expirit.exercise.Exercise;
import com.tanx.expirit.exercise.ExerciseRepository;
import com.tanx.expirit.user.program.Program;
import com.tanx.expirit.util.DTOMapper;

import rx.Observable;

@Service
public class RoutineServiceImpl implements RoutineService {

	@Autowired
	RoutineRepository routineRepository;

	@Autowired
	ExerciseRepository exerciseRepository;

	@Override
	public Observable<WeekRoutine> addWeekRoutine(WeekRoutine weekRoutine) {
		// TODO Auto-generated method stub
		return Observable.defer(() -> {
			WeekRoutine addedWeekRoutine = routineRepository.save(weekRoutine);
			return Observable.just(addedWeekRoutine);
		});
	}

	@Override
	public Observable<List<WeekRoutine>> getWeekRoutineByDays(String[] days) {
		// TODO Auto-generated method stub
		return Observable.defer(() -> {
			List<WeekRoutine> weekRoutineList = routineRepository.findByNumOfDays(days.length).stream()
					.filter(weekRoutine -> weekRoutine.hasDays(days) || weekRoutine.hasAll()).collect(Collectors.toList());

			return Observable.just(weekRoutineList);
		});
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		routineRepository.deleteAll();
	}

	@Override
	public Observable<List<WeekRoutine>> findAll() {
		// TODO Auto-generated method stub
		return Observable.defer(() -> {
			return Observable.just(this.routineRepository.findAll());
		});
	}

	@Override
	public Observable<List<Program>> getProgramListByDays(String[] days) {

		return getWeekRoutineByDays(days).map(weekroutinelist -> {
			System.out.println("getProgram");
			List<Program> programList = new ArrayList<Program>();
			if (weekroutinelist.size() == 0)
				return programList;
			else {
				weekroutinelist.get(0) // 0 -> default
						.getRoutineList().forEach(new Consumer<List<RoutineEx>>() {
							int indexDays = 0;
							@Override
							public void accept(List<RoutineEx> routineExList) {
								List<String> exNoList = routineExList.stream().map(routineEx -> routineEx.getExNo())
										.collect(Collectors.toList());
								List<Exercise> exlist = exerciseRepository.findAll(exNoList);

								routineExList.forEach(routineEx -> {
									//exNo가 일치안하는 경우 있음
									if(exlist.size()>0){
										Program program = new Program();
										program.setExercise(exlist.remove(0));
										program.setExSet(routineEx.getExSet());
										program.setExWarmUpSet(routineEx.getWarmUpSet());
										program.setDay(days[indexDays]);
										programList.add(program);
									}
								});
								indexDays++;
							}
					});
			}
			return programList;
		});
	}

	@Override
	public Observable<WeekRoutine> addDays(String[] days, String id) {
		// TODO Auto-generated method stub
		return Observable.defer(()->{
			//좀더 엄밀히 검사해야함
			WeekRoutine weekRoutine=routineRepository.findOne(new ObjectId(id));
			weekRoutine.getDaysList().add(days);
			weekRoutine=routineRepository.save(weekRoutine);
			return Observable.just(weekRoutine);
		});
	}
}
