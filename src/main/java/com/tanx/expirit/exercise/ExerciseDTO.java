package com.tanx.expirit.exercise;

import java.util.List;

import com.tanx.expirit.tip.Tip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDTO {
	int exNo;
    String exName;
    int restSecond;
    String method;
    String exImage;
    String exUrl;
    String exImgSysName;
    String exDefaultSet;
    String exImgPath;
    String exEtc;
    String exDesc;
    int exLevel;
    String exDepth1;
    String exDepth2;
    String exDepth3;
	private String exDataName;
	private String exSecond;
	private List<Tip> tips;
}
