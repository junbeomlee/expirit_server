package com.tanx.expirit.exercise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanx.expirit.exercise.exChange.ExChange;
import com.tanx.expirit.tip.Tip;

import rx.Observable;

@Service
public class ExerciseServiceImpl implements ExerciseService{

	@Autowired
	ExerciseRepository exerciseRepository;
	
	@Override
	public Observable<List<Tip>> getTips(String exNo) {
		// TODO Auto-generated method stub
		return Observable.defer(()->{
			Exercise ex=exerciseRepository.findOne(exNo);
			return Observable.just(ex.getTips());
		});
	}

	@Override
	public Observable<List<ExChange>> getFaqs(String exNo) {
		// TODO Auto-generated method stub
		return Observable.defer(()->{
			Exercise ex=exerciseRepository.findOne(exNo);
			return Observable.just(ex.getExChanges());
		});
	}

}
