package com.tanx.expirit.user;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.tanx.expirit.user.program.ProgramDTO;
import com.tanx.expirit.user.record.RecordDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
	
	@NotNull
	private String email;
	@NotNull
	private String userName;
	private String userTel;
	private String userGender;
	private String userType;
	private Integer height;
	private Integer weight;
	private String exPrefer;
	private String userBirthday;
	private String withdrawYN;
	private String exSplitType;
	private String userLevel;
	private String joinType;
	private String weightPurpose;
	private String exBodyType;
	private String tokenJoin;
	private String tokenFCM;
	private String questYN;
	private List<ProgramDTO> programs;
	private String key;
	private List<RecordDTO> records;
}
