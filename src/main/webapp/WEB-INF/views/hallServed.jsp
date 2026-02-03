<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ホール｜提供済み一覧</title>
<link rel="stylesheet" href="assets/css/hallServed.css">
<script>
    setInterval(() => location.reload(), 3000);
</script>
</head>

<body>
	<div class="wrap">

		<div class="header">
			<div class="tabs">
				<a class="tab"
					href="${pageContext.request.contextPath}/HallBeforeServlet">提供前一覧</a>
				<a class="tab active"
					href="${pageContext.request.contextPath}/HallServedServlet">完了済み商品</a>
			</div>

			<div class="count">
				提供済み件数（30分以内）：
				<c:out value="${servedList == null ? 0 : servedList.size()}" />
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
				<div class="center">注文数</div>
				<div class="center">席番号</div>
				<div class="toggleBox">完了キャンセル</div>
			</div>

			<c:choose>
				<c:when test="${empty servedList}">
					<div class="empty">提供済みの商品はありません（30分以内）</div>
				</c:when>

				<c:otherwise>
					<c:forEach var="item" items="${servedList}">
						<div class="row">
							<div>
								<c:out value="${item.productName}" />
							</div>
							<div class="center">
								<c:out value="${item.quantityText}" />
							</div>
							<div class="center">
								<c:out value="${item.tableNo}" />
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
