<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>キッチン｜調理完了一覧</title>
<link rel="stylesheet" href="assets/css/kitchenCompleted.css">
<script>
    setInterval(() => location.reload(), 3000);
</script>
</head>
<body>
	<div class="wrap">
		<div class="header">
			<div class="tabs">
				<a class="tab"
					href="${pageContext.request.contextPath}/KitchenServlet">注文料理一覧</a>
				<a class="tab active"
					href="${pageContext.request.contextPath}/KitchenCompletedServlet">完了済み商品</a>
			</div>
			<div class="count">
				完了件数：
				<c:out value="${completedList == null ? 0 : completedList.size()}" />
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
				<div class="toggleBox">完了キャンセル</div>
			</div>

			<c:choose>
				<c:when test="${empty completedList}">
					<div class="empty">完了済み商品はありません</div>
				</c:when>
				<c:otherwise>
					<c:forEach var="item" items="${completedList}">
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
