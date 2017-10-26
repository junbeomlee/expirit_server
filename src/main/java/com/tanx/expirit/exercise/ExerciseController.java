package com.tanx.expirit.exercise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tanx.expirit.RestControllerTemplate;
import com.tanx.expirit.exercise.exChange.ExChange;
import com.tanx.expirit.tip.Tip;

import lombok.extern.slf4j.Slf4j;
import rx.Single;

@Slf4j
@RestController
@RequestMapping("/exercises")
public class ExerciseController extends RestControllerTemplate<Exercise,ExerciseDTO, String>{

	@Autowired
	ExerciseService exerciseService;
	
	@Autowired
	public ExerciseController(ExerciseRepository exerciseRepository) {
		super(exerciseRepository,Exercise.class,ExerciseDTO.class);
	}
	
	@RequestMapping(value="/{exNo}/tips" , method=RequestMethod.GET)
	public Single<List<Tip>> getTips(@PathVariable("exNo") String exNo){
		return exerciseService
				.getTips(exNo)
				.toSingle();
	}
	
	@RequestMapping(value="/{exNo}/faqs" , method=RequestMethod.GET)
	public Single<List<ExChange>> getFaqs(@PathVariable("exNo") String exNo){
		return exerciseService
				.getFaqs(exNo)
				.toSingle();
	}
}
