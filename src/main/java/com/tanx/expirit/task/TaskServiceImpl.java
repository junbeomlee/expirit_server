package com.tanx.expirit.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanx.expirit.annotation.RxTransactional;
import com.tanx.expirit.history.HistoryDTO;
import com.tanx.expirit.history.HistoryService;
import com.tanx.expirit.user.User;
import com.tanx.expirit.user.UserService;
import com.tanx.expirit.user.record.Record;

import rx.Observable;

@Service
public class TaskServiceImpl implements TaskService{

	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	UserService userService;
	
	@RxTransactional
	public Observable<Record> addTask(HistoryDTO historyDTO,User user){
		return historyService
			.addHistory(historyDTO.getExNo(),user,historyDTO.getHistoryVal(),historyDTO.getDay(),historyDTO.getCntVal())
			.concatMap(history->userService.setComplDateProgram(user,String.valueOf(historyDTO.getExNo()),historyDTO.getDay()))
			.concatMap(asd->userService.addRecord(user, String.valueOf(historyDTO.getExNo()), historyDTO.getHistoryVal()));
	}
}
