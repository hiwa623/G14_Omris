<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>カートに追加しました</title>
</head>
<body>
    <h2>カートに商品を追加しました！</h2>
    <p>追加した商品：${vm.lastAddedProduct.productName} (${vm.addedQuantity}個)</p>

    <div style="margin-top: 20px;">
        <a href="CartServlet"><button type="button">カートを見る</button></a>
        
        <a href="LineupServlet"><button type="button">買い物を続ける</button></a>
    </div>
</body>
</html>