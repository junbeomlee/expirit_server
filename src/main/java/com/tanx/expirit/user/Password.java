package com.tanx.expirit.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public  class Password {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class PasswordResult {
		String key;
		Boolean isSuccess = false;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class PasswordDTO {
		String currentPw;
		String changePw;
	}
}
