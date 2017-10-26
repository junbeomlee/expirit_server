package com.tanx.expirit.task;

import com.tanx.expirit.history.HistoryDTO;
import com.tanx.expirit.user.User;
import com.tanx.expirit.user.record.Record;

import rx.Observable;

public interface TaskService {
	Observable<Record> addTask(HistoryDTO historyDTO,User user);
}
