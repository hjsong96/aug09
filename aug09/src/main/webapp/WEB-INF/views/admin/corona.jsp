<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hadine || admin || corona</title>
<link rel="stylesheet"
	href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<link rel="stylesheet" href="../css/admin.css">
<style type="text/css">
</style>
</head>
<body>
	<div class="container">
		<%@ include file="menu.jsp"%>
		<div class="main">
			<div class="article">
				<h1>Corona 일일 내역</h1>
				<div>
					<table border="1">
						<tr>
							<td>데이터 조회 기준 일시</td>
							<td>${result.mmddhh }</td>
						</tr>
						<tr>
							<td>일일 사망</td>
							<td>${result.cnt_deaths }</td>
						</tr>
						<tr>
							<td>일일 신규입원</td>
							<td>${result.cnt_hospitalizations }</td>
						</tr>
						<tr>
							<td>일일 확진</td>
							<td>${result.cnt_confirmations }</td>
						</tr>
					</table>

<%-- 					<table border="1">
						<tr>
							<td>데이터 조회 기준 일시</td>
							<td class="mmddhh">${result.mmddhh }</td>
						</tr>
						<tr>
							<td>일일 사망</td>
							<td class="cnt_deaths">${result.cnt_deaths }</td>
						</tr>
						<tr>
							<td>일일 신규입원</td>
							<td class="cnt_hospitalizations">${result.cnt_hospitalizations }</td>
						</tr>
						<tr>
							<td>일일 확진</td>
							<td class="cnt_confirmations">${result.cnt_confirmations }</td>
						</tr>
					</table> --%>

				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		let corona = $
		{
			corona
		};
		document.querySelector(".mmddhh").innerText = corona.response.result[0].mmddhh;
		document.querySelector(".cnt_deaths").innerText = corona.response.result[0].cnt_deaths;
		document.querySelector(".cnt_hospitalizations").innerText = corona.response.result[0].cnt_hospitalizations;
		document.querySelector(".cnt_confirmations").innerText = corona.response.result[0].cnt_confirmations;
	</script>
</body>
</html>