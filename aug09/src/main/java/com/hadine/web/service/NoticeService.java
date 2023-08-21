package com.hadine.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadine.web.dao.NoticeDAO;

@Service
public class NoticeService {

	@Autowired
	private NoticeDAO noticeDAO;

	public List<Map<String, Object>> list() {
		List<Map<String, Object>> list = noticeDAO.list();

		return list;
	}
}