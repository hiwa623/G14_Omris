<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者メニュー</title>
<link rel="stylesheet" href="assets/css/manager.css">
</head>
<body>
    <div class="container">
        <h1>店長管理画面</h1>
        <p>操作を選択してください</p>
        
        <a href="AdminLineupServlet" class="btn btn-list">登録商品一覧・編集・削除</a>
        <a href="RegisterServlet" class="btn btn-register">新規商品登録</a>
        <a href="SalesDailyServlet" class="btn btn-list">売上確認</a>
        <a href="CategoryControlServlet" class="btn-register">📁 カテゴリー管理</a>
		<a href="OptionControlServlet" class="btn-register">🛠️ オプション管理</a>
        
        <hr>
        <a href="index.jsp" class="btn btn-back">トップに戻る</a>
    </div>
</body>
</html>