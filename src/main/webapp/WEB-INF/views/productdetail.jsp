<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>商品詳細</title></head>
<body>
    <h1>${product.productName}</h1>
    <img src="${pageContext.request.contextPath}/${product.productImageUrl}" width="300">
    <p>${product.productDescription}</p>
    <p>価格: ${product.price}円</p>

    <form action="CartServlet" method="post">
        <input type="hidden" name="productId" value="${product.productId}">
        数量: <input type="number" name="quantity" value="1" min="1">
        <button type="submit">カートに追加する</button>
    </form>
    
    <br><a href="LineupServlet">一覧に戻る</a>
</body>
</html>