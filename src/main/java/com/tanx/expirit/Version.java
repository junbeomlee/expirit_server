package com.tanx.expirit;

import java.util.List;

import com.tanx.expirit.user.User;
import com.tanx.expirit.user.program.Program;
import com.tanx.expirit.user.record.Record;
import com.tanx.expirit.util.CommonEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Version {
	String appVersion;
	String dbVersion;
}
