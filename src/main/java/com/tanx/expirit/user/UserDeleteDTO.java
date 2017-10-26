package com.tanx.expirit.user;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.tanx.expirit.history.HistoryDTO;
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
public class UserDeleteDTO {

	@NotNull
	private String email;
	@NotNull
	private String userName;
	private String userGender;
	private String userType;
	private Integer height;
	private Integer weight;
	private String exPrefer;
	private String weightPurpose;
	private List<ProgramDTO> programs;
	private List<RecordDTO> records;
	private List<HistoryDTO> historys;
}
