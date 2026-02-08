<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0"> <title>管理者メニュー</title>
<link rel="stylesheet" href="assets/css/manager.css">
</head>
<body>

    <div class="container">
        <h1>店長管理画面</h1>
        <p class="subtitle">業務を選択してください</p>
        
        <div class="btn-group">
            <h3>商品管理</h3>
            <a href="AdminLineupServlet" class="btn btn-blue">📋 登録商品一覧・編集</a>
            <a href="RegisterServlet" class="btn btn-green">✨ 新規商品登録</a>
        </div>

        <div class="btn-group">
            <h3>売上管理</h3>
            <a href="SalesDailyServlet" class="btn btn-teal">💰 売上確認</a>
        </div>

        <div class="btn-group">
            <h3>マスター設定</h3>
            <a href="CategoryControlServlet" class="btn btn-orange">📁 カテゴリー管理</a>
            <a href="OptionControlServlet" class="btn btn-orange">🛠️ オプション管理</a>
        </div>
        
        <hr>
        
        <a href="index.jsp" class="btn btn-gray">トップに戻る</a>
    </div>

</body>
</html>