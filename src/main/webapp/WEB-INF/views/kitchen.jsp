<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>キッチン｜注文料理一覧</title>
<link rel="stylesheet" href="assets/css/kitchen.css">
<script src="https://kit.fontawesome.com/492e7df7e0.js" crossorigin="anonymous"></script>
<script>
    setInterval(() => location.reload(), 3000);
</script>
</head>
<body>
	<div class="wrap">
		<div class="header">
			<div class="tabs">
				<a class="tab active"
					href="${pageContext.request.contextPath}/KitchenServlet">注文料理一覧</a>
				<a class="tab"
					href="${pageContext.request.contextPath}/KitchenCompletedServlet">完了済み商品</a>
			</div>
			<div class="count">
				注文件数：
				<c:out value="${kitchenList == null ? 0 : kitchenList.size()}" />
				件
			</div>
		</div>

		<c:if test="${not empty error}">
			<div class="error">
				<c:out value="${error}" />
			</div>
		</c:if>

		<div class="table">
			<div class="row head">
				<div>商品名</div>
				<div class="qty">注文数</div>
				<div class="toggleBox">調理完了チェック</div>
			</div>

			<c:choose>
				<c:when test="${empty kitchenList}">
					<div class="empty">注文料理はありません</div>
				</c:when>
				<c:otherwise>
					<c:forEach var="item" items="${kitchenList}">
						<div class="row">
							<div>
								<c:out value="${item.productName}" />
							</div>
							<div class="qty">
								<c:out value="${item.quantityText}" />
							</div>
							<div class="toggleBox">
								<a class="toggleLink" href="${item.toggleUrl}"></a>
							</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>
