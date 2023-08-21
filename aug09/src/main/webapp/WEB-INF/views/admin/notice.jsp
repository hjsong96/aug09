<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hadine || admin || notice</title>
<link rel="stylesheet"
	href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<link rel="stylesheet" href="../css/admin.css">
<style type="text/css">
.notice-write-form{
	width: 95%;
	height: auto;
	margin: 10px;
	padding: 20px;
	box-sizing: border-box;
}

.notice-write-form input{
	height: 30px;
	width: 100%;
}

.notice-write-form textarea{
	width: 100%;
	height: 300px;
	margin: 5px 0px;
}

.notice-write-form button{
	width: 100px;
	height: 50px;
}


</style>
</head>
<body>
	<div class="container">
		<%@ include file="menu.jsp" %>
		<div class="main">
			<div class="article">
				<h1>공지사항</h1>
				<table>
					<tr>
						<td class="col-1">번호</td>
						<td class="col-3">제목</td>
						<td class="col-2">게시일</td>
						<td class="col-2">글쓴이</td>
						<td class="col-1">삭제여부</td>
						<td class="col-1">파일여부</td>
						</tr>
					<c:forEach items="${list }" var="row">
						<tr>
							<td class="col-1">${row.nno }</td>
							<td class="col-3">${row.ntitle }<c:if test="${row.commentcount ne 0}">&nbsp;<span class="badge bg-secondary">${row.commentcount }</span></c:if></td>
							<td class="col-2">${row.ndate }</td>
							<td class="col-2">${row.m_no }</td>
							<td class="col-1">
							<c:choose>
								<c:when test="${row.ndel eq 1}"><i class="xi-eye"></i>보여짐</c:when>
								<c:otherwise><i class="xi-eye-off"></i></c:otherwise>
							</c:choose>
							<td class="col-1"><c:if test="${row.norifile ne null }"><i class="xi-file-add"></i></c:if></td>
						</tr>
					</c:forEach>
				</table>
				<div class="notice-write-form">
					<form action="./noticeWrite" method="post" enctype="multipart/form-data">
						<input type="text" name="title">
						<textarea name="content"></textarea>
						<input type="file" name="upFile">
						<button type="submit">글쓰기</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>