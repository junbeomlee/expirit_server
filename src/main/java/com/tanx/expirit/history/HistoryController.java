package com.tanx.expirit.history;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tanx.expirit.user.User;
import com.tanx.expirit.util.DTOMapper;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Single;

@Slf4j
@RestController
@RequestMapping("/histories")
public class HistoryController{
	
	@Autowired
	HistoryService historyService;
	
	@RequestMapping(method = RequestMethod.GET)
	public Single<List<HistoryDTO>> getList(User user) {

		return historyService.getAll(user).flatMap(Observable::from)
				.map(history -> DTOMapper.mapper.map(history, HistoryDTO.class)).toList().toSingle();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Single<HistoryDTO> insert(User user, @RequestBody HistoryDTO historyDTO){	
		return historyService
		.addHistory(historyDTO.exNo, user, historyDTO.historyVal, historyDTO.day,historyDTO.getCntVal())
		.map(history->DTOMapper.mapper.map(history, HistoryDTO.class))
		.toSingle();
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	public void deleteAllHistory(User user){	
		historyService.deleteAll(user);
	}
}
