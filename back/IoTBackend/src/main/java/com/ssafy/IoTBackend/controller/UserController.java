package com.ssafy.IoTBackend.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.IoTBackend.model.User;
import com.ssafy.IoTBackend.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//http://localhost:8080/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user")
@Api(value = "USER")
public class UserController {

	public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	public static final String SUCESS = "success";
	public static final String FAIL = "fail";

	@Autowired
	private UserService service;

	@ApiOperation(value = "회원가입", notes = "성공시 'success' 실패시 'fail' 반환")
	@PostMapping("/signup")
	public ResponseEntity<String> doSignUp(Authentication authentication, User dto) throws Exception {
		dto.setUser_id(authentication.getPrincipal().toString());
		int flag = service.insertUser(dto);

		if (flag == 0) return new ResponseEntity<String>(FAIL, HttpStatus.NOT_FOUND);
		return new ResponseEntity<String>(SUCESS, HttpStatus.OK);
	}

	@ApiOperation(value = "회원정보 가져오기", notes = "성공시  'User' 반환")
	@GetMapping
	public ResponseEntity<User> doGetUser(Authentication authentication) throws Exception {
		String userId = authentication.getPrincipal().toString();
		return new ResponseEntity<User>(service.selectUser(userId), HttpStatus.OK);
	}
	
	@ApiOperation(value = "회원정보 수정", notes = "성공시 'success' 실패시 'fail' 반환")
	@PutMapping
	public ResponseEntity<String> doUpdateUser(Authentication authentication, User dto) throws Exception {
		String userId = authentication.getPrincipal().toString();
		dto.setUser_id(userId);
		
		int flag = service.updateUser(dto);

		if (flag == 0) return new ResponseEntity<String>(FAIL, HttpStatus.NOT_FOUND);
		return new ResponseEntity<String>(SUCESS, HttpStatus.OK);
	}
	
	@ApiOperation(value = "회원 화분 정보 수정", notes = "성공시 'success' 실패시 'fail' 반환")
	@PutMapping("/pot")
	public ResponseEntity<String> doUpdateUserPot(Authentication authentication, @RequestBody User dto) throws Exception {
		String userId = authentication.getPrincipal().toString();
		dto.setUser_id(userId);

		int flag = service.updateUserPot(dto);

		if (flag == 0) return new ResponseEntity<String>(FAIL, HttpStatus.NOT_FOUND);
		return new ResponseEntity<String>(SUCESS, HttpStatus.OK);
	}
}
