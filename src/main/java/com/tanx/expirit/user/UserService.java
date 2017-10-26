package com.tanx.expirit.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.tanx.expirit.login.SignUpUserDTO;
import com.tanx.expirit.user.program.Program;
import com.tanx.expirit.user.program.ProgramDTO;
import com.tanx.expirit.user.record.Record;

import rx.Observable;
import rx.Single;

public interface UserService {
	
	Single<User> createUser(User user);

	Observable<List<Program>> getProgramList(String email);

	Observable<UserDTO> login(String email, String password, HttpServletRequest request);

	Observable<Program> addProgram(User user,String exNo, String day,int exWarmUpSet, int exSet);
	
	Observable<Void> deleteProgram(User user, int exNo);

	Observable<SignUpUserDTO> signUp(String email, String password, String name,String joinType);

	Observable<User> updateUser(User user, User requestUser);

	Observable<UserDTO> loginByKey(String email, String key, HttpServletRequest request);

	Observable<Program> updateProgram(int i, String exNo, String day, User user);

	Observable<Program> setComplDateProgram(User user, String exNo,String day);

	Observable<Record> createRecord(User user, String exNo, String string);
	
	Observable<Record> addRecord(User user, String exNo, String string);

	Observable<UserDTO> loginByToken(String email,String token, HttpServletRequest request);

	Observable<User> signUpByFaceBook(String email, String name, String joinType, String token);

	void clearRecord(User user);

	Observable<Void> deleteRecord(User user, int recordNum);

	Observable<List<Program>> fillWarmUpAndCleaUpExercise(List<Program> programList);

	Observable<User> patchUser(UserDTO userDTO);

	Observable<Password.PasswordResult> updatePassword(User user, String currentPw, String changePw);

	void deleteUser(User user);
}
