package com.hadine.web.controller;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.hadine.web.service.AdminService;
import com.hadine.web.util.Util;

@Controller
@RequestMapping("/admin")
public class AdminController { //count(*) id pw / name / grade
	//AdminService / AdminDAO / adminMapper
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private Util util;

	@GetMapping(value = {"/admin", "/"})
	public String adminIndex() {
		return "admin/index";
	}
	
	@PostMapping("/login") 
	public String adminLogin(@RequestParam Map<String, Object> map, HttpSession session) {
		System.out.println(map);
		Map<String, Object> result = adminService.adminLogin(map);
		System.out.println(result);
		//{m_grade=5, m_name=정식, count=1}
		//mgrade 5이상 count 1 
		System.out.println(String.valueOf(result.get("count")).equals("1"));
		System.out.println(Integer.parseInt(String.valueOf(result.get("m_grade"))));
		//int m_grade = Integer.parseInt(String.valueOf());
		
		if (util.obj2Int(result.get("m_grade")) > 5 && (String.valueOf(result.get("count")).equals("1"))) {
			System.out.println("좋았어 진행시켜!");
			//세션올리기
			session.setAttribute("mid", map.get("id"));
			session.setAttribute("mname", result.get("m_name"));
			session.setAttribute("mgrade", result.get("m_grade"));
			
			return "redirect:/admin/main";
			
			//메인으로 이동하기
		} else {
			return "redirect:/admin/admin?error=login";
		}
	}
		
	@GetMapping("/main")
	public String main() {
		return "admin/main"; //폴더 적어줘야 admin/밑에 main.jsp 열어줍니다.
	}
	
	@GetMapping("/notice")
	public String notice(Model model) {
		//1. 데이터베이스까지 연결하기
		//List<Map<String, Object>> list = adminService.list();
		//System.out.println(list);
		//2. 데이터 불러오기
		//model.addAttribute("list", list);
		//3. 데이터 jsp로 보내기
		return "admin/notice";
	}
	
	@PostMapping("/noticeWrite")
	public String noticeWrite(@RequestParam("upFile") MultipartFile upfile, @RequestParam Map<String, Object> map) {
		//{title=ㅇㄹㄴㄹㅇㄴㄹ, content=ㄴㅇㄹㄴㄹㄴㅇㄹㅇㄴ, upFile=}
		//System.out.println(map);
		
		if(!upfile.isEmpty()) {
			//저장할 경로명 뽑기 request 뽑기
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
			String path = request.getServletContext().getRealPath("/upload");
			System.out.println("실제경로 : " + path);

			
			
			
			//upfile 정보보기
			System.out.println(upfile.getOriginalFilename()); //실제 파일이름 가져오기
			System.out.println(upfile.getSize()); //용량 크기
			System.out.println(upfile.getContentType()); //어떤 타입인지
			//진짜로 파일 업로드 하기 : 경로 + 저장할 파일명 
			File newFileName = new File(upfile.getOriginalFilename()); 
			
		}
		
		
		
		map.put("mno", 1);
		//adminService.noticeWrite(map);
		return "redirect:/admin/notice";
	}
	
}