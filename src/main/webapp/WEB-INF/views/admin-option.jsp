<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>オプション管理</title>
<link rel="stylesheet" href="assets/css/admin-option.css">
</head>
<body>
    <div class="container">
        <h1>🛠️ オプション管理</h1>

        <div class="form-group">
            <form action="OptionControlServlet" method="post">
                <label>名称：</label>
                <input type="text" name="name" placeholder="例：ご飯大盛り" required>
                
                <label>価格(円)：</label>
                <input type="number" name="price" value="0" min="0" required>
                
                <button type="submit">追加</button>
            </form>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>オプション名</th>
                    <th>価格</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="opt" items="${optionList}">
                    <tr>
                        <td>${opt.id}</td>
                        <td>${opt.optionName}</td>
                        <td>¥ ${opt.optionPrice}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <a href="ManagerServlet" class="btn-back">管理者メニューに戻る</a>
    </div>
</body>
</html>