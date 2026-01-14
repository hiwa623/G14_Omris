<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>店長管理メニュー</title>
<style>
    /* 少し見やすくするためのスタイル（任意） */
    h1 { color: #333; border-bottom: 2px solid #ffcc00; display: inline-block; }
    ul { line-height: 2; }
    .new-tag { background: #ff4444; color: white; font-size: 0.8em; padding: 2px 5px; border-radius: 3px; margin-left: 5px; }
</style>
</head>
<body>
	<h1>店長管理メニュー</h1>
	<p>行いたい操作を選択してください。</p>

	<ul>
		<li><a href="RegisterServlet">商品の新規登録</a></li>
		<li><a href="AdminLineupServlet">登録商品一覧</a></li>
		<li><a href="OrderHistoryServlet">注文履歴の確認</a><span class="new-tag">NEW</span></li>
		<li><a href="#">売上の確認（未実装）</a></li>
		<li><a href="#">在庫管理（未実装）</a></li>
	</ul>

	<hr>
	<a href="index.jsp">トップに戻る</a>
</body>
</html>