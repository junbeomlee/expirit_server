package com.tanx.expirit.user;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.tanx.expirit.annotation.RxTransactional;
import com.tanx.expirit.exception.CustomRuntimeException;
import com.tanx.expirit.exception.ExceptionDTO;
import com.tanx.expirit.exception.LoginFailException;
import com.tanx.expirit.exception.NotFoundIDException;
import com.tanx.expirit.exercise.Exercise;
import com.tanx.expirit.exercise.ExerciseRepository;
import com.tanx.expirit.history.History;
import com.tanx.expirit.history.HistoryRepository;
import com.tanx.expirit.history.HistoryService;
import com.tanx.expirit.login.SignUpUserDTO;
import com.tanx.expirit.user.Password.PasswordResult;
import com.tanx.expirit.user.program.Program;
import com.tanx.expirit.user.record.Record;
import com.tanx.expirit.util.AES128;
import com.tanx.expirit.util.CommonEntity;
import com.tanx.expirit.util.DTOMapper;
import com.tanx.expirit.util.DateUtil;

import rx.Observable;
import rx.Single;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ExerciseRepository exerciseRepository;

	@Autowired
	private HistoryRepository historyRepository;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private HistoryService historyService;

	@Override
	public Single<User> createUser(User user) {
		// TODO Auto-generated method stub
		return Single.just(user).map(newUser -> userRepository.save(newUser));
	}

	@Override
	public Observable<List<Program>> getProgramList(String email) {
		// TODO Auto-generated method stub
		return Observable.defer(() -> {
			User user = userRepository.findOne(email);
			if (user == null)
				throw new NotFoundIDException("invaild email");
			return Observable.just(user.getPrograms());
		});
	}

	@Override
	public Observable<UserDTO> login(String email, String password, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return Observable.defer(() -> {
			User user = userRepository.findOne(email);
			if (user != null) {
				if (passwordEncoder.matches(password, user.getUserPwd())) {
					String key = AES128.encrypt(user.getEmail().concat(user.getUserPwd()));
					HttpSession httpSession = request.getSession();
					httpSession.setAttribute("sessionId", user);
					user.sortProgramByExOrder();
					UserDTO userDTO = DTOMapper.mapUserToUserDTO(user, UserDTO.class);
					userDTO.setKey(key);
					return Observable.just(userDTO);
				}
				throw new LoginFailException("login fail");
			} else
				throw new LoginFailException("login fail");
		});
	}

	// todo test
	@Override
	public Observable<Program> addProgram(User user, String exNo, String day, int exWarmUpSet, int exSet) {
		// TODO Auto-generated method stub
		return Observable.defer(() -> {
			Exercise exercise = exerciseRepository.findOne(exNo);
			if (exercise == null)
				throw new NotFoundIDException("invaild exNo");

			Program program = new Program();
			program.setEmail(user.getEmail());
			program.setExercise(exercise);
			program.setExWarmUpSet(exWarmUpSet);
			program.setExSet(exSet);
			program.setDay(day);
			user.addProgram(program);
			User updatedUser = userRepository.save(user);
			System.out.println(program);
			userRepository.flush();
			System.out.println(program);
			return Observable.just(program);
		});
	}

	@Override
	public Observable<Void> deleteProgram(User user, int programNo) {
		// TODO Auto-generated method stub
		return Observable.defer(() -> {

			user.deleteProgram(programNo);
			userRepository.save(user);
			return Observable.empty();
		});
	}

	@RxTransactional
	@Override
	public Observable<SignUpUserDTO> signUp(String email, String password, String name, String joinType) {
		// TODO Auto-generated method stub
		return Observable.defer(() -> {

			User user = new User();
			user.setEmail(email);
			user.setJoinType(joinType);
			user.setUserPwd(passwordEncoder.encode(password));
			user.setUserName(name);
			
			CommonEntity cn = new CommonEntity();
			cn.setCreDT(DateUtil.getNow());
			cn.setUpdDT(DateUtil.getNow());
			user.setCommonEntity(cn);
			user = userRepository.save(user);
			return Observable.just(user);
		}).map(user -> {
			SignUpUserDTO signUpUserDTO = DTOMapper.mapper.map(user, SignUpUserDTO.class);
			String key = AES128.encrypt(user.getEmail().concat(user.getUserPwd()));
			signUpUserDTO.setKey(key);
			return signUpUserDTO;
		});
	}

	@Override
	public Observable<User> updateUser(User user, User requestUser) {
		// TODO Auto-generated method stub
		return Observable.create(sub -> {
			user.update(requestUser);
			sub.onNext(userRepository.save(user));
			sub.onCompleted();
		});
	}

	@Override
	public Observable<UserDTO> loginByKey(String email, String loginKey, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return Observable.defer(() -> {

			User user = userRepository.findOne(email);
			if (user == null)
				throw new LoginFailException("no user exists");

			String key = AES128.encrypt(user.getEmail().concat(user.getUserPwd()));

			if (key.equals(loginKey)) {
				HttpSession httpSession = request.getSession();
				httpSession.setAttribute("sessionId", user);
				user.sortProgramByExOrder();
				UserDTO userDTO = DTOMapper.mapUserToUserDTO(user, UserDTO.class);
				userDTO.setKey(key);
				return Observable.just(userDTO);
			} else
				throw new LoginFailException("Wrong password");
		});
	}

	@Override
	public Observable<UserDTO> loginByToken(String email, String token, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return Observable.defer(() -> {
			User user = userRepository.findOne(email);
			if (user != null) {
				HttpSession httpSession = request.getSession();
				httpSession.setAttribute("sessionId", user);
				user.sortProgramByExOrder();
				UserDTO userDTO = DTOMapper.mapUserToUserDTO(user, UserDTO.class);
				return Observable.just(userDTO);
			} else
				throw new LoginFailException("login fail");

		});
	}

	@Override
	public Observable<Program> updateProgram(int number, String exNo, String day, User user) {
		// TODO Auto-generated method stub
		return Observable.defer(() -> {
			Exercise exercise = exerciseRepository.findOne(exNo);
			if (exercise == null)
				throw new NotFoundIDException("invaild exNo");
			Program program = user.findProgram(number);
			program.setDay(day);
			program.setExercise(exercise);
			userRepository.save(user);
			return Observable.just(program);
		});
	}

	@Override
	public Observable<Program> setComplDateProgram(User user, String exNo, String day) {
		// TODO Auto-generated method stub
		return Observable.defer(() -> {
			Program foundProgram = user.findProgramByDayAndExNo(day, exNo);
			if (foundProgram == null)
				throw new NotFoundIDException("no found program");
			foundProgram.setExCompleteDate(DateUtil.getNow());
			User updateUser = userRepository.save(user);
			return Observable.just(foundProgram);
		});
	}

	@Override
	public Observable<Record> createRecord(User user, String exNo, String historyValue) {
		// TODO Auto-generated method stub
		return Observable.defer(() -> {
			// 이 운동을 처음으로 한다.
			Exercise exercise = exerciseRepository.findOne(exNo);
			if (exercise == null)
				throw new NotFoundIDException("invaild exNo");

			Record record = new Record();
			record.setExercise(exercise);
			record.setExFreq(1);
			record.setEmail(user.getEmail());
			record.setRecordValue(historyValue);
			record.setOneRm(record.calOneRm(historyValue));
			user.addRecord(record);
			User updateUser = userRepository.save(user);

			return Observable.just(updateUser.getLastRecord());
		});
	}

	@Override
	public Observable<Record> addRecord(User user, String exNo, String historyValue) {
		return Observable.defer(() -> {
			Record record = user.findRecordByExNo(exNo);
			if (record == null)
				return createRecord(user, exNo, historyValue);
			else {
				if (record.getExFreq() >= 2) {
					List<History> historyList = historyRepository.findByUserEmailAndExerciseExNo(user.getEmail(), exNo)
							.stream().limit(4).collect(Collectors.toList());
					record.countUpExFreq();
					record.setRecordValue(record.calHistoryToRecord(historyList));
					return Observable.just(record);
				}
				record.countUpExFreq();
				record.setOneRmFromRecordValue();
				return Observable.just(record);
			}
		});
	}

	@Override
	public Observable<User> signUpByFaceBook(String email, String name, String joinType, String token) {
		// TODO Auto-generated method stub
		return Observable.defer(() -> {
			User user = userRepository.findOne(email);
			if (user == null) {
				user = new User();
				user.setEmail(email);
				user.setJoinType(joinType);
				user.setUserName(name);
				user.setTokenJoin(token);
				CommonEntity cn = new CommonEntity();
				cn.setCreDT(DateUtil.getNow());
				cn.setUpdDT(DateUtil.getNow());
				user.setCommonEntity(cn);
				user = userRepository.save(user);
				return Observable.just(user);
			}
			throw new CustomRuntimeException("duplicated email");
		});
	}

	@Override
	public void clearRecord(User user) {
		// TODO Auto-generated method stub
		user.getRecords().clear();
		userRepository.save(user);
	}

	@Override
	public Observable<Void> deleteRecord(User user, int recordNum) {
		// TODO Auto-generated method stub
		return Observable.defer(() -> {

			user.deleteRecord(recordNum);
			userRepository.save(user);
			return Observable.empty();
		});
	}

	@Override
	public Observable<List<Program>> fillWarmUpAndCleaUpExercise(List<Program> programList) {
		// TODO Auto-generated method stub
		// HashMap<String,>programList.
		return Observable.defer(() -> {

			List<Program> willBeAddedProgramList = new ArrayList<Program>();
			List<Exercise> exerciseList = exerciseRepository.findAll();
			Map<String, Program> dayExerciseMap = new HashMap<String, Program>();
			Map<String, Program> dayMap = new HashMap<String, Program>();

			programList.forEach(program -> dayExerciseMap
					.put(program.getDay().concat(program.getExercise().getExDepth2()), program));
			programList.forEach(program -> dayMap.put(program.getDay(), program)); // 요일
																					// 구하기
			dayExerciseMap.entrySet().stream().forEach(e -> {
				Program program = e.getValue();
				switch (program.getExercise().getExDepth2()) {
				case "006002":
					exerciseList.stream()
							.filter(ex -> ex.getExNo().equals("106") || ex.getExNo().equals("9")
									|| ex.getExNo().equals("12") || ex.getExNo().equals("128")
									|| ex.getExNo().equals("112") || ex.getExNo().equals("115"))
							.filter(ex -> ex.getExAttribute().equals("024001")).forEach(ex -> {
						Program addProgram = new Program();
						addProgram.setDay(program.getDay());
						addProgram.setEmail(program.getEmail());
						addProgram.setExercise(ex);
						addProgram.setExSet(1);
						programList.add(addProgram);
					});
					break;
				case "006003":
					exerciseList.stream()
							.filter(ex -> ex.getExNo().equals("107") || ex.getExNo().equals("21")
									|| ex.getExNo().equals("129") || ex.getExNo().equals("122"))
							.filter(ex -> ex.getExAttribute().equals("024001")).forEach(ex -> {
						Program addProgram = new Program();
						addProgram.setDay(program.getDay());
						addProgram.setEmail(program.getEmail());
						addProgram.setExercise(ex);
						addProgram.setExSet(1);
						programList.add(addProgram);
					});
					break;
				case "006004":
					exerciseList.stream()
							.filter(ex -> ex.getExNo().equals("16") || ex.getExNo().equals("17")
									|| ex.getExNo().equals("19") || ex.getExNo().equals("117")
									|| ex.getExNo().equals("118") || ex.getExNo().equals("120"))
							.filter(ex -> ex.getExAttribute().equals("024001")).forEach(ex -> {
						Program addProgram = new Program();
						addProgram.setDay(program.getDay());
						addProgram.setEmail(program.getEmail());
						addProgram.setExercise(ex);
						addProgram.setExSet(1);
						programList.add(addProgram);
					});
					break;
				case "006005":
					exerciseList.stream()
							.filter(ex -> ex.getExNo().equals("104") || ex.getExNo().equals("105")
									|| ex.getExNo().equals("126") || ex.getExNo().equals("127"))
							.filter(ex -> ex.getExAttribute().equals("024001")).forEach(ex -> {
						Program addProgram = new Program();
						addProgram.setDay(program.getDay());
						addProgram.setEmail(program.getEmail());
						addProgram.setExercise(ex);
						addProgram.setExSet(1);
						programList.add(addProgram);
					});
					break;
				}
			});

			exerciseList.stream()
					.filter(ex -> ex.getExDepth2().equals("006000")
							|| ((ex.getExDepth1().equals("005010") || ex.getExDepth1().equals("005012"))
									&& ex.getExAttribute().equals("024001")))
					.forEach(exercise -> {
						if (exercise.getExDepth2().equals("006000")) {
							dayMap.entrySet().stream().forEach(e -> {
								Program addProgram = new Program();
								addProgram.setDay(e.getValue().getDay());
								addProgram.setEmail(e.getValue().getEmail());
								addProgram.setExercise(exercise);
								addProgram.setExSet(1);
								programList.add(addProgram);
							});
						}
			});

			// programList.addAll(willBeAddedProgramList);
			return Observable.just(programList);
		});
	}

	@Override
	public Observable<User> patchUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Observable<PasswordResult> updatePassword(User user, String currentPw, String changePw) {
		// TODO Auto-generated method stub
		return Observable.defer(() -> {
			if (user.checkPassword(passwordEncoder.encode(currentPw)))
				return Observable.just(new Password.PasswordResult("", false));
			else {
				user.setUserPwd(passwordEncoder.encode(changePw));
				userRepository.save(user);
				String key = AES128.encrypt(user.getEmail().concat(user.getUserPwd()));
				return Observable.just(new Password.PasswordResult(key, true));
			}
		});
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		RestTemplate template = new RestTemplate();
		ResponseEntity<Object> rawRes = null;
		
		List<History> historyList=historyService.getAll(user).toBlocking().single();
		
		UserDeleteDTO userDeleteDTO=DTOMapper.mapUserToUserDeleteDTO(user, historyList, UserDeleteDTO.class);
		
		BodyBuilder bodyBuilder = RequestEntity.method(HttpMethod.POST, URI.create("http://127.0.0.1:5050/users"));
		
		try {
			rawRes = template.exchange(bodyBuilder.body(userDeleteDTO), Object.class);
			
		}catch(HttpClientErrorException exception){
			
			ExceptionDTO exceptionDTO = new ExceptionDTO();
			exceptionDTO.setCode(exception.getStatusCode().value());
			exceptionDTO.setMessage(exception.getResponseBodyAsString());
			rawRes = new ResponseEntity<Object>(exceptionDTO,exception.getStatusCode());
			return;
		}
		
		userRepository.delete(user);
		historyRepository.deleteByUser(user);
	}
}
