<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ご注文ありがとうございます</title>
<style>
    body { font-family: sans-serif; background: #2c3e50; color: white; display:flex; justify-content:center; align-items:center; height:100vh; margin:0; text-align:center;}
    .container { background: white; color: #333; padding: 40px; border-radius: 10px; width: 90%; max-width: 500px; box-shadow: 0 4px 15px rgba(0,0,0,0.3); }
    h1 { color: #27ae60; margin-bottom: 10px; }
    .icon { font-size: 4rem; color: #27ae60; margin-bottom: 20px; }
    .order-id { background: #eee; padding: 10px; display: inline-block; border-radius: 5px; margin: 20px 0; font-weight: bold; }
    .btn { display: block; width: 100%; padding: 15px; background: #e67e22; color: white; text-decoration: none; border-radius: 5px; font-size: 1.2rem; margin-top: 20px; box-sizing: border-box; }
    .btn:hover { background: #d35400; }
</style>
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