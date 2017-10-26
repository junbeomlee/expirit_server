package com.tanx.expirit.util;

import lombok.Data;

public enum Day {
	MON("MON"),TUE("TUE"),WED("WED"),THR("THR"),FRI("FRI"),SAT("SAT"),SUN("SUN");
	
	private String day;
	
	Day(){
		
	}
	private Day(String day){
		this.day=day;
	}
	@Override
	public String toString(){
		return this.day;
	}
}
