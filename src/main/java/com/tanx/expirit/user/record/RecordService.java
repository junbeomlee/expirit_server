package com.tanx.expirit.user.record;

import rx.Observable;

public interface RecordService {

	Observable<Record> createRecord(String email, String exNo, String string);
	Observable<Record> addRecord(String email, String exNo, String string);
}
