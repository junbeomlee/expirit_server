package com.tanx.expirit.user.record;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanx.expirit.exercise.Exercise;
import com.tanx.expirit.exercise.ExerciseRepository;
import com.tanx.expirit.history.History;
import com.tanx.expirit.history.HistoryRepository;

import rx.Observable;

@Service
public class RecordServiceImpl implements RecordService {

	@Autowired
	RecordRepository recordRepository;
	
	@Autowired
	HistoryRepository historyRepository;
	
	@Autowired
	ExerciseRepository exerciseRepository;
	
	@Override
	public Observable<Record> createRecord(String email, String exNo,String historyValue) {
		// TODO Auto-generated method stub
		return Observable.create(sub->{
			//이 운동을 처음으로 한다.
			Exercise exercise = exerciseRepository.findOne(exNo);
			if(exercise==null)
				throw new RuntimeException("null exercise");
			Record record = new Record();
			record.setExercise(exercise);
			record.setExFreq(1);
			record.setEmail(email);
			record.setRecordValue(historyValue);
			record.setOneRm(record.calOneRm(historyValue));;
			recordRepository.save(record);
			sub.onNext(record);
			sub.onCompleted();
		});
	}
	
	@Override
	public Observable<Record> addRecord(String email,String exNo,String historyValue){
		return Observable.<Record>create(sub->{
			Record record= recordRepository.findByEmailAndExerciseExNo(email, exNo);
			if(record==null){
				Record createdRecord=createRecord(email,exNo,historyValue).toBlocking().single();
				sub.onNext(createdRecord);
				sub.onCompleted();
			}
			else{
				if(record.getExFreq()>=2){ //history에서 4개 뽑아서
					List<History> historyList=Observable
					.from(historyRepository.findByUserEmailAndExerciseExNo(email, exNo))
					.takeLast(4)
					.toList().toBlocking().single();
					record.setRecordValue(record.calHistoryToRecord(historyList));
				}
				record.countUpExFreq();
				record.setOneRmFromRecordValue();
				sub.onNext(recordRepository.save(record));
				sub.onCompleted();
			}
		}); 
	}
	
}
