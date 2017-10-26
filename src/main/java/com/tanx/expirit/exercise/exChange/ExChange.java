package com.tanx.expirit.exercise.exChange;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tanx.expirit.exercise.Exercise;
import com.tanx.expirit.tip.Tip;
import com.tanx.expirit.util.CommonEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_ex_change")
@Entity
@ToString
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class ExChange implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="EX_CHANGE_NO")
	private Integer exChangeNo;

	@Column(name = "CHANGE_DTL")
	private String changeDTL;

	@Column(name = "EX_CHANGE_TYPE")
	private String exChangeType;

	@Column(name = "EX_WEIGHT_RATIO")
	private Double exWeightRatio;

	@Column(name = "EX_FREQ_RATIO")
	private Double exFreqRatio;

	@Column(name = "CHANGE_EX_NO")
	private Integer changeExNo;

	@Column(name = "EX_GUIDE")
	private String exGuide;
}
