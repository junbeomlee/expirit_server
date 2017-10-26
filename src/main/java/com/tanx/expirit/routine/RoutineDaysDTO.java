package com.tanx.expirit.routine;

import java.util.List;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineDaysDTO {

	String[] days;
	List<RoutineEx> routine;
}
