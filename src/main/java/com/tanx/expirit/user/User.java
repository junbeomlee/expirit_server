package com.tanx.expirit.user;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tanx.expirit.annotation.NotUpdatable;
import com.tanx.expirit.user.program.Program;
import com.tanx.expirit.user.record.Record;
import com.tanx.expirit.util.CommonEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
@Entity
@ToString
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class User implements Serializable {
	/**
	 * 
	 */
	@NotUpdatable
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EMAIL")
	@NotUpdatable
	private String email;

	@Column(name = "USER_NM")
	@NotUpdatable
	private String userName;

	@JsonIgnore
	@Column(name = "USER_PWD")
	private String userPwd;

	@Column(name = "USER_TEL")
	private String userTel;

	@Column(name = "USER_GENDER")
	private String userGender;

	@Column(name = " USER_TYPE")
	private String userType;

	@Column(name = "HEIGHT")
	private Integer height;

	@Column(name = "WEIGHT")
	private Integer weight;

	@Column(name = "EX_PREFER")
	private String exPrefer;

	@Column(name = "USER_BIRTHDAY")
	private String userBirthday;

	@Column(name = "WITHDRAW_YN")
	private String withdrawYN;

	@Column(name = "EX_SPLIT_TYPE")
	private String exSplitType;

	@Embedded
	@JsonIgnore
	private CommonEntity commonEntity;

	@Column(name = "USER_LEVEL")
	private String userLevel;

	@Column(name = "JOIN_TYPE")
	private String joinType;

	@Column(name = "WEIGHT_PURPOSE")
	private String weightPurpose;

	@Column(name = "EX_BODY_TYPE")
	private String exBodyType;

	@Column(name = "TOKEN_JOIN")
	private String tokenJoin;

	@Column(name = "TOKEN_FCM")
	private String tokenFCM;

	@Column(name = "QUEST_YN")
	private String questYN;

	@NotUpdatable
	@OneToMany(cascade = { CascadeType.ALL, CascadeType.REFRESH }, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "EMAIL", referencedColumnName = "EMAIL")
	private List<Program> programs = new ArrayList<Program>();

	@NotUpdatable
	@OneToMany(cascade = { CascadeType.ALL, CascadeType.REFRESH }, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "EMAIL", referencedColumnName = "EMAIL")
	private List<Record> records = new ArrayList<Record>();

	public void deleteProgram(int programSeq) {

		this.programs.removeIf(program -> program.getProgramSeq() == programSeq);
	}

	public void addProgram(Program program) {
		// TODO Auto-generated method stub
		this.programs.add(program);
	}

	public void setPrograms(List<Program> programs) {
		this.programs.clear();
		this.programs.addAll(programs);
	}

	public Program findProgram(int programSeq) {
		// TODO Auto-generated method stub
		return programs.stream().filter(program -> program.getProgramSeq() == programSeq).findFirst().orElse(null);

	}

	public Program findProgramByDayAndExNo(String day, String exNo) {
		return this.programs.stream().filter(program -> program.getExercise().getExNo().toString().equals(exNo))
				.filter(program -> program.getDay().toString().equals(day)).findFirst().orElse(null);
	}

	public User(String email) {
		// TODO Auto-generated constructor stub
		this.email = email;
	}

	public boolean checkPassword(String password) {
		// TODO Auto-generated method stub
		return this.userPwd.equals(password);
	}

	public Program getLastProgram() {
		// TODO Auto-generated method stub
		return this.programs.get(this.programs.size() - 1);
	}

	public void addRecord(Record record) {
		// TODO Auto-generated method stub
		this.records.add(record);
	}

	public Record getLastRecord() {
		// TODO Auto-generated method stub
		return this.records.get(this.records.size() - 1);
	}

	// test
	public Record findRecordByExNo(String exNo) {
		// TODO Auto-generated method stub
		return this.records.stream().filter(record -> record.getExercise().getExNo().toString().equals(exNo))
				.findFirst().orElse(null);
	}

	public void sortProgramByExOrder() {
		List<Program> sortedProgramList = this.programs.stream()
				.sorted((a, b) -> a.getExercise().getExOrder().compareTo(b.getExercise().getExOrder()))
				.collect(Collectors.toList());
		this.programs.clear();
		this.programs.addAll(sortedProgramList);
	}

	public void setPGorder() {
		this.sortProgramByExOrder();
		this.programs.forEach(new Consumer<Program>() {
			int index = 0;

			@Override
			public void accept(Program t) {
				// TODO Auto-generated method stub
				t.setPgOrder(index);
				index++;
			}
		});
	}

	public void deleteRecord(int recordNum) {
		this.records.removeIf(recrod -> recrod.getRecordNo() == recordNum);
	}

	public void update(User requestUser) {
		// TODO Auto-generated method stub
		ReflectionUtils.doWithFields(requestUser.getClass(), field -> {
			if (field.get(requestUser) != null) {
				Object updateField = field.get(requestUser);
				String fieldName = field.getName();
				Field userField = ReflectionUtils.findField(getClass(), fieldName);
				if (!userField.isAnnotationPresent(NotUpdatable.class)){
					userField.set(this, updateField);
				}
			}
		});
	}
}
