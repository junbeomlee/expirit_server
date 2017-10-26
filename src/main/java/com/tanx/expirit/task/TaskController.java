package com.tanx.expirit.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tanx.expirit.history.HistoryDTO;
import com.tanx.expirit.user.User;
import com.tanx.expirit.user.record.RecordDTO;
import com.tanx.expirit.util.DTOMapper;

import lombok.extern.slf4j.Slf4j;
import rx.Single;

@Slf4j
@RestController
public class TaskController {

	@Autowired
	TaskService taskService;

	@RequestMapping(value="/tasks", method=RequestMethod.POST)
	public Single<RecordDTO> addHistoryList(User user,
			@RequestBody HistoryDTO historyDTO){
		//history add하고
		return taskService.addTask(historyDTO,user)
				.map(record->DTOMapper.mapper.map(record, RecordDTO.class))
				.toSingle();
	}
}
