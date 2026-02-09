<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>お会計確認</title>
<link rel="stylesheet" href="assets/css/checkout_confirm.css">
<script src="assets/js/checkout_confirm.js"></script>
</head>
<body>

	<div class="container">
		<h1>お会計確認</h1>

		<div class="table-info">
			<div class="table-label">Table No.</div>
			<%-- セッションからテーブルIDを表示 --%>
			<div class="table-no">${tableNo}</div>
		</div>

		<div class="total-section">
			<div class="total-label">お支払い合計</div>
			<div class="total-price">
				¥
				<fmt:formatNumber value="${grandTotal}" />
			</div>
		</div>

		<p class="note">
			上記金額をレジにてお支払いください。<br> この画面をスタッフにご提示いただくとスムーズです。
		</p>

		<%-- 1分後に自動で画面が切り替わる旨を表示しておくと親切です --%>
		<p style="font-size: 0.8rem; color: #999; margin-top: 50px;">
			※この画面は1分後に自動的にトップへ戻ります</p>
	</div>

</body>
</html>