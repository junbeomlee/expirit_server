package com.tanx.expirit.history;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.dozer.Mapping;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tanx.expirit.exercise.Exercise;
import com.tanx.expirit.user.User;
import com.tanx.expirit.util.CommonEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tb_history")
@Entity
@ToString
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class History {

	private static final long serialVersionUID = -6725685511802189236L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="HISTORY_SEQ")
	private int historySeq;
	
	@Mapping("this")
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="EMAIL",referencedColumnName="EMAIL")
	private User user;
	
	@Mapping("this")
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="EX_NO",referencedColumnName="EX_NO")
	private Exercise exercise;
	
	@CreationTimestamp
	@Column(name="EX_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date exDate;
	
	@Column(name="HISTORY_VAL")
	private String historyVal;
	
	@Column(name="CNT_VAL")
	private String cntVal;
	
	@Embedded 
    private CommonEntity commonEntity;
}
