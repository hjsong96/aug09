package com.hadine.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.mail.EmailException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hadine.web.service.AdminService;
import com.hadine.web.util.Util;

@Controller
@RequestMapping("/admin")
public class AdminController { // count(*) id pw / name / grade
	// AdminService / AdminDAO / adminMapper
	@Autowired
	private AdminService adminService;

	@Autowired
	private Util util;

	@GetMapping(value = { "/admin", "/" })
	public String adminIndex() {
		return "admin/index";
	}

	@PostMapping("/login")
	public String adminLogin(@RequestParam Map<String, Object> map, HttpSession session) {
		System.out.println(map);
		Map<String, Object> result = adminService.adminLogin(map);
		System.out.println(result);
		// {m_grade=5, m_name=정식, count=1}
		// mgrade 5이상 count 1
		System.out.println(String.valueOf(result.get("count")).equals("1"));
		System.out.println(Integer.parseInt(String.valueOf(result.get("m_grade"))));
		// int m_grade = Integer.parseInt(String.valueOf());

		if (util.obj2Int(result.get("m_grade")) > 5 && (String.valueOf(result.get("count")).equals("1"))) {
			System.out.println("좋았어 진행시켜!");
			// 세션올리기
			session.setAttribute("mid", map.get("id"));
			session.setAttribute("mname", result.get("m_name"));
			session.setAttribute("mgrade", result.get("m_grade"));

			return "redirect:/admin/main";

			// 메인으로 이동하기
		} else {
			return "redirect:/admin/admin?error=login";
		}
	}

	@GetMapping("/main")
	public String main() {
		return "admin/main"; // 폴더 적어줘야 admin/밑에 main.jsp 열어줍니다.
	}

	@GetMapping("/notice")
	public String notice(Model model) {
		// 1. 데이터베이스까지 연결하기
		List<Map<String, Object>> list = adminService.list();
		// System.out.println(list);
		// 2. 데이터 불러오기
		model.addAttribute("list", list);
		// 3. 데이터 jsp로 보내기
		return "admin/notice";
	}

