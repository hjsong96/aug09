package com.hadine.web.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminDAO {

	Map<String, Object> adminLogin(Map<String, Object> map);

	List<Map<String, Object>> list();

	void noticeWrite(Map<String, Object> map);

	String noticeDetail(int nno);

	int noticeHide(int nno);

	List<Map<String, Object>> list2();

	int multiBoardInsert(Map<String, Map> map);

	List<Map<String, Object>> memberList();

	int gradeChange(Map<String, String> map);

	List<Map<String, Object>> post(Map<String, Object> map);

	List<Map<String, Object>> boardList();

	Map<String, String> openC(Map<String, Object> map);
}
