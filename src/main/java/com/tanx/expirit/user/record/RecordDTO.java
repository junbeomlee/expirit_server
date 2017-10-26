package com.tanx.expirit.user.record;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecordDTO {
	@NotNull
	int recordNo;
	@NotNull
    int exFreq;
	@NotNull
    int exNo;
	
	String exName;
    String oneRm;
    String recordValue;
}