	@PostMapping("/noticeWrite")
	public String noticeWrite(@RequestParam("upFile") MultipartFile upfile, @RequestParam Map<String, Object> map,
			HttpSession session) {
		// {title=ㅇㄹㄴㄹㅇㄴㄹ, content=ㄴㅇㄹㄴㄹㄴㅇㄹㅇㄴ, upFile=}
		System.out.println(map);

		// 2023-08-22 요구사항 확인
		//
		if (!upfile.isEmpty()) {
			// 저장할 경로명 뽑기 request 뽑기
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
					.getRequest();
			String path = request.getServletContext().getRealPath("/upload");
			System.out.println("실제경로 : " + path);

			// upfile 정보보기
			// System.out.println(upfile.getOriginalFilename()); //실제 파일이름 가져오기
			// System.out.println(upfile.getSize()); //용량 크기
			// System.out.println(upfile.getContentType()); //어떤 타입인지
			// 진짜로 파일 업로드 하기 : 경로 + 저장할 파일명
			// String타입의 경로를 file 형태로 바꿔주겠습니다.
			// File filePath = new File(path);
			// 중복이 발생할 수 있기 때문에... 파일명 + 날짜 + ID + .파일확장자
			// UUID + 파일명 + .확장자
			// 아이디 + UUID + 파일명.확장자

			// 날짜 뽑기 SimpleDateFormat
			// Date date = new Date();
			// SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
			// String dateTime = sdf.format(date);
			// realFileName = dateTime.toString() + upfile.getOriginalFilename();

			UUID uuid = UUID.randomUUID();
			// String realFileName = uuid.toString() + upfile.getOriginalFilename();
			// 다른 날짜 뽑기 형식
			LocalDateTime ldt = LocalDateTime.now();
			String format = ldt.format(DateTimeFormatter.ofPattern("YYYYMMddHHmmss"));
			String mid = (String) session.getAttribute("mid");
			// 아이디 + 날짜 + UUID + 실제 파일명으로 사용하겠습니다.
			String realFileName = mid + format + uuid.toString() + upfile.getOriginalFilename();

			File newFileName = new File(path, realFileName);
			// 이제 파일을 올립니다.
			try {
				// upfile.transferTo(newFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// System.out.println("저장 끝.");
			// FileCopyUtils를 사용하기 위해서는 오리지널 파일을 byte[]로 만들어야 합니다.
			try {
				FileCopyUtils.copy(upfile.getBytes(), newFileName);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// #{upFile}, #{realFile}
			map.put("upFile", upfile.getOriginalFilename());
			map.put("realFile", realFileName);
		}

		map.put("mno", 1);
		adminService.noticeWrite(map);
		return "redirect:/admin/notice";
	}

	@GetMapping("/mail")
	public String mail() {
		return "admin/mail";
	}

	@PostMapping("/mail")
	public String mail(@RequestParam Map<String, Object> map) throws EmailException {
		// {title=제목, to=ghkwlsdbwls@naver.com, content=안녕}
		// System.out.println(map);
		// return "forward:/mail";

		// util.simpleMailSender(map);
		util.htmlMailSender(map);

		return "admin/mail";
	}

	// noticeDetail
	@ResponseBody
	@PostMapping("noticeDetail")
	public String noticeDetail(@RequestParam("nno") int nno) {
		System.out.println(nno);

		// jackson사용해보기
		ObjectNode json = JsonNodeFactory.instance.objectNode();
		// json.put("name", "홍길동");
		// 해야할 일
		/*
		 * 1. 데이터 베이스에 물어보기 -> nno로 -> 본문 내용 가져오기 2. jackson에 담아주세요.
		 */
		/*
		 * map json에 담는법 Map<String, Object> maaaap = new HashMap<String, Object>();
		 * maaaap.put("bno", 123); maaaap.put("btitle", 1234);
		 * 
		 * ObjectMapper jsonMap = new ObjectMapper(); try { json.put("map",
		 * jsonMap.writeValueAsString(maaaap)); } catch (JsonProcessingException e) {
		 * e.printStackTrace(); }
		 */

		json.put("content", adminService.noticeDetail(nno));
		return json.toString();
	}

	@ResponseBody
	@PostMapping("noticeHide")
	public String noticeHide(@RequestParam("nno") int nno) {
		int result = adminService.noticeHide(nno);
		ObjectNode json = JsonNodeFactory.instance.objectNode();
		json.put("result", result);
		return json.toString();
	}

	@RequestMapping(value = "/multiBoard", method = RequestMethod.GET)
	public String multiboard(Model model) {

		List<Map<String, Object>> list2 = adminService.list2();
		model.addAttribute("list2", list2);
		System.out.println(list2);

		return "/admin/multiBoard";
	}

	// multiboard 2023-08-25 어플리케이션 테스트 수행
	@RequestMapping(value = "/multiBoard", method = RequestMethod.POST)
	public String multiBoard(@RequestParam Map<String, Map> map) {
		System.out.println(map);
		// DB에 저장하기
		int result = adminService.multiBoardInsert(map);
		System.out.println("result" + result);

		return "redirect:/admin/multiBoard";
	}

	@RequestMapping(value = "/member", method = RequestMethod.GET)
	public ModelAndView member() {
		ModelAndView mv = new ModelAndView("/admin/member");
		List<Map<String, Object>> memberList = adminService.memberList();
		mv.addObject("memberList", memberList);
		System.out.println(memberList);
		return mv;
	}

	@GetMapping("/gradeChange")
	public String gradeChange(@RequestParam Map<String, String> map) {
		int result = adminService.gradeChange(map);
		System.out.println(result);
		return "redirect:/admin/member";
	}

	@GetMapping("/post")
	public String post(Model model, @RequestParam(name = "cate", required = false, defaultValue = "0") int cate,
			@RequestParam Map<String, Object> map) {
		// 게시판 번호가 들어옵니다.
		// 게시판 관리번호를 다 불러옵니다.
		if (!(map.containsKey("cate")) || map.get("cate").equals(null) || map.get("cate").equals("")) {
			map.put("cate", 0);
		}
		System.out.println("cate : " + cate);
		System.out.println("검색 : " + map);

		List<Map<String, Object>> boardList = adminService.boardList();
		model.addAttribute("boardList", boardList);

		// 게시글을 다 불러옵니다.
		List<Map<String, Object>> list = adminService.post(map);
		model.addAttribute("list", list);
		// System.out.println(list);

		return "/admin/post";
	}

	@ResponseBody
	@GetMapping("/detail")
	public String openC(@RequestParam Map<String, Object> map) {

		System.out.println(map);

		Map<String, String> content = adminService.openC(map);
		JSONObject json = new JSONObject();
		json.put("content", content.get("mb_content"));
		System.out.println(content);

		return json.toString();
	}

	@GetMapping("/corona")
	public String corona(Model model) throws Exception {
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/1790387/covid19CurrentStatusKorea/covid19CurrentStatusKoreaJason"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
				+ "=gAML2gJL%2FFOsNi0sPuba0Q1CDjxxpOBzdtzoJkPhyIbAKUIHfwaWre8qczwtAmNKI6i4qYrM7n73EFnBuW1Nzg%3D%3D"); /*
																														 * Service
																														 * Key
																														 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		// System.out.println(sb.toString());
		model.addAttribute("corona", sb.toString());

		// String to Json
		ObjectMapper objectMapper = new ObjectMapper(); //
		JsonNode jsonN = objectMapper.readTree(sb.toString());
		//

		System.out.println(jsonN.get("response").get("result").get(0));

		// Json to map
		Map<String, Object> result = objectMapper.readValue(jsonN.get("response").get("result").get(0).toString(),
				new TypeReference<Map<String, Object>>() {// 앞에 값을 뽑아서 map으로 저장하겠습니다.
				});

		System.out.println(result);
		model.addAttribute("result", result);
		return "/admin/corona";
	}

	/*
	 * @GetMapping("/air2") public String air() throws IOException, Exception {
	 * 
	 * StringBuilder urlBuilder = new StringBuilder(
	 * "http://apis.data.go.kr/B552584/ArpltnStatsSvc/getMsrstnAcctoRDyrg"); URL
	 * urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") +
	 * "=gAML2gJL%2FFOsNi0sPuba0Q1CDjxxpOBzdtzoJkPhyIbAKUIHfwaWre8qczwtAmNKI6i4qYrM7n73EFnBuW1Nzg%3D%3D"
	 * ); Service Key
	 * 
	 * urlBuilder.append("&returnType=xml"); xml 또는 json
	 * urlBuilder.append("&numOfRows=100"); 한 페이지 결과 수
	 * urlBuilder.append("&pageNo=1"); 페이지번호
	 * urlBuilder.append("&inqBginDt=20230801"); 조회시작일자
	 * urlBuilder.append("&inqEndDt=20230829"); 조회종료일자
	 * urlBuilder.append("&msrstnName=" + URLEncoder.encode("강남구", "UTF-8")); 측정소명
	 * URL url = new URL(urlBuilder.toString()); HttpURLConnection conn =
	 * (HttpURLConnection) url.openConnection(); conn.setRequestMethod("GET");
	 * conn.setRequestProperty("Content-type", "application/json");
	 * System.out.println("Response code: " + conn.getResponseCode());
	 * BufferedReader rd; if (conn.getResponseCode() >= 200 &&
	 * conn.getResponseCode() <= 300) { rd = new BufferedReader(new
	 * InputStreamReader(conn.getInputStream())); } else { rd = new
	 * BufferedReader(new InputStreamReader(conn.getErrorStream())); } StringBuilder
	 * sb = new StringBuilder(); String line; while ((line = rd.readLine()) != null)
	 * { sb.append(line); } rd.close(); conn.disconnect();
	 * System.out.println(sb.toString());
	 * 
	 * // String to xml DocumentBuilderFactory factory =
	 * DocumentBuilderFactory.newInstance(); DocumentBuilder builder =
	 * factory.newDocumentBuilder(); Document document = builder.parse(new
	 * InputSource(new StringReader(sb.toString())));
	 * 
	 * document.getDocumentElement().normalize(); System.out.println(document);
	 * 
	 * return "admin/air"; }
	 */

	@GetMapping("/air")
	public String air(Model model) throws Exception {
		// String to xml
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse("c:\\temp\\air.xml");

		// document.getDocumentElement().normalize();
		System.out.println(document.getDocumentElement().getNodeName());

		NodeList list = document.getElementsByTagName("item");
		// System.out.println("item length = " + list.getLength());
		// System.out.println(list.toString());
		ArrayList<Map<String, Object>> coronaList = new ArrayList<Map<String, Object>>();
		for (int i = list.getLength() - 1; i >= 0; i--) {
			NodeList childList = list.item(i).getChildNodes();

			Map<String, Object> value = new HashMap<String, Object>();
			for (int j = 0; j < childList.getLength(); j++) {
				Node node = childList.item(j);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					// System.out.println(node.getNodeName());
					// System.out.println(node.getTextContent());
					value.put(node.getNodeName(), node.getTextContent());
				}
			}
			coronaList.add(value);
		}
		System.out.println("xml : " + coronaList);
		model.addAttribute("list", coronaList);

		return "/admin/air";
	}

}
