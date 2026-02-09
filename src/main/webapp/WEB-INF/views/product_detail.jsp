<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${product.productName}- 詳細</title>
<link rel="stylesheet" href="assets/css/product_detail.css">
</head>
<body>

	<div class="page-container">
		
		<form action="AddtoCartServlet" method="post" class="detail-card">
			<input type="hidden" name="productId" value="${product.productId}">

			<div class="detail-main">
				
				<div class="image-section">
					<c:choose>
						<c:when test="${not empty product.productImageUrl}">
							<img src="${product.productImageUrl}" alt="${product.productName}" class="main-img">
						</c:when>
						<c:otherwise>
							<div class="no-image">No Image</div>
						</c:otherwise>
					</c:choose>
				</div>

				<div class="info-section">
					<div class="product-subtext">${product.productDescription }</div> <h1 class="product-title">${product.productName}</h1>
					<div class="product-price">¥ ${product.price}</div>

					<c:if test="${not empty optionList}">
						<div class="option-section">
							<div class="option-header">オプション（トッピングなど）</div>
							<div class="option-list">
								<c:forEach var="opt" items="${optionList}">
									<label class="option-item">
										<input type="checkbox" name="optionIds" value="${opt.id}">
										<span class="checkmark"></span>
										<span class="option-text">
											<c:out value="${opt.optionName}" />
											<span class="opt-price">(+¥<c:out value="${opt.optionPrice}" />)</span>
										</span>
									</label>
								</c:forEach>
							</div>
						</div>
					</c:if>
				</div>
			</div>

			<div class="detail-footer">
				
				<a href="MenuListServlet" class="btn-cancel">キャンセル</a>

				<div class="qty-selector">
					<button type="button" class="qty-btn" onclick="changeQty(-1)">－</button>
					<span id="qty-display">1</span>
					<button type="button" class="qty-btn" onclick="changeQty(1)">＋</button>
					
					<input type="hidden" name="quantity" id="qty-input" value="1">
				</div>

				<button type="submit" class="btn-add-cart">カートに追加</button>
			</div>

		</form>
	</div>

	<script>
		function changeQty(amount) {
			const qtyInput = document.getElementById('qty-input');
			const qtyDisplay = document.getElementById('qty-display');
			let currentQty = parseInt(qtyInput.value);

			currentQty += amount;

			// 最小値は1、最大値は10（必要に応じて変更）
			if (currentQty < 1) currentQty = 1;
			if (currentQty > 10) currentQty = 10;

			qtyInput.value = currentQty;
			qtyDisplay.textContent = currentQty;
		}
	</script>
</body>
</html>