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
public class LoginKeyDataDTO {

	@NotNull
	String email;
	@NotNull
	String key;
	
}
