<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カテゴリー管理</title>
<style>
    body { font-family: sans-serif; padding: 20px; background: #f4f6f9; }
    .container { max-width: 600px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; }
    .form-group { margin-bottom: 20px; padding: 15px; background: #eee; border-radius: 5px; }
    input[type="text"] { padding: 8px; width: 70%; }
    button { padding: 8px 15px; background: #27ae60; color: white; border: none; cursor: pointer; }
    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
    th, td { padding: 10px; border-bottom: 1px solid #ddd; }
    th { background: #f8f9fa; text-align: left; }
    .btn-back { display: block; margin-top: 20px; text-decoration: none; color: #555; }
</style>
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