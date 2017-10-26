package com.tanx.expirit.routine;

import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoutineRepository extends MongoRepository<WeekRoutine, ObjectId>{

	//List<WeekRoutine> findByDaysList(String[] strings);

	List<WeekRoutine> findByNumOfDays(Integer length);

}
