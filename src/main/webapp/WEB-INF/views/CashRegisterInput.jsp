<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>レジ - テーブル選択</title>
<link rel="stylesheet" href="assets/css/CashRegisterInput.css">
</head>
<body>

<div class="card">
    <h2>会計テーブル入力</h2>
    
    <form action="CashRegisterInputServlet" method="post">
        <input type="text" name="table_no" placeholder="C-1" required autofocus>
        
        <button type="submit">次へ進む</button>
    </form>
</div>

</body>
</html>