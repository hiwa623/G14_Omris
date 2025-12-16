<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>オムライス屋さん | 商品ラインナップ</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
}

.product-list {
	display: flex;
	flex-wrap: wrap;
	gap: 20px;
}

.product-card {
	border: 1px solid #ccc;
	padding: 15px;
	width: 300px;
}

.favorite {
	color: gold;
	font-weight: bold;
}

.error-message {
	color: red;
	font-weight: bold;
}
</style>
</head>
<body>
	<h1>オムライス メニュー一覧</h1>

	<c:if test="${not empty vm.errorMessage}">
		<p class="error-message">${vm.errorMessage}</p>
	</c:if>

	<p>
		全商品件数: <strong>${vm.totalItemCount}</strong> 件
	</p>

	<div class="product-list">
        
        <c:forEach var="product" items="${vm.productList}">
            <c:if test="${product.favorite}">
                <div class="product-card">
                    
                    <span class="favorite">★ おすすめ ★</span>

                    <h2>${product.productName}</h2>

                    <img
                        src="${pageContext.request.contextPath}/${product.productImageUrl}"
                        alt="${product.productName}" style="width: 100%; height: auto;">	

                    <p>
                        価格: <strong> <fmt:formatNumber value="${product.price}"
                                type="currency" currencySymbol="¥" maxFractionDigits="0" />
                        </strong>
                    </p>

                    <%-- ここの簡易説明はいらないのでコメントアウト --%>
					<%-- <p>${product.productDescription}</p> --%>
                    
					<%-- <button onclick="alert('${product.productName} の詳細画面へ');">詳細を見る</button> --%>
                </div>
            </c:if>
		</c:forEach>
        
        <c:forEach var="product" items="${vm.productList}">
            <c:if test="${!product.favorite}">
                <div class="product-card">
                    
                    <%-- 通常商品は★おすすめ★のタグは表示しない --%>

                    <h2>${product.productName}</h2>

                    <img
                        src="${pageContext.request.contextPath}/${product.productImageUrl}"
                        alt="${product.productName}" style="width: 100%; height: auto;">	

                    <p>
                        価格: <strong> <fmt:formatNumber value="${product.price}"
                                type="currency" currencySymbol="¥" maxFractionDigits="0" />
                        </strong>
                    </p>

					<%-- ここの簡易説明はいらないのでコメントアウト --%>
					<%-- <p>${product.productDescription}</p> --%>
                    
					<%-- <button onclick="alert('${product.productName} の詳細画面へ');">詳細を見る</button> --%>
                    
                </div>
            </c:if>
		</c:forEach>
        
	</div>
	<a href="index.jsp">管理メニューに戻る</a>
	<%-- 実際の本番環境ではこのデバッグ情報は削除します --%>
	<h3>デバッグ情報</h3>
	<p>リクエストスコープのViewModelキー: ${vm}</p>
	<p>最初の商品のID: ${vm.productList[0].productId}</p>
</body>
</html>