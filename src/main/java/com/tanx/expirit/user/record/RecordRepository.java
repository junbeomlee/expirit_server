package com.tanx.expirit.user.record;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Integer>{

	//Record findByUserAndExercise(User user, Exercise exercise);

	Record findByEmailAndExerciseExNo(String email, String exNo);

}
