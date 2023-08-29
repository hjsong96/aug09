<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="menu">
			<div class="menu-logo" onclick="url('main')"><img alt="없음" src="../img/logo_cc.png"></div>
			<div class="menu-item" onclick="url('main')"><i class="xi-home-o xi-2x"></i>홈으로</div>
			<div class="menu-item" onclick="url('multiBoard')"><i class="xi-layout-o xi-2x"></i>게시판관리</div>
			<div class="menu-item" onclick="url('post?cate=0')"><i class="xi-document xi-2x"></i>게시글관리</div>
			<div class="menu-item" onclick="url('member')"><i class="xi-group xi-2x"></i>회원관리</div>
			<div class="menu-item" onclick="url('air')"><i class="xi-comment-o xi-2x"></i>공기질 관리</div>
			<div class="menu-item" onclick="url('corona')"><i class="xi-message-o xi-2x"></i>코로나</div>
			<div class="menu-item" onclick="url('mail')"><i class="xi-mail-o xi-2x"></i>메일보내기</div>
			<div class="menu-item" onclick="url('notice')"><i class="xi-info-o xi-2x"></i>공지사항</div>
			<div class="menu-item" onclick="url('logout')"><i class="xi-log-out xi-2x"></i>로그아웃</div>
		</div>
		<script type="text/javascript">function url(url) {location.href = "./"+url;};</script>