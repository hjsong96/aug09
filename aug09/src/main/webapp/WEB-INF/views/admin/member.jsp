<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hadine || admin || main</title>
<link rel="stylesheet"
	href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<link rel="stylesheet" href="../css/admin.css">
<link rel="stylesheet" href="../css/member.css">
<link rel="stylesheet" href="../css/multiboard.css">
<style type="text/css">
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
	background-color : #BDBDBD;
}

.yellow{
	background-color: #FFECB3;
}

</style>
<script type="text/javascript">
	function gradeCh(mno, name) {
		//alert(mno + name + "님을 변경하시겠습니까?");
		let select = document.getElementById(mno);
		let selectName = select.options[select.selectedIndex].text;
		let selectValue = select.options[select.selectedIndex].value;
		//alert(value);
		if(confirm(name + " 님을 " + selectName + " 등급으로 변경하시겠습니까?")) {
			location.href="./gradeChange?mno="+mno+"&grade="+selectValue;
		}
	}
</script>
</head>
<body>
	<div class="container">
		<%@ include file="menu.jsp"%>
		<div class="main">
			<div class="article">
				<h1>회원관리</h1>
				<div class="div-table">
					<div class="div-row table-head">
						<div class="div-cell table-head">번호</div>
						<div class="div-cell table-head">아이디</div>
						<div class="div-cell table-head">이름</div>
						<div class="div-cell table-head">가입일</div>
						<div class="div-cell table-head">주소</div>
						<div class="div-cell table-head">생년월일</div>
						<div class="div-cell table-head">mbti</div>
						<div class="div-cell table-head">성별</div>
						<div class="div-cell table-head">등급</div>
					</div>
					<c:forEach items="${memberList }" var="row">
						<div class="div-row <c:if test="${row.m_grade lt 5}">gray</c:if><c:if test="${row.m_grade gt 5}">yellow</c:if>">
							<div class="div-cell">${row.m_no }</div>
							<div class="div-cell">${row.m_id }</div>
							<div class="div-cell">${row.m_name }</div>
							<div class="div-cell">${row.m_joindate }</div>
							<div class="div-cell">${row.m_addr }</div>
							<div class="div-cell">${row.m_birth }</div>
							<div class="div-cell">${row.m_mbti }</div>
							<div class="div-cell">
								<c:choose>
									<c:when test="${row.m_gender eq 0}">
										<i class="xi-woman xi-1x" style="color: blue"></i>
									</c:when>
									<c:otherwise>
										<i class="xi-man xi-1x" style="color: red"></i>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="div-cell">
							<select id="${row.m_no }" onchange="gradeCh(${row.m_no }, '${row.m_name }')">
								<optgroup label="로그인 불가">
									<option value="0" <c:if test="${row.m_grade eq 0 }">selected="selected"</c:if>>강퇴</option>
									<option value="1" <c:if test="${row.m_grade eq 1 }">selected="selected"</c:if>>탈퇴</option>
									<option value="2" <c:if test="${row.m_grade eq 2 }">selected="selected"</c:if>>징역</option>
									<option value="3" <c:if test="${row.m_grade eq 3 }">selected="selected"</c:if>>임시정지</option>
									<option value="4" <c:if test="${row.m_grade eq 4 }">selected="selected"</c:if>>휴면</option>
								</optgroup>
								<optgroup label="회원">
									<option value="5" <c:if test="${row.m_grade eq 5 }">selected="selected"</c:if>>일반회원</option>
								</optgroup>
								<optgroup label="관리자들">
									<option value="6" <c:if test="${row.m_grade eq 6 }">selected="selected"</c:if>>일반관리자</option>
									<option value="7" <c:if test="${row.m_grade eq 7 }">selected="selected"</c:if>>게시판관리자</option>
									<option value="8" <c:if test="${row.m_grade eq 8 }">selected="selected"</c:if>>가입관리자</option>
									<option value="9" <c:if test="${row.m_grade eq 9 }">selected="selected"</c:if>>최고관리자</option>
								</optgroup>
							</select>
							</div>
						</div>
					</c:forEach>
				</div>
				<div class="input-form">
					<form action="./multiBoard" method="post">
						<input type="number" name="cateNum" required="required"
							placeholder="게시판 번호 입력"> <input type="text" name="name"
							required="required" placeholder="게시판 이름 입력"> <input
							type="text" name="comment" required="required"
							placeholder="참고를 남겨주세요">
						<button type="submit">저장</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>