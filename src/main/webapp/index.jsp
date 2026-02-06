	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>オムライス屋さん - トップ</title>
<script src="https://kit.fontawesome.com/492e7df7e0.js" crossorigin="anonymous"></script>
</head>
<body>
	<h1>オムライス屋さんシステム<i class="fa-solid fa-house"></i><i class="fa-solid fa-circle-plus"></i></h1>

    <div class="menu-box">
        <h2>お客様はこちら</h2>
        <a href="StartOrderServlet">商品一覧を見る（注文する）</a>
    </div>

    <div class="menu-box manager">
        <h2>店長・管理者はこちら</h2>
        <a href="ManagerServlet">管理画面へ</a>
    </div>

    <div class="menu-box">
        <h2>開発中</h2>
        <a href="KitchenServlet">キッチン画面</a>
        <a href="HallBeforeServlet">ホール画面</a>
        <a href="CashRegisterInputServlet">会計ごー！</a>
    </div>
    <div>
    	<h2>ログイン画面</h2>
    	<a href="#">ログイン画面単体</a>
    </div>
    <div></div>
</body>
</html>