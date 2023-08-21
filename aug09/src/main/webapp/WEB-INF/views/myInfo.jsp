<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> ❤ 사용자 정보</title>
<link href="css/styles.css" rel="stylesheet" />
<script src="./js/jquery-3.7.0.min.js"></script>

</head>
<body>
	<%@ include file="menu.jsp"%>
	<!-- Masthead-->
	<header class="masthead">
		<div class="container">
		<h1>사용자 정보 보기</h1>
		이름 : ${my.m_name }<br>
		i d : ${my.m_id }<br>
		가입일 : ${my.m_joindate }<br>
		주 소 : ${my.m_addr }<br>
		등 급: ${my.m_grade }<br>
		mbti : ${my.m_mbti }<br>
		성 별 :${my.m_gender }<br>
		</div>
	</header>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="js/scripts.js"></script>
<script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
</body>
</html>