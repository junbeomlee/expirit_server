package com.tanx.expirit.history;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanx.expirit.exception.CustomJPAxcetption;
import com.tanx.expirit.exception.NotFoundIDException;
import com.tanx.expirit.exercise.Exercise;
import com.tanx.expirit.exercise.ExerciseRepository;
import com.tanx.expirit.user.User;
import com.tanx.expirit.user.UserRepository;
import com.tanx.expirit.util.DateUtil;

import rx.Observable;

@Service
public class HistoryServiceImpl implements HistoryService{
	
	@Autowired
	HistoryRepository historyRepository;
	
	@Autowired
	ExerciseRepository exerciseRepository;
	
	@Autowired
	UserRepository userRepository;

	
	//test
	public Observable<Boolean> countMoreThanFour(User user, Exercise exercise) {		
		return Observable
		.defer(()->Observable.just(historyRepository.countByUserAndExercise(user, exercise)))
		.map(count->count>0 ? true : false);
	}

	public Observable<List<History>> findByUserAndExercise(String userEmail, String string) {
		// TODO Auto-generated method stub
		return Observable
				.defer(()->Observable.just(historyRepository.findByUserEmailAndExerciseExNo(userEmail, string)));

	}

	@Override
	public Observable<List<History>> getAll(User user) {
		// TODO Auto-generated method stub
		return Observable
				.defer(()->Observable.just(historyRepository.findByUser(user)));
	}

	//test
	@Override
	public Observable<History> addHistory(String exNo, User user,String historyVal,String day,String cntVal){
		return Observable.defer(()->{
			Exercise exercise=exerciseRepository.findOne(exNo);
			if(exercise == null)
				throw new NotFoundIDException("invalid exNo");
			
			History history = new History();
			history.setUser(user);
			history.setHistoryVal(historyVal);
			history.setExercise(exercise);
			history.setExDate(DateUtil.getNow());
			history.setCntVal(cntVal);
			history= historyRepository.save(history);
			
			if(history==null)
				throw new CustomJPAxcetption("failed to save history");
			
			return Observable.just(history);
		});
	}

	@Override
	@Transactional
	public void deleteAll(User user) {
		// TODO Auto-generated method stub
		this.historyRepository.deleteByUser(user);
	}
}
