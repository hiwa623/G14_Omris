<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カテゴリー管理</title>
<link rel="stylesheet" href="assets/css/admin-category.css">
</head>
<body>
    <div class="container">
        <h1>📁 カテゴリー管理</h1>

        <div class="form-group">
            <form action="CategoryControlServlet" method="post">
                <label>新規カテゴリー名：</label><br>
                <input type="text" name="name" placeholder="例：期間限定メニュー" required>
                <button type="submit">追加</button>
            </form>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>カテゴリー名</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cat" items="${categoryList}">
                    <tr>
                        <%-- ここを修正しました --%>
                        <td>${cat.categoryId}</td>
                        <td>${cat.categoryName}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <a href="ManagerServlet" class="btn-back">管理者メニューに戻る</a>
    </div>
</body>
</html>