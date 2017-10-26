package com.tanx.expirit.user.record;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.dozer.Mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tanx.expirit.exercise.Exercise;
import com.tanx.expirit.history.History;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_ex_record")
@Entity
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Record implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RECORD_SEQ")
	private Integer recordNo;

	@Column(name = "EMAIL")
	private String email;

	@Mapping("this")
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH }, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "EX_NO", referencedColumnName = "EX_NO")
	private Exercise exercise;

	@Column(name = "1RM")
	private String oneRm;

	@Column(name = "EX_FREQ")
	private Integer exFreq = 1;

	@Column(name = "RECORD_VAL")
	private String recordValue;

	public void countUpExFreq() {
		// TODO Auto-generated method stub
		this.exFreq++;
	}

	public String calHistoryToRecord(List<History> historyList) {
		// TODO Auto-generated method stub

		int size=historyList.size();
		
		Integer[] recordValueArray= new Integer[30];
		int[] recordValueCountArray = new int[30];
		for (History historyItem : historyList) {
			String historyValue=historyItem.getHistoryVal();
			int historyLength=historyItem.getHistoryVal().length();
	
			for(int i=0;i<(historyLength/3);i++){
				int setValue=Integer.parseInt(historyValue.substring(i*3,i*3+3));
				if(recordValueArray[i]!=null){
					Integer recordValue=recordValueArray[i];
					recordValue=recordValue+setValue;
					recordValueArray[i]=recordValue;
					
				}
				else{
					recordValueCountArray[i]=0;
					recordValueArray[i]=setValue;
				}
				recordValueCountArray[i]++;
			}
		}
		
		List<Integer> recordValueList = Arrays.asList(recordValueArray);
		
		String recordValueStringFormat = recordValueList
				.stream()
				.filter(recordSetValue->recordSetValue!=null)
				.map(new Function<Integer, String>() {
					int index=-1;
					@Override
					public String apply(Integer recordSetValue) {
						// TODO Auto-generated method stub
						index++;
						return String.format("%03d", recordSetValue/recordValueCountArray[index]);
					}
				})
				.collect(Collectors.joining(""));

		return recordValueStringFormat;
	}

	public String calOneRm(String historyValue) {
		// TODO Auto-generated method stub
		int oneRmValue=0;
		for(int i=0;i<(historyValue.length()/3);i++){
			oneRmValue=oneRmValue+Integer.parseInt(historyValue.substring(i*3,i*3+3));
		}
		return String.format("%03d", oneRmValue/(historyValue.length()/3));
	}

	public void setOneRmFromRecordValue() {
		// TODO Auto-generated method stub
		int oneRmValue=0;
		for(int i=0;i<(recordValue.length()/3);i++){
			oneRmValue=oneRmValue+Integer.parseInt(recordValue.substring(i*3,i*3+3));
		}
		
		this.oneRm=String.format("%03d", oneRmValue/(recordValue.length()/3));
	}
}
