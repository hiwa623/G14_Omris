<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${product.productName}-詳細</title>
<style>
/* 簡易スタイル */
body {
	font-family: sans-serif;
	background: #f5f5f5;
	padding: 20px;
}

.detail-card {
	background: white;
	padding: 20px;
	border-radius: 10px;
	max-width: 600px;
	margin: 0 auto;
}

img {
	width: 100%;
	height: auto;
	border-radius: 10px;
}

.price {
	color: #d35400;
	font-size: 1.5rem;
	font-weight: bold;
}

.section-title {
	margin-top: 20px;
	font-weight: bold;
	border-bottom: 1px solid #ddd;
}

.option-item {
	margin: 10px 0;
}

input[type="number"] {
	width: 60px;
	padding: 5px;
	font-size: 1.2rem;
}

.btn-submit {
	background: #d35400;
	color: white;
	width: 100%;
	padding: 15px;
	border: none;
	font-size: 1.2rem;
	border-radius: 5px;
	margin-top: 20px;
	cursor: pointer;
}

.btn-back {
	display: block;
	text-align: center;
	margin-top: 15px;
	text-decoration: none;
	color: #555;
}
</style>
</head>
<body>

	<div class="detail-card">
		<c:if test="${not empty product.productImageUrl}">
			<img src="${product.productImageUrl}" alt="${product.productName}">
		</c:if>

		<h1>${product.productName}</h1>
		<p>${product.productDescription}</p>
		<p class="price">¥ ${product.price}</p>

		<form action="AddtoCartServlet" method="post">
			<input type="hidden" name="productId" value="${product.productId}">

			<div class="section-title">数量</div>
			<div style="margin-top: 10px;">
				<input type="number" name="quantity" value="1" min="1" max="10"
					required> 個
			</div>

			<%-- 
			  ★修正ポイント: 
			  サーブレットから渡された optionList が空でない場合のみ表示する。
			  これにより、オプション設定のない商品で無駄なスペースが表示されなくなります。
			--%>
			<c:if test="${not empty optionList}">
				<div class="section-title">オプション（トッピングなど）</div>
				
				<c:forEach var="opt" items="${optionList}">
					<div class="option-item">
						<label> 
							<%-- valueはOptionDTOのID --%>
							<input type="checkbox" name="optionIds" value="${opt.id}"> 
							
							<%-- 表示名 --%>
							<c:out value="${opt.optionName}" />
							(+¥<c:out value="${opt.optionPrice}" />)
						</label>
					</div>
				</c:forEach>
			</c:if>
			<%-- オプション表示エリア終了 --%>

			<button type="submit" class="btn-submit">カートに入れる</button>
		</form>

		<a href="MenuListServlet" class="btn-back">メニュー一覧に戻る</a>
	</div>

</body>
</html>