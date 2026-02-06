<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>メニュー一覧</title>
<link rel="stylesheet" href="assets/css/menu_list.css">
<script src="assets/js/menu_list.js"></script>
</head>
<body>

	<div class="app-container">

		<aside class="sidebar">
			<a href="javascript:void(0);" class="category-btn active"
				onclick="filterCategory('all', this)">すべて</a>

			<c:forEach var="cat" items="${viewModel.categoryList}">
				<a href="javascript:void(0);" class="category-btn"
					onclick="filterCategory('${cat.categoryId}', this)">
					${cat.categoryName} </a>
			</c:forEach>
		</aside>

		<main class="main-content">
			
			<div class="top-header">
				<div class="top-buttons">
					<a href="OrderHistoryServlet" class="btn btn-history">注文履歴</a>
					<a href="CheckoutConfirmServlet" class="btn btn-checkout">お会計</a>
				</div>
			</div>

			<div class="scrollable-product-list">
				<div class="menu-grid">
					<c:forEach var="product" items="${viewModel.productList}">
						<div class="product-card" data-category-id="${product.categoryId}">
							<div class="image-area">
								<c:choose>
									<c:when test="${not empty product.productImageUrl}">
										<img src="${product.productImageUrl}" class="product-image" alt="${product.productName}">
									</c:when>
									<c:otherwise>
										<div class="no-image">No Image</div>
									</c:otherwise>
								</c:choose>
							</div>

							<div class="product-info">
								<div class="product-name">${product.productName}</div>
								<div class="product-price">¥ ${product.price}</div>
								<a href="ProductDetailServlet?id=${product.productId}" class="add-btn-circle">＋</a>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>

			<div class="floating-cart-area">
				<a href="CartServlet" class="btn-cart-large">カートに進む</a>
			</div>

		</main>
	</div>

</body>
</html>