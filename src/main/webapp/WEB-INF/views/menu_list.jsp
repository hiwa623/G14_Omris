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
	<header>
		<a href="javascript:void(0);" class="category-btn active"
			onclick="filterCategory('all', this)">すべて</a>

		<c:forEach var="cat" items="${viewModel.categoryList}">
			<a href="javascript:void(0);" class="category-btn"
				onclick="filterCategory('${cat.categoryId}', this)">
				${cat.categoryName} </a>
		</c:forEach>
	</header>

	<div class="menu-container">
		<c:forEach var="product" items="${viewModel.productList}">

			<div class="product-card" data-category-id="${product.categoryId}">
				<c:choose>
					<c:when test="${not empty product.productImageUrl}">
						<img src="${product.productImageUrl}" class="product-image"
							alt="${product.productName}">
					</c:when>
					<c:otherwise>
						<div class="product-image"
							style="display: flex; align-items: center; justify-content: center; color: #aaa;">No
							Image</div>
					</c:otherwise>
				</c:choose>

				<div class="product-info">
					<div>
						<div class="product-name">${product.productName}</div>
						<div class="product-desc">${product.productDescription}</div>
					</div>
					<div class="product-price">¥ ${product.price}</div>

					<a href="ProductDetailServlet?id=${product.productId}"
						class="add-btn"
						style="text-align: center; text-decoration: none; display: block;">
						商品詳細・オプション選択 </a>
				</div>
			</div>

		</c:forEach>
	</div>

	<div class="footer-bar">
		<a href="CartServlet" class="cart-btn">注文確認へ進む</a>
	</div>
	<div style="text-align: right; margin-bottom: 10px;">
		<a href="OrderHistoryServlet"
			style="background: #3498db; color: white; padding: 8px 15px; text-decoration: none; border-radius: 5px; font-size: 0.9rem;">
			注文履歴を見る </a>
		<div style="margin-top: 20px;">
			<a href="CheckoutConfirmServlet"
				style="display: block; background: #e67e22; color: white; padding: 15px; text-align: center; text-decoration: none; border-radius: 5px; font-weight: bold;">
				お会計へ進む </a>
		</div>
	</div>

</body>
</html>