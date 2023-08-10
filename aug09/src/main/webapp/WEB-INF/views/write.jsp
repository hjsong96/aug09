<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<script src="./js/jquery-3.7.0.min.js"></script>
<link href="css/styles.css" rel="stylesheet" />

<script type="text/javascript" src="./js/HuskyEZCreator.js" charset="utf-8"></script>

<style type="text/css">
	.write-form {
		background-color: white;
		padding: 10px;
		text-align: left;
		color: black;
	}
	.write-form textarea {
			width: 99.5%;
	}
}
</style>
</head>
<body>
	<%@ include file="menu.jsp"%>
	<!-- Masthead-->
	<header class="masthead">
		<div class="container">
			<div class="rounded-3 write-form">
			<form action="./write" method="post">
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">제목</span>
					</div>
					<input type="text" class="form-control" name="title">
				</div>
				<textarea id="editorTxt" name="content"></textarea>
				<button type="button" class="btn btn-primary">저장</button>
			</form>
			</div>
		</div>
	</header>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="js/scripts.js"></script>
<script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>

<script type="text/javascript">
var oEditor = []
smartEditor = function(){
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditor,
		elPlaceHolder : "editorTxt",
		sSkinURI : "SmartEditor2Skin.html",
		fCreator: "createSEditor2"
		
	});
};
$(function(){
	smartEditor();
});
</script>

</body>
</html>