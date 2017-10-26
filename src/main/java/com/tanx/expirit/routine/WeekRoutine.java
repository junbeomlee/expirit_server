package com.tanx.expirit.routine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeekRoutine {
	
	@Id
	private String id;
	
	private String routineName;
	private Integer numOfDays;
	private String description;
	private List<String[]> daysList = new ArrayList<String[]>();
	private List<List<RoutineEx>> routineList = new ArrayList<List<RoutineEx>>();
	
	public boolean hasDays(String[] qdays){
		
		return this
		.daysList
		.stream()
		.filter(days->{
			Arrays.sort(days);
			Arrays.sort(qdays);
			return Arrays.toString(days).equals(Arrays.toString(qdays));
		})
		.findAny()
		.isPresent();
	}

	public boolean hasAll() {
		// TODO Auto-generated method stub
		return this.daysList
				.stream()
				.filter(days->Arrays.equals(days, new String[]{"ALL"}))
				.findAny()
				.isPresent();
	}
}
