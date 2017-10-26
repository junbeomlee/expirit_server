package com.tanx.expirit.history;

import java.util.List;

import com.tanx.expirit.exercise.Exercise;
import com.tanx.expirit.user.User;

import rx.Observable;

public interface HistoryService {
	public Observable<Boolean> countMoreThanFour(User user, Exercise exercise);
	Observable<List<History>> findByUserAndExercise(String userEmail, String exNo);
	public Observable<List<History>> getAll(User user);
	public Observable<History> addHistory(String exNo, User user, String historyVal,String day, String string);
	public void deleteAll(User user);
}
