<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ホール｜変更確定</title>
<link rel="stylesheet" href="assets/css/hallServeConfirm.css">
</head>

<body>
	<div class="wrap">
		<div class="card">
			<div class="title">変更してよろしいですか</div>
			<div class="btns">
				<a class="btnLink"
					href="${pageContext.request.contextPath}/HallBeforeServlet">いいえ</a>
				<form method="post"
					action="${pageContext.request.contextPath}/HallServeCompleteServlet">
					<input type="hidden" name="orderDetailId" value="${orderDetailId}">
					<button class="btn" type="submit">はい</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
