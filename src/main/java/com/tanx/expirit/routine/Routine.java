package com.tanx.expirit.routine;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Routine {
	private String bodyPart;
	private List<RoutineEx> routineExList= new ArrayList<RoutineEx>();
}
