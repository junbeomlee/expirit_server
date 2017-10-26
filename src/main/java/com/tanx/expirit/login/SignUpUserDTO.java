package com.tanx.expirit.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignUpUserDTO {

	private String email;
	
	private String userName;

	private String userPwd;

	private String userTel;

	private String userGender;

	private String userType;

	private int height;

	private int weight;

	private String purpose;

	private int userAge;

	private String withdrawYN;

	private String userLevel;

	private String joinType;

	private String weightPurpose;
	
	private String key;
}
