<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ご注文ありがとうございます</title>
<link rel="stylesheet" href="assets/css/thank_you.css">
</head>
<body>

<div class="container">
    <div class="icon">✔</div>
    <h1>ご注文を受け付けました</h1>
    <p>ただいま調理を開始いたしました。<br>商品到着まで少々お待ちください。</p>
    
    <div class="order-id">
        注文番号: ${orderId}
    </div>

    <a href="MenuListServlet" class="btn">メニューに戻って追加注文する</a>
</div>

</body>
</html>