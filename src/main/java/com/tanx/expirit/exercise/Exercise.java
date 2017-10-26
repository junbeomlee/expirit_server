package com.tanx.expirit.exercise;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tanx.expirit.exercise.exChange.ExChange;
import com.tanx.expirit.tip.Tip;
import com.tanx.expirit.util.CommonEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_exercise")
@Entity
@ToString
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Exercise implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EX_NO")
	private String exNo;

	@Column(name = "EX_NM")
	private String exName;

	@Column(name = "REST_SECOND")
	private int restSecond;

	@Column(name = "METHOD")
	private String method;

	@Column(name = "EX_DATA_NM")
	private String exDataName;

	@Column(name = "EX_URL")
	private String exUrl;

	@Column(name = "EX_IMG_SYS_NM")
	private String exImgSysName;

	@Column(name = "EX_DEFAULT_SET")
	private String exDefaultSet;

	@Embedded
	@JsonIgnore
	private CommonEntity commonEntity;

	@Column(name = "EX_IMG_PATH")
	private String exImgPath;

	@Column(name = "EX_ETC")
	private String exEtc;

	@Column(name = "EX_DESC")
	private String exDesc;

	@Column(name = "EX_LEVEL")
	private String exLevel;

	@Column(name = "EX_DEPTH1")
	private String exDepth1;

	@Column(name = "EX_DEPTH2")
	private String exDepth2;

	@Column(name = "EX_DEPTH3")
	private String exDepth3;
	
	@Column(name= "EX_ORDER")
	private Integer exOrder;
	
	@Column(name= "EX_ATTRIBUTE")
	private String exAttribute;
	
	@OneToMany(cascade={CascadeType.ALL,CascadeType.REFRESH},fetch=FetchType.LAZY,orphanRemoval=true)
	@JoinColumn(name="EX_NO",referencedColumnName="EX_NO")
	private List<Tip> tips = new ArrayList<Tip>();
	
	@OneToMany(cascade={CascadeType.ALL,CascadeType.REFRESH},fetch=FetchType.LAZY,orphanRemoval=true)
	@JoinColumn(name="EX_NO",referencedColumnName="EX_NO")
	private List<ExChange> exChanges = new ArrayList<ExChange>();
	
	public Exercise(String exNo) {
		this.exNo=exNo;
		this.exName="asd";
	}

}
