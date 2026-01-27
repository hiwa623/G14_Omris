<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>オプション管理</title>
<style>
    body { font-family: sans-serif; padding: 20px; background: #f4f6f9; }
    .container { max-width: 700px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; }
    .form-group { margin-bottom: 20px; padding: 15px; background: #eee; border-radius: 5px; }
    input[type="text"], input[type="number"] { padding: 8px; margin-right: 10px; }
    button { padding: 8px 15px; background: #8e44ad; color: white; border: none; cursor: pointer; }
    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
    th, td { padding: 10px; border-bottom: 1px solid #ddd; }
    th { background: #f8f9fa; text-align: left; }
    .btn-back { display: block; margin-top: 20px; text-decoration: none; color: #555; }
</style>
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