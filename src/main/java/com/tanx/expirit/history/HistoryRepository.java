package com.tanx.expirit.history;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.tanx.expirit.exercise.Exercise;
import com.tanx.expirit.user.User;


public interface HistoryRepository extends PagingAndSortingRepository<History,Integer>{

	int countByUser(User user);

	int countByUserAndExercise(User user, Exercise exercise);

	List<History> findByUserAndExercise(User user, Exercise exercise);

	List<History> findExerciseDistinctByUser(User user);

	List<History> findByUserEmailAndExerciseExNo(String email, String string);

	List<History> findByUser(User user);

	@Transactional
	void deleteByUser(User user);
}
