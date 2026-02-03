<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${product.productName}-詳細</title>
<link rel="stylesheet" href="assets/css/product_detail.css">
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