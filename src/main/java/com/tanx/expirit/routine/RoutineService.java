package com.tanx.expirit.routine;

import java.util.List;

import com.tanx.expirit.user.program.Program;

import rx.Observable;

public interface RoutineService {
	Observable<WeekRoutine> addWeekRoutine(WeekRoutine weekRoutine);
	Observable<List<WeekRoutine>> getWeekRoutineByDays(String[] days);
	void deleteAll();
	Observable<List<WeekRoutine>> findAll();
	Observable<List<Program>> getProgramListByDays(String[] days);
	Observable<WeekRoutine> addDays(String[] days,String id);
}
