<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者メニュー</title>
<style>
    body { font-family: sans-serif; padding: 50px; text-align: center; background: #f0f0f0; }
    .container { max-width: 500px; margin: 0 auto; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
    .btn { display: block; width: 100%; padding: 15px; margin: 10px 0; text-decoration: none; color: white; border-radius: 5px; font-size: 1.2rem; box-sizing: border-box; }
    .btn-list { background: #3498db; }
    .btn-register { background: #2ecc71; }
    .btn-back { background: #95a5a6; }
</style>
</head>
<body>
    <div class="container">
        <h1>店長管理画面</h1>
        <p>操作を選択してください</p>
        
        <a href="AdminLineupServlet" class="btn btn-list">登録商品一覧・編集・削除</a>
        <a href="RegisterServlet" class="btn btn-register">新規商品登録</a>
        <a href="SalesServlet" class="btn btn-list">売上確認</a>
        
        <hr>
        <a href="index.jsp" class="btn btn-back">トップに戻る</a>
    </div>
</body>
</html>