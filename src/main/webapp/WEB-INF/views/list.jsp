<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<h2>게시판 리스트</h2>
		<hr width="400" color="red" />

		<table border="1" cellpadding="0" cellspacing="0" width="500">
			<tr>
				<td>번호</td>
				<td>제목</td>
				<td>작성자</td>
				<td>날짜</td>
				<td>조회수</td>
			</tr>
			<!-- 게시글 목록 가져오기 -->
			<c:forEach items="${list}" var="vo">
				<tr>
					<td>${vo.bNo}</td>
					<td>${vo.bSubject}</td>
					<td>${vo.bTitle}</td>
					<td>${vo.bName}</td>
					<td>${vo.bHit}</td>
				</tr>
			</c:forEach>
			<tr>
				<td colpsan="5"><a href="writeForm">글쓰기</a></td>
			</tr>
		</table>
	</div>
</body>
</html>