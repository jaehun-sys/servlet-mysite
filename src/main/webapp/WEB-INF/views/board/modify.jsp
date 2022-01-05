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
				<form class="board-form" method="post" action="/mysite/board">
				<input type="hidden" name ="a" value="modify">
					<table class="tbl-ex">
					<c:if test="${boardVo != null }">
					<input type="hidden" name ="no" value="${boardVo.no }">
					<script>
					console.log(${board.no});
					</script>
						<tr>
							<th colspan="2">글수정</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value="${boardVo.title }"></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="content" name="content" >${boardVo.content }</textarea>
								<!-- 
								<textarea id="content" name="content" >수정해야 할 글은 고대로 
								이렇게 textarea에 뿌려야 합니다.
								개행문자 변경도 하지마세요.
								하하하하하
								즐건 코딩 되세요~~~~</textarea>
								 -->
							</td>
						</tr>
					</c:if>
					</table>
					<div class="bottom">
						<a href="javascript:history.back()">취소</a>
						<input type="submit" value="수정">
					</div>
				</form>				
			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	</div>
</body>
</html>