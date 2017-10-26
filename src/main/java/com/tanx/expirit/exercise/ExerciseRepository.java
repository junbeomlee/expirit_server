package com.tanx.expirit.exercise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, String>{

	List<Exercise> findByExDepth1AndExDepth2AndExDepth3(String depth1, String depth2, String depth3);
	List<Exercise> findByExDepth1AndExDepth2(String depth1,String depth2);
	List<Exercise> findByExDepth1(String depth1);
	Exercise findByExNo(String exNo);
	List<Exercise> findByExDepth1AndExDepth2In(String depth1,List<String> depth2);
}
