package com.tanx.expirit.util;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable @Access(AccessType.FIELD)
public class CommonEntity {

	@Column(name="DEL_YN")
	private String deleteYN;
	
	@Column(name="CRE_DT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creDT;
	
	@Column(name="CRE_ID")
	private String creID;
	
	@Column(name="UPD_DT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updDT;
	
	@Column(name="UPD_ID")
	private String updID;
}
