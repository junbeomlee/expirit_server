package com.tanx.expirit.exercise;

import java.util.List;

import com.tanx.expirit.exercise.exChange.ExChange;
import com.tanx.expirit.tip.Tip;

import rx.Observable;

public interface ExerciseService {

	Observable<List<Tip>> getTips(String exNo);

	Observable<List<ExChange>> getFaqs(String exNo);

}
