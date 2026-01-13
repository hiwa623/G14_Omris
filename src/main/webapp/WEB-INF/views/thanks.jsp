<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>注文完了</title>
<body>
	<c:choose>
        <c:when test="${vm.success}">
            <h1>${vm.message}</h1>
            <p>お買い上げありがとうございます。商品の到着をお待ちください。</p>
        </c:when>
        <c:otherwise>
            <h1>注文エラー</h1>
            <p style="color:red;">${vm.message}</p>
        </c:otherwise>
    </c:choose>
    
    <br>
    <a href="LineupServlet">商品一覧に戻る</a>
</body>
</html>