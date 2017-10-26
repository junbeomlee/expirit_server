package com.tanx.expirit.login;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignUpDataDTO {
	@NotNull
	String email;
	String pw;
	@NotNull
	String name;
	String token;
	String joinType;
}
