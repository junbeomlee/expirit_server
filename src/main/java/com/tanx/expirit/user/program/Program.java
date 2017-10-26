package com.tanx.expirit.user.program;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.dozer.Mapping;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tanx.expirit.exercise.Exercise;
import com.tanx.expirit.util.CommonEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tb_program")
@Entity
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Program implements Serializable{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -6725685511802189236L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="PROGRAM_SEQ")
	private Integer programSeq;
	
	@Column(name="EMAIL")
	private String email;
	
	@Mapping("this")
	@OneToOne(cascade={CascadeType.MERGE,CascadeType.DETACH},orphanRemoval=false,fetch = FetchType.LAZY)
	@JoinColumn(name="EX_NO",referencedColumnName="EX_NO")
	private Exercise exercise;
	
	@Column(name="DAY")
	private String day;
	
	@Column(name="DELETE_DT")
	private Date exDeleteDate;
	
	@Column(name="COMPLETE_DT")
	private Date exCompleteDate;
	
	@Column(name="EX_SET")
	private Integer exSet;
	
	@Column(name="WARMUP_SET")
	private Integer exWarmUpSet;
	
	@Column(name="PG_ORDER")
	private Integer pgOrder;

	@Embedded 
	@JsonIgnore
    private CommonEntity commonEntity;
	
	public Program(String email, Exercise exercise, String day) {
		// TODO Auto-generated constructor stub
		this.email=email;
		this.exercise=exercise;
		this.day=day;
	}
}
