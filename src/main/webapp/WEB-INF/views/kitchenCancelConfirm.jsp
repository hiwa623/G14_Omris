<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>キッチン｜完了キャンセル確認</title>
<link rel="stylesheet" href="assets/css/kitchenCancelConfirm.css">
</head>
<body>
	<div class="wrap">
		<div class="card">
			<div class="title">変更してよろしいですか</div>
			<div class="btns">
				<a class="btnLink"
					href="${pageContext.request.contextPath}/KitchenCompletedServlet">いいえ</a>
				<form method="post"
					action="${pageContext.request.contextPath}/KitchenCancelCompleteServlet">
					<input type="hidden" name="orderDetailId" value="${orderDetailId}">
					<button class="btn" type="submit">はい</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
