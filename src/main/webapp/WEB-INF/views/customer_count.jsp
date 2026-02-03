<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>人数入力</title>
<link rel="stylesheet" href="assets/css/customer_count.css">
<script src="assets/js/customer_count.js"></script>
</head>
<body>
	<div style="text-align: right; margin-top: 20px; font-size: 0.8rem;">
		現在設定中のテーブル:
		<%=session.getAttribute("tableId")%>番<br> <a
			href="StartOrderServlet?action=reset" style="color: gray;">[設定を解除してテーブル番号を変更]</a>
	</div>
	<div class="container">
		<h1>人数入力</h1>
		<p>
			Table No.
			<%=session.getAttribute("tableId")%></p>
		<p>ご利用人数を入力してください</p>

		<form action="ConfirmCountServlet" method="post">
			<div class="counter-wrapper">
				<button type="button" class="btn-count minus"
					onclick="updateCount(-1)">－</button>

				<input type="text" id="countInput" name="customerCount"
					class="count-display" value="1" readonly>

				<button type="button" class="btn-count plus"
					onclick="updateCount(1)">＋</button>
			</div>

			<button type="submit" class="btn-submit">メニューを見る</button>
		</form>
	</div>

</body>
</html>