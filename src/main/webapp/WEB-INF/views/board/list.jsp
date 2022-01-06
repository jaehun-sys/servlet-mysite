<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<!DOCTYPE html>
<html>
<head>
	<title>mysite</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" placeholder="글 제목을 입력하세요"><!-- 검색 -->
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
					<c:forEach items="${list}" var="vo">
						<tr>
							<td>${vo.no }</td>
							<td><a href="/mysite/board?a=read&no=${vo.no }">${vo.title }</a></td>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.regdate }</td>
							<c:if test="${authUser.no == vo.userno }">
							<td><a href="/mysite/board?a=delete&no=${vo.no }" class="del">삭제</a></td>
							</c:if>
						</tr>
						<!--  
						<tr>
							<td colspan=4>
							${fn:replace(vo.content, newLine, "<br>")}
							</td>
						</tr>
						-->
					</c:forEach>
				</table>
				
				<div class="pager">
					<ul>
					<c:if test="${paging.prev}">
						<li><a href="/mysite/board?a=list&page=${paging.beginPage-1}">◀</a></li>
					</c:if>
					<c:forEach begin="${paging.beginPage}" end="${paging.endPage}" step="1" var="index">
						<c:choose>
							<c:when test="${paging.page==index }">
							<li class="selected">${index}</li>
							</c:when>
							<c:otherwise>
							<li><a href="/mysite/board?a=list&page=${index}">${index}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${paging.next}">
						<li><a href="/mysite/board?a=list&page=${paging.endPage+1}">▶</a></li>
					</c:if>
					</ul>
				</div>			
				
				<c:choose>
					<c:when test="${authUser ne null}">
						<div class="bottom">
							<a href="/mysite/board?a=writeform" id="new-book">글쓰기</a>
						</div>	
					</c:when>
					<c:otherwise>
						<!--
						<div class="bottom">
							 <a href="" id="new-book">훼이크</a>
						</div>	
						 -->
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	</div>
</body>
</html>