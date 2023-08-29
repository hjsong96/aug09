<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hadine || admin || postDIV</title>
<link rel="stylesheet"
	href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<link rel="stylesheet"
	href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<link rel="stylesheet" href="../css/admin.css">
<link href="../css/multiboard2.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.7.0.min.js"
	integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g="
	crossorigin="anonymous"></script>

<script type="text/javascript">
	$(function() {
		$(".title")
				.click(
						function() {
							let mbno = $(this).siblings(".mbno").text()
							let contentBoxL = $("."+mbno);
							//alert(contentBoxL.find(".contentBox").length);

							// 이미 콘텐츠 박스에 버튼이 있는지 확인
							if (contentBoxL.find(".contentBox").length == 0) {
								$
										.ajax({
											url : "./openC",
											type : "get",
											data : {
												mbno : mbno
											},
											dataType : "json",
											success : function(data) {
												let contentBox = '<div class="contentBox">';
												contentBox += '<div class="close">'+data.content+'</div>'
												contentBox += '<button class="closeBt">닫기</button>'
												contentBox += '</div>'
												contentBoxL.append(contentBox);
											},
											error : function(error) {
												alert("에러가 발생했습니다.") + error
											}
										});
							}
						});

		$(document).on("click", ".closeBt", function() {
			let contentBox = $(this).parent();
			contentBox.remove();
		});
	});
</script>

<style type="text/css">
.please {
	display: block;
	width: 100%;
	color: red;
}

.input-form {
	margin: 0 auto;
	margin-top: 10px;
	width: 90%;
	height: 30px;
	line-height: 30px;
	padding: 5px;
	background-color: #ffc800;
}

.input-form input, .input-form button {
	width: 19%;
	border: 0px;
	margin: 0;
	padding: 0;
	height: 30px;
	vertical-align: middle;
}

.gray {
	background-color: #BDBDBD;
}

.dynamic-p {
	width: 100%;
}

</style>


</head>
<body>
	<div class="container">
		<%@ include file="menu.jsp"%>
		<div class="main">
			<div class="article">
				<h1>게시글 관리 ${list[0].count} 개의 글 있음</h1>
				<div class="boardlist">
					<button onclick="location.href='./post?cate=0'">전체보기</button>
					<c:forEach items="${boardList }" var="b">
						<button onclick="location.href='./post?cate=${b.mb_cate }'">${b.b_catename }
						</button>
					</c:forEach>
					<form action="./post" method="get">
						<select name="searchN">
							<option value="title">제목</option>
							<option value="content">내용</option>
							<option value="nick">글쓴이</option>
							<option value="id">ID</option>
						</select> <input action="text" name="searchV" required="required">
						<input type="hidden" name="cate" value="${param.cate }">
						<button type="submit">검색</button>
					</form>
				</div>
				<div class="div-table">
					<div class="div-row table-head">
						<div class="div-cell table-head">번호</div>
						<div class="div-cell table-head">카테고리</div>
						<div class="div-cell table-head">제목</div>
						<div class="div-cell table-head">글쓴이</div>
						<div class="div-cell table-head">날짜</div>
						<div class="div-cell table-head">읽음수</div>
						<div class="div-cell table-head">삭제</div>
					</div>
					<c:forEach items="${list }" var="row">
						<div class="div-row <c:if test="${row.mb_del ne 1}">gray</c:if>">
							<div class="div-cell mbno">${row.mb_no }</div>
							<div class="div-cell">${row.b_catename }</div>
							<div class="div-cell title">${row.mb_title }</div>
							<div class="div-cell">${row.m_name }</div>
							<div class="div-cell">${row.mb_date }</div>
							<div class="div-cell">${row.mb_read }</div>
							<div class="div-cell mbdel">${row.mb_del }</div>
						</div>
							<span class="${row.mb_no }"></span>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
</html>