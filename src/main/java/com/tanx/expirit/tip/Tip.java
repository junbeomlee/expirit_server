package com.tanx.expirit.tip;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_tips")
@Entity
@ToString
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Tip  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TIP_NO")
	int tipNo;
	
	@Column(name = "EX_NO")
	String exNo;
	
	@Column(name = "TIP_CONTENT")
	String tipContent;
}
