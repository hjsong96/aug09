package com.hadine.web.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadine.web.dao.LoginDAO;

@Service
public class LoginService {

	@Autowired
	private LoginDAO loginDAO;
	
	public Map<String, Object> login(Map<String, String> map) {
		return loginDAO.login(map);
	}

}
