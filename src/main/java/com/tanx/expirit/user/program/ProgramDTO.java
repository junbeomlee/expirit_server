package com.tanx.expirit.user.program;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDTO{
	int programSeq;
	String exNo;
	String exName;
	String day;
	String exCompleteDate;
	String exDeleteDate;
	int exSet;
	int exWarmupSet;
	Integer exOrder;
}
