package com.tanx.expirit.exAvg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tanx.expirit.util.CommonEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tb_exercise_avg")
@Entity
@ToString
public class ExAvg implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="EX_AVG_NO")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int exAvgNo;
	
	@Column(name="EX_NO")
	private String exNo;
	
	@Column(name="EX_GENDER")
	private String exGender;

	@Column(name="EX_WEIGHT")
	private int exWeight;
	
	@Column(name="BEGINNER")
	private int beginner;
	
	@Column(name=" NOVICE")
	private int novice;

	@Column(name="INTERMEDIATE")
	private int intermediate;
	
	@Column(name="ADVANCED")
	private int advanced;
	
	@Column(name="ELITE")
	private int elite;

	@Embedded 
	@JsonIgnore
    private CommonEntity commonEntity;

}
