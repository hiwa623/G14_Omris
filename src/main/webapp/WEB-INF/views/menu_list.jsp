<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>メニュー一覧</title>

<style>
/* タブレット風の簡易スタイル */
body {
	font-family: "Helvetica Neue", Arial, sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f5f5f5;
	display: flex;
	flex-direction: column;
	height: 100vh;
}

/* ヘッダー（カテゴリバー） */
header {
	background-color: #333;
	color: white;
	padding: 10px;
	overflow-x: auto; /* 横スクロール可能に */
	white-space: nowrap;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.category-btn {
	display: inline-block;
	padding: 15px 25px;
	margin-right: 10px;
	background-color: #555;
	color: white;
	text-decoration: none;
	border-radius: 5px;
	font-size: 1.2rem;
	cursor: pointer;
}

.category-btn.active {
	background-color: #d35400; /* 選択中の色 */
}

/* メインエリア（商品一覧） */
.menu-container {
	flex: 1;
	padding: 20px;
	overflow-y: auto;
	display: grid;
	grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
	/* レスポンシブグリッド */
	gap: 20px;
}

/* 商品カード */
.product-card {
	background: white;
	border-radius: 10px;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
	overflow: hidden;
	display: flex;
	flex-direction: column;
	transition: transform 0.2s;
}

.product-card:active {
	transform: scale(0.98); /* タップ時の凹み */
}

.product-image {
	width: 100%;
	height: 150px;
	object-fit: cover;
	background-color: #eee;
}

.product-info {
	padding: 15px;
	flex: 1;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
}

.product-name {
	font-weight: bold;
	font-size: 1.1rem;
	margin-bottom: 5px;
}

.product-desc {
	font-size: 0.8rem;
	color: #666;
	margin-bottom: 10px;
}

.product-price {
	color: #d35400;
	font-weight: bold;
	font-size: 1.2rem;
	text-align: right;
}

/* カートボタンなど（下部固定などにする場合） */
.footer-bar {
	background: white;
	padding: 15px;
	text-align: right;
	border-top: 1px solid #ddd;
}

.cart-btn {
	background-color: #27ae60;
	color: white;
	padding: 15px 30px;
	text-decoration: none;
	border-radius: 5px;
	font-size: 1.2rem;
	font-weight: bold;
}

/* ボタンの見た目をしたsubmitボタン */
.add-btn {
	width: 100%;
	padding: 10px;
	background-color: #e67e22;
	color: white;
	border: none;
	border-radius: 5px;
	font-size: 1rem;
	cursor: pointer;
	margin-top: 10px;
}
</style>

</head>
<body>
	<header>
		<c:forEach var="cat" items="${viewModel.categoryList}">
			<a href="#cat-${cat.categoryId}" class="category-btn">${cat.categoryName}</a>
		</c:forEach>
	</header>

	<div class="menu-container">
		<c:forEach var="product" items="${viewModel.productList}">

			<div class="product-card" id="cat-${product.categoryId}">
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

					<a href="ProductDetailServlet?productId=${product.productId}"
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
    <a href="OrderHistoryServlet" style="background:#3498db; color:white; padding:8px 15px; text-decoration:none; border-radius:5px; font-size:0.9rem;">
        注文履歴を見る
    </a>
</div>
</body>
</html>